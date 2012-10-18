package cz.cvut.fsv.webgama.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import cz.cvut.fsv.webgama.dao.AngleDao;
import cz.cvut.fsv.webgama.dao.DirectionDao;
import cz.cvut.fsv.webgama.dao.DistanceDao;
import cz.cvut.fsv.webgama.dao.ObservationDao;
import cz.cvut.fsv.webgama.dao.SlopeDistanceDao;
import cz.cvut.fsv.webgama.dao.ZenithAngleDao;
import cz.cvut.fsv.webgama.domain.Angle;
import cz.cvut.fsv.webgama.domain.Direction;
import cz.cvut.fsv.webgama.domain.Distance;
import cz.cvut.fsv.webgama.domain.Network;
import cz.cvut.fsv.webgama.domain.Observation;
import cz.cvut.fsv.webgama.domain.SlopeDistance;
import cz.cvut.fsv.webgama.domain.ZenithAngle;

public class JdbcObservationDao extends JdbcDaoSupport implements
		ObservationDao {

	private DirectionDao directionDao;
	private DistanceDao distanceDao;
	private AngleDao angleDao;
	private SlopeDistanceDao slopeDistanceDao;
	private ZenithAngleDao zenithAngleDao;

	public void setDirectionDao(DirectionDao directionDao) {
		this.directionDao = directionDao;
	}

	public void setDistanceDao(DistanceDao distanceDao) {
		this.distanceDao = distanceDao;
	}

	public void setAngleDao(AngleDao angleDao) {
		this.angleDao = angleDao;
	}

	public void setSlopeDistanceDao(SlopeDistanceDao slopeDistanceDao) {
		this.slopeDistanceDao = slopeDistanceDao;
	}

	public void setZenithAngleDao(ZenithAngleDao zenithAngleDao) {
		this.zenithAngleDao = zenithAngleDao;
	}

	@Override
	public void insert(Observation observation, Integer networkId) {

		String sql = "INSERT INTO observations (network_id, from_id, orientation, from_dh) VALUES (?, ?, ?, ?)";

		getJdbcTemplate()
				.update(sql,
						new Object[] { networkId, observation.getFrom(),
								observation.getOrientation(),
								observation.getFromDh() });

		for (Direction direction : observation.getDirections()) {
			directionDao.insert(direction, observation.getId());
		}

		for (Distance distance : observation.getDistances()) {
			distanceDao.insert(distance, observation.getId());
		}

		for (Angle angle : observation.getAngles()) {
			angleDao.insert(angle, observation.getId());
		}

		for (SlopeDistance slopeDistance : observation.getSlopeDistances()) {
			slopeDistanceDao.insert(slopeDistance, observation.getId());
		}

		for (ZenithAngle zenithAngle : observation.getZenithAngles()) {
			zenithAngleDao.insert(zenithAngle, observation.getId());
		}
	}

	@Override
	public void delete(Observation observation) {

		String sql = "DELETE FROM observations WHERE observation_id = ?";

		getJdbcTemplate().update(sql, new Object[] { observation.getId() });
	}

	@Override
	public void update(Observation observation) {

		String sql = "UPDATE observations SET from_id=? orientation=? from_dh=? WHERE observation_id=?";

		getJdbcTemplate().update(
				sql,
				new Object[] { observation.getFrom(),
						observation.getOrientation(), observation.getFromDh(),
						observation.getId() });
	}

	@Override
	public List<Observation> findObservationsInNetwork(Network network) {

		String sql = "SELECT * FROM observations WHERE network_id = ?";

		List<Observation> observations = getJdbcTemplate().query(sql,
				new Object[] { network.getId() }, new ObservationMapper());

		return observations;
	}

	private class ObservationMapper implements RowMapper<Observation> {

		@Override
		public Observation mapRow(ResultSet rs, int rowNum) throws SQLException {

			Observation observation = new Observation();

			observation.setId(rs.getInt("observation_id"));
			observation.setFrom(rs.getString("from_id"));
			observation.setOrientation(rs.getString("orientation"));
			observation.setFromDh(rs.getDouble("from_dh"));
			observation.setDirections(directionDao
					.findDirectionsInObservation(observation));
			observation.setDistances(distanceDao
					.findDistancesInObservation(observation));
			observation
					.setAngles(angleDao.findAnglesInObservation(observation));
			observation.setSlopeDistances(slopeDistanceDao
					.findSlopeDistancesInObservation(observation));
			observation.setZenithAngles(zenithAngleDao
					.findZenithAnglesInObservation(observation));

			return observation;
		}

	}

}