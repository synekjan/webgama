package cz.cvut.fsv.webgama.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import cz.cvut.fsv.webgama.dao.RoleDao;
import cz.cvut.fsv.webgama.domain.Role;

public class JdbcRoleDao extends JdbcDaoSupport implements RoleDao {

	@Override
	public Role findRoleById(int id) {

		String sql = "SELECT * FROM roles WHERE role_id = ?";

		Role role = getJdbcTemplate().queryForObject(sql, new Object[] { id }, new RoleMapper());

		return role;
	}

	private static class RoleMapper implements RowMapper<Role> {

		@Override
		public Role mapRow(ResultSet rs, int rowNum) throws SQLException {

			Role role = new Role();

			role.setId(rs.getLong("role_id"));
			role.setName(rs.getString("role"));

			return role;
		}

	}

}
