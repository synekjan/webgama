package cz.cvut.fsv.webgama.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import cz.cvut.fsv.webgama.dao.AlternativeObservationDao;
import cz.cvut.fsv.webgama.dao.NetworkDao;
import cz.cvut.fsv.webgama.dao.ObservationDao;
import cz.cvut.fsv.webgama.dao.PointDao;
import cz.cvut.fsv.webgama.domain.AlternativeObservation;
import cz.cvut.fsv.webgama.domain.Input;
import cz.cvut.fsv.webgama.domain.Network;
import cz.cvut.fsv.webgama.domain.Observation;
import cz.cvut.fsv.webgama.domain.Point;

public class JdbcNetworkDao extends JdbcDaoSupport implements NetworkDao {

	private PointDao pointDao;
	private ObservationDao observationDao;
	private AlternativeObservationDao alternativeObservationDao;

	public void setPointDao(PointDao pointDao) {
		this.pointDao = pointDao;
	}

	public void setObservationDao(ObservationDao observationDao) {
		this.observationDao = observationDao;
	}

	public void setAlternativeObservationDao(
			AlternativeObservationDao alternativeObservationDao) {
		this.alternativeObservationDao = alternativeObservationDao;
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

		for (Observation observation : network.getObservations()) {
			observationDao.insert(observation, networkId);
		}

		for (AlternativeObservation alternativeObservation : network
				.getAlternativeObservations()) {
			alternativeObservationDao.insert(alternativeObservation, networkId);
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
			network.setEpoch(rs.getDouble("epoch"));
			network.setDescription(rs.getString("description"));
			network.setSigmaApr(rs.getDouble("sigma_apr"));
			network.setConfPr(rs.getDouble("conf_pr"));
			network.setTolAbs(rs.getDouble("tol_abs"));
			network.setSigmaAct(rs.getString("sigma_act"));
			network.setUpdateCC(rs.getString("update_cc"));
			network.setDirectionStdev(rs.getString("direction_stdev"));
			network.setAngleStdev(rs.getString("angle_stdev"));
			network.setZenithAngleStdev(rs.getString("zenith_angle_stdev"));
			network.setDistanceStdev(rs.getString("distance_stdev"));
			network.setPoints(pointDao.findPointsInNetwork(network));
			network.setObservations(observationDao
					.findObservationsInNetwork(network));

			return network;
		}

	}

}
