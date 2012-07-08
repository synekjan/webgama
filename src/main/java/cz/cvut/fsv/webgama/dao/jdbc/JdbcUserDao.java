package cz.cvut.fsv.webgama.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import cz.cvut.fsv.webgama.dao.UserDao;
import cz.cvut.fsv.webgama.domain.Confirmation;
import cz.cvut.fsv.webgama.domain.User;

public class JdbcUserDao extends JdbcDaoSupport implements UserDao {

	@Override
	public void insert(User user) {

		String sql = "INSERT INTO users ( username, password, firstname, lastname, email, telephone, street, number, city, zipcode, state) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		getJdbcTemplate().update(
				sql,
				new Object[] { user.getUsername(), user.getPassword(),
						user.getFirstName(), user.getLastName(),
						user.getEmail(), user.getTelephone(), user.getStreet(),
						user.getNumber(), user.getCity(), user.getZipCode(),
						user.getState() });
	}

	@Override
	public void delete(User user) {

		String sql = "DELETE FROM users WHERE id = ?";

		getJdbcTemplate().update(sql, new Object[] { user.getId() });
	}

	@Override
	public void update(User user) {

		String sql = "UPDATE users SET firstname=?, lastname=?, email=?, telephone=?, street=?, number=?, city=?, zipcode=?, state=? WHERE id = ?";

		getJdbcTemplate().update(
				sql,
				new Object[] { user.getFirstName(), user.getLastName(),
						user.getEmail(), user.getTelephone(), user.getStreet(),
						user.getNumber(), user.getCity(), user.getZipCode(),
						user.getState(), user.getId() });
	}

	@Override
	public void updatePassword(User user) {

		String sql = "UPDATE users SET password=? WHERE id=?";

		getJdbcTemplate().update(sql, user.getPassword(), user.getId());
	}
	
	public void updateEnabled(User user) {
		String sql = "UPDATE users set enabled=? WHERE id=?";
		
		getJdbcTemplate().update(sql, user.getEnabled(), user.getId());
	}
	

	@Override
	public List<User> getUserList() {

		List<User> users = getJdbcTemplate().query("SELECT * FROM users;",
				new UserMapper());

		return users;
	}

	@Override
	public List<User> findUsersByUsername(String username) {

		String sql = "SELECT * FROM users WHERE username = ?";

		List<User> users = getJdbcTemplate().query(sql,
				new Object[] { username }, new UserMapper());

		return users;
	}

	@Override
	public List<User> findUsersByEmail(String email) {

		String sql = "SELECT * FROM users WHERE email = ?";

		List<User> users = getJdbcTemplate().query(sql, new Object[] { email },
				new UserMapper());

		return users;
	}

	@Override
	public User findUserById(int id) {

		String sql = "SELECT * FROM users WHERE id = ?";

		User user = getJdbcTemplate().queryForObject(sql, new Object[] { id },
				new UserMapper());

		return user;
	}

	@Override
	public User findUserByUsername(String username) {

		String sql = "SELECT * FROM users WHERE username = ?";

		User user = getJdbcTemplate().queryForObject(sql,
				new Object[] { username }, new UserMapper());

		return user;
	}

	@Override
	public void insertConfirmationID(String uuid, Integer user_id) {

		String sql = "INSERT INTO confirmations (user_id, uuid) VALUES (?,?)";

		getJdbcTemplate().update(sql, user_id, uuid);

	}

	@Override
	public void deleteConfirmationID(String uuid) {

		String sql = "DELETE FROM confirmations WHERE uuid = ?";

		getJdbcTemplate().update(sql, uuid);

	}
	
	@Override
	public List<Confirmation> findConfirmationsByUUID(String uuid) {
		
		String sql = "SELECT * FROM confirmations WHERE uuid = ?";

		List<Confirmation> confirmations = getJdbcTemplate().query(sql, new Object[] { uuid },
				new ConfirmationMapper());

		return confirmations;
	}

	private class UserMapper implements RowMapper<User> {

		@Override
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {

			User user = new User();
			user.setId(rs.getInt("id"));
			user.setUsername(rs.getString("username"));
			user.setPassword(rs.getString("password"));
			user.setEnabled(rs.getBoolean("enabled"));
			user.setFirstName(rs.getString("firstname"));
			user.setLastName(rs.getString("lastname"));
			user.setEmail(rs.getString("email"));
			user.setTelephone(rs.getString("telephone"));
			user.setStreet(rs.getString("street"));
			user.setNumber(rs.getString("number"));
			user.setCity(rs.getString("city"));
			user.setZipCode(rs.getString("zipcode"));
			user.setState(rs.getString("state"));
			user.setCreated(new Date(rs.getTimestamp("date_created").getTime()));
			user.setModified(new Date(rs.getTimestamp("date_modified")
					.getTime()));

			return user;
		}
	}
	
	
	private class ConfirmationMapper implements RowMapper<Confirmation> {

		@Override
		public Confirmation mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			Confirmation conf = new Confirmation();
			
			conf.setId(rs.getInt("id"));
			conf.setUser(findUserById(rs.getInt("user_id")));
			conf.setUuid(rs.getString("uuid"));
			conf.setTime(new Date(rs.getTimestamp("time").getTime()));
			
			return conf;
		}
		
	}

}
