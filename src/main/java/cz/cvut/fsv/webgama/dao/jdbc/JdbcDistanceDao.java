package cz.cvut.fsv.webgama.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import cz.cvut.fsv.webgama.dao.DistanceDao;
import cz.cvut.fsv.webgama.domain.Distance;
import cz.cvut.fsv.webgama.domain.Observation;

public class JdbcDistanceDao extends JdbcDaoSupport implements DistanceDao {

	@Override
	public void insert(Distance distance, Long observationId) {

		String sql = "INSERT INTO distances (observation_id, from_id, to_id, val, stdev, from_dh, to_dh) VALUES (?, ?, ?, ?, ?, ?, ?)";

		getJdbcTemplate().update(
				sql,
				new Object[] { observationId, distance.getFrom(), distance.getTo(), distance.getVal(),
						distance.getStdev(), distance.getFromDh(), distance.getToDh() });
	}

	@Override
	public void delete(Distance distance) {

		String sql = "DELETE FROM distances WHERE distance_id = ?";

		getJdbcTemplate().update(sql, new Object[] { distance.getId() });
	}

	@Override
	public void update(Distance distance) {

		String sql = "UPDATE distances SET from_id=?, to_id=?, val=?, stdev=?, from_dh=?, to_dh=? WHERE distance_id=?";

		getJdbcTemplate().update(
				sql,
				new Object[] { distance.getFrom(), distance.getTo(), distance.getVal(), distance.getStdev(),
						distance.getFromDh(), distance.getToDh(), distance.getId() });
	}

	@Override
	public List<Distance> findDistancesInObservation(Observation observation) {

		String sql = "SELECT * FROM distances WHERE observation_id = ? ORDER BY distance_id";

		List<Distance> distances = getJdbcTemplate().query(sql, new Object[] { observation.getId() },
				new DistanceMapper());

		return distances;
	}

	private static class DistanceMapper implements RowMapper<Distance> {

		@Override
		public Distance mapRow(ResultSet rs, int rowNum) throws SQLException {

			Distance distance = new Distance();

			distance.setId(rs.getLong("distance_id"));
			distance.setFrom(rs.getString("from_id"));
			distance.setTo(rs.getString("to_id"));
			distance.setVal(rs.getObject("val") != null ? rs.getDouble("val") : null);
			distance.setStdev(rs.getObject("stdev") != null ? rs.getDouble("stdev") : null);
			distance.setFromDh(rs.getObject("from_dh") != null ? rs.getDouble("from_dh") : null);
			distance.setToDh(rs.getObject("to_dh") != null ? rs.getDouble("to_dh") : null);

			return distance;
		}
	}

}
