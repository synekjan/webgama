package cz.cvut.fsv.webgama.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import cz.cvut.fsv.webgama.dao.CoordinateDao;
import cz.cvut.fsv.webgama.domain.Cluster;
import cz.cvut.fsv.webgama.domain.Coordinate;

public class JdbcCoordinateDao extends JdbcDaoSupport implements CoordinateDao {

	@Override
	public void insert(Coordinate coordinate, Long clusterId) {
		String sql = "INSERT INTO coordinates (cluster_id, id, x, y, z) VALUES (?, ?, ?, ?, ?)";

		getJdbcTemplate()
				.update(sql,
						new Object[] { clusterId, coordinate.getName(), coordinate.getX(), coordinate.getY(),
								coordinate.getZ() });
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
				new Object[] { coordinate.getName(), coordinate.getX(), coordinate.getY(), coordinate.getZ(),
						coordinate.getId() });

	}

	@Override
	public List<Coordinate> findCoordinatesInCluster(Cluster cluster) {
		String sql = "SELECT * FROM coordinates WHERE cluster_id = ?";

		List<Coordinate> coordinates = getJdbcTemplate().query(sql, new Object[] { cluster.getId() },
				new CoordinateMapper());

		return coordinates;
	}

	private static class CoordinateMapper implements RowMapper<Coordinate> {
		@Override
		public Coordinate mapRow(ResultSet rs, int rowNum) throws SQLException {

			Coordinate coordinate = new Coordinate();

			coordinate.setId(rs.getLong("coordinate_id"));
			coordinate.setName(rs.getString("id"));
			coordinate.setX(rs.getObject("x") != null ? rs.getDouble("x") : null);
			coordinate.setY(rs.getObject("y") != null ? rs.getDouble("y") : null);
			coordinate.setZ(rs.getObject("z") != null ? rs.getDouble("z") : null);

			return coordinate;
		}
	}

}
