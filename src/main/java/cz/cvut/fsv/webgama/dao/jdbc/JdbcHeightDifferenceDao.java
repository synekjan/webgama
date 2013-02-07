package cz.cvut.fsv.webgama.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import cz.cvut.fsv.webgama.dao.HeightDifferenceDao;
import cz.cvut.fsv.webgama.domain.AlternativeObservation;
import cz.cvut.fsv.webgama.domain.HeightDifference;

public class JdbcHeightDifferenceDao extends JdbcDaoSupport implements
		HeightDifferenceDao {

	@Override
	public void insert(HeightDifference heightDifference,
			Integer alternativeObservationId) {
		String sql = "INSERT INTO height_differences (alternative_observation_id, from_id, to_id, val, stdev, dist) VALUES (?, ?, ?, ?, ?, ?)";

		getJdbcTemplate().update(
				sql,
				new Object[] { alternativeObservationId,
						heightDifference.getFrom(), heightDifference.getTo(),
						heightDifference.getVal(), heightDifference.getStdev(),
						heightDifference.getDist() });

	}

	@Override
	public void delete(HeightDifference heightDifference) {
		String sql = "DELETE FROM height_differences WHERE height_difference_id = ?";

		getJdbcTemplate()
				.update(sql, new Object[] { heightDifference.getId() });

	}

	@Override
	public void update(HeightDifference heightDifference) {
		String sql = "UPDATE height_differences SET from_id=?, to_id=?, val=?, stdev=?, dist=? WHERE height_difference_id=?";

		getJdbcTemplate().update(
				sql,
				new Object[] { heightDifference.getFrom(),
						heightDifference.getTo(), heightDifference.getVal(),
						heightDifference.getStdev(),
						heightDifference.getDist(), heightDifference.getId() });

	}

	@Override
	public List<HeightDifference> findHeightDifferencesInAlternativeObservation(
			AlternativeObservation alternativeObservation) {
		String sql = "SELECT * FROM height_differences WHERE alternative_observation_id = ?";

		List<HeightDifference> heightDifferences = getJdbcTemplate().query(sql,
				new Object[] { alternativeObservation.getId() },
				new HeightDifferenceMapper());

		return heightDifferences;
	}

	private static class HeightDifferenceMapper implements
			RowMapper<HeightDifference> {

		@Override
		public HeightDifference mapRow(ResultSet rs, int rowNum)
				throws SQLException {

			HeightDifference heightDifference = new HeightDifference();

			heightDifference.setId(rs.getInt("height_difference_id"));
			heightDifference.setFrom(rs.getString("from_id"));
			heightDifference.setTo(rs.getString("to_id"));
			heightDifference.setVal(rs.getDouble("val"));
			heightDifference.setStdev(rs.getDouble("stdev"));
			heightDifference.setDist(rs.getDouble("dist"));

			return heightDifference;
		}
	}

}