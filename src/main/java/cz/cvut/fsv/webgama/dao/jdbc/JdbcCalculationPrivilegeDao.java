package cz.cvut.fsv.webgama.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import cz.cvut.fsv.webgama.dao.CalculationPrivilegeDao;
import cz.cvut.fsv.webgama.dao.PrivilegeDao;
import cz.cvut.fsv.webgama.dao.UserDao;
import cz.cvut.fsv.webgama.domain.Calculation;
import cz.cvut.fsv.webgama.domain.CalculationPrivilege;

public class JdbcCalculationPrivilegeDao extends JdbcDaoSupport implements CalculationPrivilegeDao {

	private UserDao userDao;

	private PrivilegeDao privilegeDao;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setPrivilegeDao(PrivilegeDao privilegeDao) {
		this.privilegeDao = privilegeDao;
	}

	@Override
	public Long insert(CalculationPrivilege calculationPrivilege, Long calculationId) {
		String sql = "INSERT INTO calculation_privileges (user_id, calculation_id) VALUES (?,?) RETURNING calculation_privilege_id";

		Long privilegeId = getJdbcTemplate().queryForObject(sql,
				new Object[] { calculationPrivilege.getUser().getId(), calculationId }, Long.class);

		return privilegeId;
	}

	@Override
	public void delete(CalculationPrivilege calculationPrivilege) {

		String sql = "DELETE FROM calculation_privileges WHERE calculation_privilege_id = ?";

		getJdbcTemplate().update(sql, new Object[] { calculationPrivilege.getId() });

	}

	@Override
	public void deleteById(Long id) {
		String sql = "DELETE FROM calculation_privileges WHERE calculation_privilege_id = ?";

		getJdbcTemplate().update(sql, id);

	}

	@Override
	public void update(CalculationPrivilege calculationPrivilege) {

	}

	@Override
	public List<CalculationPrivilege> findAllPrivilegesforCalculation(Calculation calculation) {
		String sql = "SELECT * FROM calculation_privileges WHERE calculation_id = ?";

		List<CalculationPrivilege> calculationPrivileges = getJdbcTemplate().query(sql,
				new Object[] { calculation.getId() }, new CalculationPrivilegeMapper());

		return calculationPrivileges;
	}

	@Override
	public Boolean hasUserPrivilegeToCalculation(Long id, String username) {

		String sql = "SELECT * FROM calculation_privileges WHERE calculation_id = ? AND user_id = (SELECT user_id FROM users WHERE username = ?)";

		List<CalculationPrivilege> list = getJdbcTemplate().query(sql, new Object[] { id, username },
				new CalculationPrivilegeMapper());

		if (list.isEmpty())
			return false;

		return true;
	}

	private class CalculationPrivilegeMapper implements RowMapper<CalculationPrivilege> {

		@Override
		public CalculationPrivilege mapRow(ResultSet rs, int rowNum) throws SQLException {

			CalculationPrivilege calculationPrivilege = new CalculationPrivilege();
			calculationPrivilege.setId(rs.getLong("calculation_privilege_id"));
			calculationPrivilege.setUser(userDao.findUserById(rs.getLong("user_id")));
			calculationPrivilege.setPrivilege(privilegeDao.findPrivilegeById(rs.getLong("privilege_id")));

			return calculationPrivilege;
		}
	}
}
