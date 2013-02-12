package cz.cvut.fsv.webgama.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import cz.cvut.fsv.webgama.dao.LoginDao;
import cz.cvut.fsv.webgama.domain.Login;
import cz.cvut.fsv.webgama.domain.User;

public class JdbcLoginDao extends JdbcDaoSupport implements LoginDao {

	@Override
	public void insert(Login login) {

		String sql = "INSERT INTO logins (user_id,ip_address,success) VALUES (?, ?, ?)";

		getJdbcTemplate().update(
				sql,
				new Object[] { login.getUser().getId(), login.getIp(),
						login.getSuccess() });

		if (login.getSuccess()) {
			logger.info("User [" + login.getUser().getUsername()
					+ "] successfully logged from " + login.getIp());
		} else {
			logger.info("User [" + login.getUser().getUsername()
					+ "] unsuccessfully logged from " + login.getIp());
		}

	}

	@Override
	public List<Login> getLoginList(User user) {

		String sql = "SELECT login_id,ip_address,time,success FROM logins WHERE user_id = ? ORDER BY time DESC";

		List<Login> logins = getJdbcTemplate().query(sql,
				new Object[] { user.getId() }, new LoginMapper());

		return logins;
	}

	@Override
	public Login getLastLogin(User user) {

		String sql = "SELECT login_id,ip_address,time,success FROM logins WHERE time = (SELECT MAX(time) FROM logins WHERE user_id = ? )";

		return getJdbcTemplate().queryForObject(sql,
				new Object[] { user.getId() }, new LoginMapper());

	}

	private static class LoginMapper implements RowMapper<Login> {

		@Override
		public Login mapRow(ResultSet rs, int rowNum) throws SQLException {

			Login login = new Login();
			login.setId(rs.getLong("login_id"));
			login.setIp(rs.getString("ip_address"));
			login.setTime(new DateTime(rs.getTimestamp("time").getTime()));
			login.setSuccess(rs.getBoolean("success"));

			return login;
		}

	}

}
