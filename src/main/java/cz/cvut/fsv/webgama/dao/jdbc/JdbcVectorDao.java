package cz.cvut.fsv.webgama.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import cz.cvut.fsv.webgama.dao.VectorDao;
import cz.cvut.fsv.webgama.domain.Cluster;
import cz.cvut.fsv.webgama.domain.Vector;

public class JdbcVectorDao extends JdbcDaoSupport implements VectorDao {

	@Override
	public void insert(Vector vector, Long clusterId) {
		String sql = "INSERT INTO vectors (cluster_id, from_id, to_id, dx, dy, dz, from_dh, to_dh) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		getJdbcTemplate().update(
				sql,
				new Object[] { clusterId, vector.getFrom(), vector.getTo(), vector.getDx(), vector.getDy(),
						vector.getDz(), vector.getFromDh(), vector.getToDh() });
	}

	@Override
	public void delete(Vector vector) {
		String sql = "DELETE FROM vectors WHERE vector_id = ?";

		getJdbcTemplate().update(sql, new Object[] { vector.getId() });

	}

	@Override
	public void update(Vector vector) {
		String sql = "UPDATE vectors SET from_id=?, to_id=?, dx=?, dy=?, dz=?, from_dh=?, to_dh=? WHERE vector_id=?";

		getJdbcTemplate().update(
				sql,
				new Object[] { vector.getFrom(), vector.getTo(), vector.getDx(), vector.getDy(), vector.getDz(),
						vector.getFromDh(), vector.getToDh(), vector.getId() });

	}

	@Override
	public List<Vector> findVectorsInCluster(Cluster cluster) {
		String sql = "SELECT * FROM vectors WHERE cluster_id = ?";

		List<Vector> vectors = getJdbcTemplate().query(sql, new Object[] { cluster.getId() }, new VectorMapper());

		return vectors;
	}

	private static class VectorMapper implements RowMapper<Vector> {
		@Override
		public Vector mapRow(ResultSet rs, int rowNum) throws SQLException {

			Vector vector = new Vector();

			vector.setId(rs.getLong("vector_id"));
			vector.setFrom(rs.getString("from_id"));
			vector.setTo(rs.getString("to_id"));
			vector.setDx(rs.getObject("dx") != null ? rs.getDouble("dx") : null);
			vector.setDy(rs.getObject("dy") != null ? rs.getDouble("dy") : null);
			vector.setDz(rs.getObject("dz") != null ? rs.getDouble("dz") : null);
			vector.setFromDh(rs.getObject("from_dh") != null ? rs.getDouble("from_dh") : null);
			vector.setToDh(rs.getObject("to_dh") != null ? rs.getDouble("to_dh") : null);

			return vector;
		}
	}
}
