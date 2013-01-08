package cz.cvut.fsv.webgama.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import cz.cvut.fsv.webgama.dao.SlopeDistanceDao;
import cz.cvut.fsv.webgama.domain.Observation;
import cz.cvut.fsv.webgama.domain.SlopeDistance;

public class JdbcSlopeDistanceDao extends JdbcDaoSupport implements
		SlopeDistanceDao {

	@Override
	public void insert(SlopeDistance slopeDistance, Integer observationId) {

		String sql = "INSERT INTO slope_distances (observation_id, from_id, to_id, val, stdev, from_dh, to_dh) VALUES (?, ?, ?, ?, ?, ?, ?)";

		getJdbcTemplate().update(
				sql,
				new Object[] { observationId, slopeDistance.getFrom(),
						slopeDistance.getTo(), slopeDistance.getVal(),
						slopeDistance.getStdev(), slopeDistance.getFromDh(),
						slopeDistance.getToDh() });
	}

	@Override
	public void delete(SlopeDistance slopeDistance) {

		String sql = "DELETE FROM slope_distances WHERE slope_distance_id = ?";

		getJdbcTemplate().update(sql, new Object[] { slopeDistance.getId() });
	}

	@Override
	public void update(SlopeDistance slopeDistance) {

		String sql = "UPDATE slope_distances SET from_id=?, to_id=?, val=?, stdev=?, from_dh=?, to_dh=? WHERE slope_distance_id=?";

		getJdbcTemplate().update(
				sql,
				new Object[] { slopeDistance.getFrom(), slopeDistance.getTo(),
						slopeDistance.getVal(), slopeDistance.getStdev(),
						slopeDistance.getFromDh(), slopeDistance.getToDh(),
						slopeDistance.getId() });
	}

	@Override
	public List<SlopeDistance> findSlopeDistancesInObservation(
			Observation observation) {

		String sql = "SELECT * FROM slope_distances WHERE observation_id = ?";

		List<SlopeDistance> slopeDistances = getJdbcTemplate().query(sql,
				new Object[] { observation.getId() }, new DistanceMapper());

		return slopeDistances;
	}

	private static class DistanceMapper implements RowMapper<SlopeDistance> {

		@Override
		public SlopeDistance mapRow(ResultSet rs, int rowNum)
				throws SQLException {

			SlopeDistance slopeDistance = new SlopeDistance();

			slopeDistance.setId(rs.getInt("slope_distance_id"));
			slopeDistance.setFrom(rs.getString("from_id"));
			slopeDistance.setTo(rs.getString("to_id"));
			slopeDistance.setVal(rs.getDouble("val"));
			slopeDistance.setStdev(rs.getDouble("stdev"));
			slopeDistance.setFromDh(rs.getDouble("from_dh"));
			slopeDistance.setToDh(rs.getDouble("to_dh"));

			return slopeDistance;
		}
	}

}
