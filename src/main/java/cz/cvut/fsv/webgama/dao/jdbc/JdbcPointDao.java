package cz.cvut.fsv.webgama.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import cz.cvut.fsv.webgama.dao.PointDao;
import cz.cvut.fsv.webgama.domain.Network;
import cz.cvut.fsv.webgama.domain.Point;

public class JdbcPointDao extends JdbcDaoSupport implements PointDao {

	@Override
	public void insert(Point point, Integer networkId) {

		String sql = "INSERT INTO points (network_id, id, x, y, z, fix, adj) VALUES (?, ?, ?, ?, ?, ?, ?)";

		getJdbcTemplate().update(
				sql,
				new Object[] { networkId, point.getName(), point.getX(),
						point.getY(), point.getZ(), point.getFix(),
						point.getAdj() });
	}

	@Override
	public void delete(Point point) {

		String sql = "DELETE FROM points WHERE point_id = ?";

		getJdbcTemplate().update(sql, new Object[] { point.getId() });
	}

	@Override
	public void update(Point point) {

		String sql = "UPDATE points SET id=?, x=?, y=?, z=?, fix=?, adj=? WHERE point_id=?";

		getJdbcTemplate().update(
				sql,
				new Object[] { point.getName(), point.getX(), point.getY(),
						point.getZ(), point.getFix(), point.getAdj(),
						point.getId() });
	}

	@Override
	public List<Point> findPointsInNetwork(Network network) {

		String sql = "SELECT * FROM points WHERE network_id = ?";

		List<Point> points = getJdbcTemplate().query(sql,
				new Object[] { network.getId() }, new PointMapper());

		return points;
	}

	private static class PointMapper implements RowMapper<Point> {

		@Override
		public Point mapRow(ResultSet rs, int rowNum) throws SQLException {

			Point point = new Point();

			point.setId(rs.getLong("point_id"));
			point.setName(rs.getString("id"));
			point.setX(rs.getObject("x") != null ? rs.getDouble("x") : null);
			point.setY(rs.getObject("y") != null ? rs.getDouble("y") : null);
			point.setZ(rs.getObject("z") != null ? rs.getDouble("z") : null);
			point.setFix(rs.getString("fix"));
			point.setAdj(rs.getString("adj"));

			return point;
		}
	}
}
