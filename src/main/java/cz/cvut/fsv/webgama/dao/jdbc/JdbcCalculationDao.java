package cz.cvut.fsv.webgama.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import cz.cvut.fsv.webgama.dao.CalculationDao;
import cz.cvut.fsv.webgama.dao.CalculationStatisticDao;
import cz.cvut.fsv.webgama.dao.InputDao;
import cz.cvut.fsv.webgama.dao.OutputDao;
import cz.cvut.fsv.webgama.dao.UserDao;
import cz.cvut.fsv.webgama.domain.Calculation;
import cz.cvut.fsv.webgama.domain.CalculationStatistic;
import cz.cvut.fsv.webgama.domain.Output;
import cz.cvut.fsv.webgama.domain.User;

public class JdbcCalculationDao extends JdbcDaoSupport implements CalculationDao {

	private InputDao inputDao;

	private OutputDao outputDao;

	private UserDao userDao;

	private CalculationStatisticDao calculationStatisticDao;

	@Override
	public void insert(Calculation calculation) {

		String sql = "INSERT INTO calculations (user_id, name, progress, language, algorithm, ang_units, latitude, ellipsoid) VALUES (?, ?, ?, ?, ?, ?, ?, ?) RETURNING calculation_id";

		Long calculationId = getJdbcTemplate().queryForObject(
				sql,
				new Object[] { calculation.getUser().getId(), calculation.getName(), calculation.getProgress(),
						calculation.getLanguage(), calculation.getAlgorithm(), calculation.getAngUnits(),
						calculation.getLatitude(), calculation.getEllipsoid() }, Long.class);

		inputDao.insert(calculation.getInput(), calculationId);
		// insert calculation statistics
		calculationStatisticDao.insert(insertStatistics(calculation), calculationId);

		if (calculation.getOutput() != null) {
			outputDao.insert(calculation.getOutput(), calculationId);
		}
	}

	@Override
	public void delete(Calculation calculation) {

		String sql = "DELETE FROM calculations WHERE calculation_id = ?";

		getJdbcTemplate().update(sql, new Object[] { calculation.getId() });

	}

	@Override
	public void update(Calculation calculation) {

		String sql = "UPDATE calculations SET user_id=?, name=?, progress=?, language=?, algorithm=?, ang_units=?, latitude=?, ellipsoid=?, time=? WHERE calculation_id = ?";

		getJdbcTemplate().update(
				sql,
				new Object[] { calculation.getUser().getId(), calculation.getName(), calculation.getProgress(),
						calculation.getLanguage(), calculation.getAlgorithm(), calculation.getAngUnits(),
						calculation.getLatitude(), calculation.getEllipsoid(), calculation.getTime().toDate(),
						calculation.getId() });

		inputDao.delete(calculation.getInput());
		inputDao.insert(calculation.getInput(), calculation.getId());
		// update calculation statistic
		calculationStatisticDao.update(updateStatistics(calculation));

		if (calculation.getOutput() == null) {
			Output output = outputDao.findOutputInCalculation(calculation);
			if (output != null) {
				outputDao.delete(output);
			}
		} else {
			if (outputDao.findOutputInCalculation(calculation) == null) {
				outputDao.insert(calculation.getOutput(), calculation.getId());
			}
			outputDao.update(calculation.getOutput());
		}
	}

	@Override
	public void updateOutput(Calculation calculation) {

		String sql = "UPDATE calculations SET user_id=?, name=?, progress=?, language=?, algorithm=?, ang_units=?, latitude=?, ellipsoid=?, time=? WHERE calculation_id = ?";

		getJdbcTemplate().update(
				sql,
				new Object[] { calculation.getUser().getId(), calculation.getName(), calculation.getProgress(),
						calculation.getLanguage(), calculation.getAlgorithm(), calculation.getAngUnits(),
						calculation.getLatitude(), calculation.getEllipsoid(), calculation.getTime().toDate(),
						calculation.getId() });

		Output output = outputDao.findOutputInCalculation(calculation);

		if (output == null) {
			outputDao.insert(calculation.getOutput(), calculation.getId());
		} else {
			calculation.getOutput().setId(output.getId());
			outputDao.update(calculation.getOutput());
		}

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
	public List<Calculation> findCalculationsOnlyByUser(User user) {
		String sql = "SELECT * FROM calculations WHERE user_id = ? ORDER BY time DESC";

		List<Calculation> calculations = getJdbcTemplate().query(sql, new Object[] { user.getId() },
				new CalculationPartialMapper());

		return calculations;
	}

	@Override
	public Calculation findCalculationById(Long id) {
		String sql = "SELECT * FROM calculations WHERE calculation_id = ?";

		Calculation calculation = getJdbcTemplate().queryForObject(sql, new Object[] { id }, new CalculationMapper());

		return calculation;
	}

	@Override
	public Calculation findCalculationByInputId(Long id) {
		String sql = "SELECT A.calculation_id, user_id, name, progress, language, algorithm, ang_units, latitude, ellipsoid, A.time FROM calculations A JOIN inputs B ON A.calculation_id = B.calculation_id WHERE input_id = ?";

		Calculation calculation = getJdbcTemplate().queryForObject(sql, new Object[] { id },
				new CalculationPartialMapper());

		return calculation;
	}

	@Override
	public Long countCalculationsByUser(User user) {

		String sql = "SELECT COUNT(calculation_id) FROM calculations WHERE user_id = ?";

		return getJdbcTemplate().queryForObject(sql, new Object[] { user.getId() }, Long.class);
	}

	@Override
	public void deleteCalculationById(Long id) {

		String sql = "DELETE FROM calculations WHERE calculation_id = ?";

		getJdbcTemplate().update(sql, id);
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

	@Override
	public void updateProgress(Calculation calculation, String progress) {

		String sql = "UPDATE calculations SET progress=? WHERE calculation_id = ?";
		getJdbcTemplate().update(sql, progress, calculation.getId());
	}

	@Override
	public String findProgressById(Long id) {

		String sql = "SELECT progress FROM calculations WHERE calculation_id = ?";

		String result = getJdbcTemplate().queryForObject(sql, new Object[] { id }, String.class);

		return result;
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

	// RowMapper for getting calculation without input part
	private class CalculationPartialMapper implements RowMapper<Calculation> {

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
			calculation.setCalculationStatistic(calculationStatisticDao
					.findCalculationStatisticInCalculation(calculation));
			calculation.setTime(new DateTime(rs.getTimestamp("time").getTime()));
			calculation.setInput(null);
			calculation.setOutput(outputDao.findOutputInCalculation(calculation));

			return calculation;
		}

	}

	// helper method for inserting calculation statistic
	private CalculationStatistic insertStatistics(Calculation calculation) {
		CalculationStatistic statistic = new CalculationStatistic();
		statistic.setPoints(calculation.getInput().getNetwork().getPoints().size());
		statistic.setClusters(calculation.getInput().getNetwork().getClusters().size());
		return statistic;
	}

	// helper method for updating calculation statistic
	private CalculationStatistic updateStatistics(Calculation calculation) {
		CalculationStatistic statistic = calculationStatisticDao.findCalculationStatisticInCalculation(calculation);
		statistic.setPoints(calculation.getInput().getNetwork().getPoints().size());
		statistic.setClusters(calculation.getInput().getNetwork().getClusters().size());
		return statistic;
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

	public void setCalculationStatisticDao(CalculationStatisticDao calculationStatisticDao) {
		this.calculationStatisticDao = calculationStatisticDao;
	}
}