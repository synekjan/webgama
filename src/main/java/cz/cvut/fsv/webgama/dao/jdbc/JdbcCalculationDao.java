package cz.cvut.fsv.webgama.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import cz.cvut.fsv.webgama.dao.CalculationDao;
import cz.cvut.fsv.webgama.dao.InputDao;
import cz.cvut.fsv.webgama.dao.OutputDao;
import cz.cvut.fsv.webgama.dao.UserDao;
import cz.cvut.fsv.webgama.domain.Calculation;
import cz.cvut.fsv.webgama.domain.User;

public class JdbcCalculationDao extends JdbcDaoSupport implements CalculationDao {

	private InputDao inputDao;

	private OutputDao outputDao;

	private UserDao userDao;

	@Override
	public void insert(Calculation calculation) {

		String sql = "INSERT INTO calculations (user_id, name, progress, language, algorithm, ang_units, latitude, ellipsoid) VALUES (?, ?, ?, ?, ?, ?, ?, ?) RETURNING calculation_id";

		long calculationId = getJdbcTemplate().queryForLong(
				sql,
				new Object[] { calculation.getUser().getId(), calculation.getName(), calculation.getProgress(),
						calculation.getLanguage(), calculation.getAlgorithm(), calculation.getAngUnits(),
						calculation.getLatitude(), calculation.getEllipsoid() });

		inputDao.insert(calculation.getInput(), calculationId);
		outputDao.insert(calculation.getOutput(), calculationId);
	}

	@Override
	public void delete(Calculation calculation) {

		String sql = "DELETE FROM calculations WHERE calculation_id = ?";

		getJdbcTemplate().update(sql, new Object[] { calculation.getId() });

	}

	@Override
	public void update(Calculation calculation) {

		String sql = "UPDATE calculations SET user_id=?, name=?, progress=?, language=?, algorithm=?, ang_units=?, latitude=?, ellipsoid=? WHERE calculation_id = ?";

		getJdbcTemplate().update(
				sql,
				new Object[] { calculation.getUser().getId(), calculation.getName(), calculation.getProgress(),
						calculation.getLanguage(), calculation.getAlgorithm(), calculation.getAngUnits(),
						calculation.getLatitude(), calculation.getEllipsoid(), calculation.getId() });
		inputDao.update(calculation.getInput());
	}

	@Override
	public List<Calculation> getCalculationList() {
		String sql = "SELECT * FROM calculations";

		List<Calculation> calculations = getJdbcTemplate().query(sql, new CalculationMapper());

		return calculations;
	}

	@Override
	public List<Calculation> findCalculationsByUser(User user) {
		String sql = "SELECT * FROM calculations WHERE user_id = ? ORDER BY time DESC";

		List<Calculation> calculations = getJdbcTemplate().query(sql, new Object[] { user.getId() },
				new CalculationMapper());

		return calculations;
	}

	@Override
	public Calculation findCalculationById(Long id) {
		String sql = "SELECT * FROM calculations WHERE calculation_id = ?";

		Calculation calculation = getJdbcTemplate().queryForObject(sql, new Object[] { id }, new CalculationMapper());

		return calculation;
	}

	@Override
	public long getCalculationCountByUser(User user) {

		String sql = "SELECT COUNT(calculation_id) FROM calculations WHERE user_id = ?";
		
		return getJdbcTemplate().queryForLong(sql, new Object[] { user.getId() });
	}

	@Override
	public boolean isCalculationIdInDB(Long id) {
		String sql = "SELECT * FROM calculations WHERE calculation_id = ?";
		List<Calculation> ids = getJdbcTemplate().query(sql, new Object[] { id }, new CalculationMapper());

		if (ids.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}

	private class CalculationMapper implements RowMapper<Calculation> {

		@Override
		public Calculation mapRow(ResultSet rs, int rowNum) throws SQLException {

			Calculation calculation = new Calculation();

			calculation.setId(rs.getLong("calculation_id"));
			calculation.setUser(userDao.findUserById(rs.getInt("user_id")));
			calculation.setName(rs.getString("name"));
			calculation.setProgress(rs.getString("progress"));
			calculation.setLanguage(rs.getString("language"));
			calculation.setAlgorithm(rs.getString("algorithm"));
			calculation.setAngUnits(rs.getInt("ang_units"));
			calculation.setLatitude(rs.getObject("latitude") != null ? rs.getDouble("latitude") : null);
			calculation.setEllipsoid(rs.getString("ellipsoid"));
			calculation.setTime(new DateTime(rs.getTimestamp("time").getTime()));
			calculation.setInput(inputDao.findInputInCalculation(calculation));
			calculation.setOutput("calculated".equals(calculation.getProgress()) ? outputDao
					.findOutputInCalculation(calculation) : null);

			return calculation;
		}

	}

	public void setInputDao(InputDao inputDao) {
		this.inputDao = inputDao;
	}

	public void setOutputDao(OutputDao outputDao) {
		this.outputDao = outputDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

}
