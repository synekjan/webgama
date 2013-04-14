package cz.cvut.fsv.webgama.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import cz.cvut.fsv.webgama.dao.OutputDao;
import cz.cvut.fsv.webgama.domain.Calculation;
import cz.cvut.fsv.webgama.domain.Output;

public class JdbcOutputDao extends JdbcDaoSupport implements OutputDao {

	@Override
	public void insert(Output output, Long calculationId) {
		String sql = "INSERT INTO outputs (calculation_id, xml_content, text_content, html_content, svg_content, running_time, last_error) VALUES (?, ?, ?, ?, ?, ?, ?)";

		getJdbcTemplate().update(
				sql,
				new Object[] { calculationId, output.getXmlContent(), output.getTextContent(), output.getHtmlContent(),
						output.getSvgContent(), output.getRunningTime(), output.getLastError() });

	}

	@Override
	public void delete(Output output) {

		String sql = "DELETE FROM outputs WHERE output_id = ?";

		getJdbcTemplate().update(sql, new Object[] { output.getId() });

	}

	@Override
	public void update(Output output) {

		String sql = "UPDATE outputs SET xml_content=?, text_content=?, html_content=?, svg_content=?, running_time=?, last_error=?, time=? WHERE output_id=?";

		getJdbcTemplate().update(
				sql,
				new Object[] { output.getXmlContent(), output.getTextContent(), output.getHtmlContent(),
						output.getSvgContent(), output.getRunningTime(), output.getLastError(), output.getTime().toDate(), output.getId() });
	}

	@Override
	public Output findOutputInCalculation(Calculation calculation) {

		String sql = "SELECT * FROM outputs WHERE calculation_id =?";

		List<Output> outputs = getJdbcTemplate().query(sql, new Object[] { calculation.getId() }, new OutputMapper());

		if (outputs.isEmpty()) {
			return null;
		} else {
			return outputs.get(0);
		}
	}

	private class OutputMapper implements RowMapper<Output> {

		@Override
		public Output mapRow(ResultSet rs, int rowNum) throws SQLException {

			Output output = new Output();

			output.setId(rs.getLong("output_id"));
			output.setXmlContent(rs.getString("xml_content"));
			output.setTextContent(rs.getString("text_content"));
			output.setHtmlContent(rs.getString("html_content"));
			output.setSvgContent(rs.getString("svg_content"));
			output.setRunningTime(rs.getDouble("running_time"));
			output.setLastError(rs.getString("last_error"));
			output.setTime(new DateTime(rs.getTimestamp("time").getTime()));

			return output;
		}
	}
}
