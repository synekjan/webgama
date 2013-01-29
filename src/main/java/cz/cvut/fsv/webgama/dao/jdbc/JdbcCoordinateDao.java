package cz.cvut.fsv.webgama.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import cz.cvut.fsv.webgama.dao.CoordinateDao;
import cz.cvut.fsv.webgama.domain.AlternativeObservation;
import cz.cvut.fsv.webgama.domain.Coordinate;

public class JdbcCoordinateDao extends JdbcDaoSupport implements CoordinateDao {

	@Override
	public void insert(Coordinate coordinate, Integer alternativeObservationId) {
		String sql = "INSERT INTO coordinates (alternative_observation_id, id, x, y, z) VALUES (?, ?, ?, ?, ?)";

		getJdbcTemplate()
				.update(sql,
						new Object[] { alternativeObservationId,
								coordinate.getName(), coordinate.getX(),
								coordinate.getY(), coordinate.getZ() });
	}

	@Override
	public void delete(Coordinate coordinate) {
		String sql = "DELETE FROM coordinates WHERE coordinate_id = ?";

		getJdbcTemplate().update(sql, new Object[] { coordinate.getId() });

	}

	@Override
	public void update(Coordinate coordinate) {
		String sql = "UPDATE coordinate SET id=?, x=?, y=?, z=? WHERE coordinate_id=?";

		getJdbcTemplate().update(
				sql,
				new Object[] { coordinate.getName(), coordinate.getX(),
						coordinate.getY(), coordinate.getZ(),
						coordinate.getId() });

	}

	@Override
	public List<Coordinate> findCoordinatesInAlternativeObservation(
			AlternativeObservation alternativeObservation) {
		String sql = "SELECT * FROM coordinates WHERE alternative_observation_id = ?";

		List<Coordinate> coordinates = getJdbcTemplate().query(sql,
				new Object[] { alternativeObservation.getId() },
				new CoordinateMapper());

		return coordinates;
	}

	private static class CoordinateMapper implements RowMapper<Coordinate> {
		@Override
		public Coordinate mapRow(ResultSet rs, int rowNum) throws SQLException {

			Coordinate coordinate = new Coordinate();

			coordinate.setId(rs.getInt("coordinate_id"));
			coordinate.setName(rs.getString("id"));
			coordinate.setX(rs.getDouble("x"));
			coordinate.setY(rs.getDouble("y"));
			coordinate.setZ(rs.getDouble("z"));

			return coordinate;
		}
	}

}
