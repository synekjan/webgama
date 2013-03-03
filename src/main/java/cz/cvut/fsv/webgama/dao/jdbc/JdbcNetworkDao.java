package cz.cvut.fsv.webgama.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import cz.cvut.fsv.webgama.dao.ClusterDao;
import cz.cvut.fsv.webgama.dao.NetworkDao;
import cz.cvut.fsv.webgama.dao.PointDao;
import cz.cvut.fsv.webgama.domain.Cluster;
import cz.cvut.fsv.webgama.domain.Input;
import cz.cvut.fsv.webgama.domain.Network;
import cz.cvut.fsv.webgama.domain.Point;

public class JdbcNetworkDao extends JdbcDaoSupport implements NetworkDao {

	private PointDao pointDao;
	private ClusterDao clusterDao;

	public void setPointDao(PointDao pointDao) {
		this.pointDao = pointDao;
	}

	public void setClusterDao(ClusterDao clusterDao) {
		this.clusterDao = clusterDao;
	}

	@Override
	public void insert(Network network, Integer inputId) {

		String sql = "INSERT INTO networks (input_id, axes_xy, angles, epoch, description, sigma_apr, conf_pr, tol_abs, sigma_act, update_cc, direction_stdev, angle_stdev, zenith_angle_stdev, distance_stdev) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING network_id";

		int networkId = getJdbcTemplate().queryForInt(
				sql,
				new Object[] { inputId, network.getAxesXY(),
						network.getAngles(), network.getEpoch(),
						network.getDescription(), network.getSigmaApr(),
						network.getConfPr(), network.getTolAbs(),
						network.getSigmaAct(), network.getUpdateCC(),
						network.getDirectionStdev(), network.getAngleStdev(),
						network.getZenithAngleStdev(),
						network.getDistanceStdev() });

		for (Point point : network.getPoints()) {
			pointDao.insert(point, networkId);
		}

		for (Cluster cluster : network.getClusters()) {
			clusterDao.insert(cluster, networkId);
		}

	}

	@Override
	public void delete(Network network) {

		String sql = "DELETE FROM networks WHERE network_id = ?";

		getJdbcTemplate().update(sql, new Object[] { network.getId() });

	}

	@Override
	public void update(Network network) {

		String sql = "UPDATE networks SET axes_xy=?, angles=?, epoch=?, description=?, sigma_apr=?, conf_pr=?, tol_abs=?, sigma_act=?, update_cc=?, direction_stdev=?, angle_stdev=?, zenith_angle_stdev=?, distance_stdev=? WHERE network_id=?";

		getJdbcTemplate().update(
				sql,
				new Object[] { network.getAxesXY(), network.getAngles(),
						network.getEpoch(), network.getDescription(),
						network.getSigmaApr(), network.getConfPr(),
						network.getTolAbs(), network.getSigmaAct(),
						network.getUpdateCC(), network.getDirectionStdev(),
						network.getAngleStdev(), network.getZenithAngleStdev(),
						network.getDistanceStdev(), network.getId() });
	}

	@Override
	public Network findNetworkByInput(Input input) {

		String sql = "SELECT * FROM networks WHERE input_id = ?";

		Network network = getJdbcTemplate().queryForObject(sql,
				new Object[] { input.getId() }, new NetworkMapper());

		return network;
	}

	private class NetworkMapper implements RowMapper<Network> {

		@Override
		public Network mapRow(ResultSet rs, int rowNum) throws SQLException {

			Network network = new Network();

			network.setId(rs.getLong("network_id"));
			network.setAxesXY(rs.getString("axes_xy"));
			network.setAngles(rs.getString("angles"));
			network.setEpoch(rs.getObject("epoch") != null ? rs
					.getDouble("epoch") : null);
			network.setDescription(rs.getString("description"));
			network.setSigmaApr(rs.getObject("sigma_apr") != null ? rs
					.getDouble("sigma_apr") : null);
			network.setConfPr(rs.getObject("conf_pr") != null ? rs
					.getDouble("conf_pr") : null);
			network.setTolAbs(rs.getObject("tol_abs") != null ? rs
					.getDouble("tol_abs") : null);
			network.setSigmaAct(rs.getString("sigma_act"));
			network.setUpdateCC(rs.getString("update_cc"));
			network.setDirectionStdev(rs.getString("direction_stdev"));
			network.setAngleStdev(rs.getString("angle_stdev"));
			network.setZenithAngleStdev(rs.getString("zenith_angle_stdev"));
			network.setDistanceStdev(rs.getString("distance_stdev"));
			network.setPoints(pointDao.findPointsInNetwork(network));
			network.setClusters(clusterDao.findClustersInNetwork(network));

			return network;
		}
	}

}
