package cz.cvut.fsv.webgama.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import cz.cvut.fsv.webgama.dao.ActivityDao;
import cz.cvut.fsv.webgama.dao.UserDao;
import cz.cvut.fsv.webgama.domain.Activity;
import cz.cvut.fsv.webgama.domain.User;

public class JdbcActivityDao extends JdbcDaoSupport implements ActivityDao {

	private UserDao userDao;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public void insert(Activity activity) {

		String sql = "INSERT INTO activities (user_id, type, message) VALUES (?, ?, ?)";

		getJdbcTemplate().update(
				sql,
				new Object[] { activity.getUser().getId(), activity.getType(),
						activity.getMessage() });
	}

	@Override
	public void delete(Activity activity) {
		String sql = "DELETE FROM activities WHERE activity_id = ?";

		getJdbcTemplate().update(sql, activity.getId());
	}

	@Override
	public void update(Activity activity) {
		String sql = "UPDATE activities SET user_id=?, type=?, message=? WHERE activity_id = ?";

		getJdbcTemplate().update(sql, activity.getUser().getId(),
				activity.getType(), activity.getMessage());
	}

	@Override
	public List<Activity> findAllActivitiesByUser(User user) {
		
		String sql = "SELECT * FROM activities WHERE user_id = ? ORDER BY time DESC";
		
		List<Activity> activities = getJdbcTemplate().query(sql, new Object[] {user.getId()} , new ActivityMapper());
		
		return activities;
	}

	@Override
	public List<Activity> findRecentActivitiesByUser(User user) {
		
		String sql = "SELECT * FROM activities WHERE user_id = ? ORDER BY time DESC LIMIT 15";
		
		List<Activity> activities = getJdbcTemplate().query(sql, new Object[] {user.getId()} , new ActivityMapper());
		
		return activities;
	}

	private class ActivityMapper implements RowMapper<Activity> {

		@Override
		public Activity mapRow(ResultSet rs, int rowNum) throws SQLException {

			Activity activity = new Activity();
			activity.setId(rs.getLong("activity_id"));
			activity.setUser(userDao.findUserById(rs.getLong("user_id")));
			activity.setType(rs.getString("type"));
			activity.setMessage(rs.getString("message"));
			activity.setTime(new DateTime(rs.getTimestamp("time").getTime()));

			return activity;
		}

	}

}
