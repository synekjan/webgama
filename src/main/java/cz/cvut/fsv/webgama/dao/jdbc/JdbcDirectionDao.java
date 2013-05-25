package cz.cvut.fsv.webgama.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import cz.cvut.fsv.webgama.dao.DirectionDao;
import cz.cvut.fsv.webgama.domain.Direction;
import cz.cvut.fsv.webgama.domain.Observation;

public class JdbcDirectionDao extends JdbcDaoSupport implements DirectionDao {

	@Override
	public void insert(Direction direction, Long observationId) {

		String sql = "INSERT INTO directions (observation_id, to_id, val, stdev, from_dh, to_dh) VALUES (?, ?, ?, ?, ?, ?)";

		getJdbcTemplate().update(
				sql,
				new Object[] { observationId, direction.getTo(), direction.getVal(), direction.getStdev(),
						direction.getFromDh(), direction.getToDh() });
	}

	@Override
	public void delete(Direction direction) {

		String sql = "DELETE FROM directions WHERE direction_id = ?";

		getJdbcTemplate().update(sql, new Object[] { direction.getId() });
	}

	@Override
	public void update(Direction direction) {

		String sql = "UPDATE directions SET to_id=?, val=?, stdev=?, from_dh=?, to_dh=? WHERE direction_id=?";

		getJdbcTemplate().update(
				sql,
				new Object[] { direction.getTo(), direction.getVal(), direction.getStdev(), direction.getFromDh(),
						direction.getToDh(), direction.getId() });
	}

	@Override
	public List<Direction> findDirectionsInObservation(Observation observation) {

		String sql = "SELECT * FROM directions WHERE observation_id = ? ORDER BY direction_id";

		List<Direction> directions = getJdbcTemplate().query(sql, new Object[] { observation.getId() },
				new DirectionMapper());

		return directions;
	}

	private static class DirectionMapper implements RowMapper<Direction> {

		@Override
		public Direction mapRow(ResultSet rs, int rowNum) throws SQLException {

			Direction direction = new Direction();

			direction.setId(rs.getLong("direction_id"));
			direction.setTo(rs.getString("to_id"));
			direction.setVal(rs.getString("val"));
			direction.setStdev(rs.getObject("stdev") != null ? rs.getDouble("stdev") : null);
			direction.setFromDh(rs.getObject("from_dh") != null ? rs.getDouble("from_dh") : null);
			direction.setToDh(rs.getObject("to_dh") != null ? rs.getDouble("to_dh") : null);

			return direction;
		}
	}
}
