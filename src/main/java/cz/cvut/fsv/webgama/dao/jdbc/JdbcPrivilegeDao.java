package cz.cvut.fsv.webgama.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import cz.cvut.fsv.webgama.dao.PrivilegeDao;
import cz.cvut.fsv.webgama.domain.Privilege;

public class JdbcPrivilegeDao extends JdbcDaoSupport implements PrivilegeDao {

	@Override
	public Privilege findPrivilegeById(Long id) {
		
		String sql = "SELECT * FROM privileges WHERE privilege_id = ?";

		Privilege privilege = getJdbcTemplate().queryForObject(sql, new Object[] { id }, new PrivilegeMapper());

		return privilege;
	}
	
	private class PrivilegeMapper implements RowMapper<Privilege> {

		@Override
		public Privilege mapRow(ResultSet rs, int rowNum) throws SQLException {

			Privilege privilege = new Privilege();

			privilege.setId(rs.getLong("privilege_id"));
			privilege.setName(rs.getString("privilege"));

			return privilege;
		}
	}

}
