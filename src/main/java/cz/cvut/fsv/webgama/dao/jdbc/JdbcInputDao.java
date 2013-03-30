package cz.cvut.fsv.webgama.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.joda.time.DateTime;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import cz.cvut.fsv.webgama.dao.InputDao;
import cz.cvut.fsv.webgama.dao.NetworkDao;
import cz.cvut.fsv.webgama.domain.Calculation;
import cz.cvut.fsv.webgama.domain.Input;

public class JdbcInputDao extends JdbcDaoSupport implements InputDao {

	private NetworkDao networkDao;

	public void setNetworkDao(NetworkDao networkDao) {
		this.networkDao = networkDao;
	}

	@Override
	public void insert(Input input, Long calculationId) {

		String sql = "INSERT INTO inputs (calculation_id, xml_content, version) VALUES (?, ?, ?) RETURNING input_id";

		Object[] params = new Object[] { calculationId, input.getXmlContent(), input.getVersion() };

		// Store last generated value from SERIAL PostgreSQL type [RETURNING
		// syntax
		Long inputId = getJdbcTemplate().queryForObject(sql, params, Long.class);

		networkDao.insert(input.getNetwork(), inputId);
	}

	@Override
	public void delete(Input input) {

		String sql = "DELETE FROM inputs WHERE input_id = ?";

		getJdbcTemplate().update(sql, new Object[] { input.getId() });
	}

	@Override
	public void update(Input input) {

		String sql = "UPDATE inputs SET xml_content=?, version=?, time=? WHERE input_id = ?";

		getJdbcTemplate().update(sql,
				new Object[] { input.getXmlContent(), input.getVersion(), input.getTime().toDate(), input.getId() });

		networkDao.update(input.getNetwork());
	}

	@Override
	public Input findInputById(Long id) {

		String sql = "SELECT * FROM inputs WHERE input_id = ?";

		Input input = getJdbcTemplate().queryForObject(sql, new Object[] { id }, new InputMapper());

		return input;
	}

	@Override
	public Input findInputInCalculation(Calculation calculation) {

		String sql = "SELECT * FROM inputs WHERE calculation_id = ?";

		Input input = getJdbcTemplate().queryForObject(sql, new Object[] { calculation.getId() }, new InputMapper());

		return input;
	}

	private class InputMapper implements RowMapper<Input> {

		@Override
		public Input mapRow(ResultSet rs, int rowNum) throws SQLException {

			Input input = new Input();

			input.setId(rs.getLong("input_id"));
			input.setXmlContent(rs.getString("xml_content"));
			input.setVersion(rs.getString("version"));
			input.setNetwork(networkDao.findNetworkByInput(input));
			input.setTime(new DateTime(rs.getTimestamp("time").getTime()));

			return input;
		}

	}

}
