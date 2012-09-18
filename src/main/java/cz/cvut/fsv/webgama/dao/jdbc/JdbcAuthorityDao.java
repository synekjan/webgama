package cz.cvut.fsv.webgama.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import cz.cvut.fsv.webgama.dao.AuthorityDao;
import cz.cvut.fsv.webgama.dao.RoleDao;
import cz.cvut.fsv.webgama.dao.UserDao;
import cz.cvut.fsv.webgama.domain.Authority;
import cz.cvut.fsv.webgama.domain.User;

public class JdbcAuthorityDao extends JdbcDaoSupport implements AuthorityDao {

	private UserDao userDao;

	private RoleDao roleDao;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	@Override
	public List<Authority> getUserAuthorities(User user) {

		String sql = "SELECT * FROM authorities WHERE user_id = ?";

		List<Authority> list = getJdbcTemplate().query(sql,
				new Object[] { user.getId() }, new AuthorityMapper());

		return list;
	}

	private class AuthorityMapper implements RowMapper<Authority> {

		@Override
		public Authority mapRow(ResultSet rs, int rowNum) throws SQLException {

			Authority authority = new Authority();
			authority.setId(Integer.valueOf(rs.getInt("authority_id")));
			authority.setRole(roleDao.findRoleById(rs.getInt("role_id")));
			authority.setUser(userDao.findUserById(rs.getInt("user_id")));
			
			return authority;
		}

	}

}
