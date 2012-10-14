package cz.cvut.fsv.webgama.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import cz.cvut.fsv.webgama.dao.InputDao;
import cz.cvut.fsv.webgama.dao.NetworkDao;
import cz.cvut.fsv.webgama.dao.UserDao;
import cz.cvut.fsv.webgama.domain.Input;
import cz.cvut.fsv.webgama.domain.User;

public class JdbcInputDao extends JdbcDaoSupport implements InputDao {

	private UserDao userDao;

	private NetworkDao networkDao;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setNetworkDao(NetworkDao networkDao) {
		this.networkDao = networkDao;
	}

	@Override
	public void insert(Input input) {

		String sql = "INSERT INTO inputs (user_id, filename, file_content, algorithm, ang_units, latitude, ellipsoid, version, time) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

		getJdbcTemplate().update(
				sql,
				new Object[] { input.getUser().getId(), input.getFilename(),
						input.getFileContent(), input.getAlgorithm(),
						input.getAngUnits(), input.getLatitude(),
						input.getEllipsoid(), input.getVersion(),
						input.getTime() });

		networkDao.insert(input.getNetwork(), input.getId());
	}

	@Override
	public void delete(Input input) {

		String sql = "DELETE FROM inputs WHERE input_id = ?";

		getJdbcTemplate().update(sql, new Object[] { input.getId() });
	}

	@Override
	public void update(Input input) {

		String sql = "UPDATE inputs SET user_id=?, filename=?, file_content=?, algorithm=?, ang_units=?, latitude=?, ellipsoid=?, version=?, time=? WHERE input_id = ?";

		getJdbcTemplate().update(
				sql,
				new Object[] { input.getUser().getId(), input.getFilename(),
						input.getFileContent(), input.getAlgorithm(),
						input.getAngUnits(), input.getLatitude(),
						input.getEllipsoid(), input.getVersion(),
						input.getTime(), input.getId() });
	}

	@Override
	public List<Input> getInputList() {

		String sql = "SELECT * FROM inputs";

		List<Input> inputs = getJdbcTemplate().query(sql, new InputMapper());

		return inputs;
	}

	@Override
	public List<Input> findInputsByUser(User user) {

		String sql = "SELECT * FROM inputs WHERE user_id = ?";

		List<Input> inputs = getJdbcTemplate().query(sql,
				new Object[] { user.getId() }, new InputMapper());

		return inputs;
	}

	private class InputMapper implements RowMapper<Input> {

		@Override
		public Input mapRow(ResultSet rs, int rowNum) throws SQLException {

			Input input = new Input();

			input.setId(rs.getInt("input_id"));
			input.setUser(userDao.findUserById(rs.getInt("user_id")));
			input.setFilename(rs.getString("filename"));
			input.setFileContent(rs.getString("file_content"));
			input.setAlgorithm(rs.getString("algorithm"));
			input.setAngUnits(rs.getInt("ang_units"));
			input.setLatitude(rs.getDouble("latitude"));
			input.setEllipsoid(rs.getString("ellipsoid"));
			input.setVersion(rs.getString("version"));
			input.setNetwork(networkDao.findNetworkByInput(input));
			input.setTime(new DateTime(rs.getTimestamp("time").getTime()));

			return input;
		}

	}

}
