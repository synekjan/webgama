package cz.cvut.fsv.webgama.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import cz.cvut.fsv.webgama.dao.AlternativeObservationDao;
import cz.cvut.fsv.webgama.dao.CoordinateDao;
import cz.cvut.fsv.webgama.dao.HeightDifferenceDao;
import cz.cvut.fsv.webgama.dao.VectorDao;
import cz.cvut.fsv.webgama.domain.AlternativeObservation;
import cz.cvut.fsv.webgama.domain.Coordinate;
import cz.cvut.fsv.webgama.domain.HeightDifference;
import cz.cvut.fsv.webgama.domain.Network;
import cz.cvut.fsv.webgama.domain.Vector;

public class JdbcAlternativeObservationDao extends JdbcDaoSupport implements
		AlternativeObservationDao {

	private CoordinateDao coordinateDao;
	private HeightDifferenceDao heightDifferenceDao;
	private VectorDao vectorDao;

	public void setCoordinateDao(JdbcCoordinateDao coordinateDao) {
		this.coordinateDao = coordinateDao;
	}

	public void setHeightDifferenceDao(
			JdbcHeightDifferenceDao heightDifferenceDao) {
		this.heightDifferenceDao = heightDifferenceDao;
	}

	public void setVectorDao(JdbcVectorDao vectorDao) {
		this.vectorDao = vectorDao;
	}

	@Override
	public void insert(AlternativeObservation alternativeObservation,
			Integer networkId) {

		String sql = "INSERT INTO alternative_observations (network_id, tagname) VALUES (?, ?) RETURNING alternative_observation_id";

		int alternativeObservationId = getJdbcTemplate()
				.queryForInt(
						sql,
						new Object[] { networkId,
								alternativeObservation.getTagname() });

		switch (alternativeObservation.getTagname()) {
		case "coordinates":
			for (Coordinate coordinate : alternativeObservation
					.getCoordinates()) {
				coordinateDao.insert(coordinate, alternativeObservationId);
			}
			break;
		case "height-differences":
			for (HeightDifference heightDifference : alternativeObservation
					.getHeightDifferences()) {
				heightDifferenceDao.insert(heightDifference,
						alternativeObservationId);
			}
			break;
		case "vectors":
			for (Vector vector : alternativeObservation.getVectors()) {
				vectorDao.insert(vector, alternativeObservationId);
			}
			break;
		default:
			logger.error("Unrecognized alternative observation during inserting - "
					+ alternativeObservation.getTagname());
			break;
		}
	}

	@Override
	public void delete(AlternativeObservation alternativeObservation) {
		String sql = "DELETE FROM alternative_observations WHERE alternative_observation_id = ?";

		getJdbcTemplate().update(sql,
				new Object[] { alternativeObservation.getId() });

	}

	@Override
	public void update(AlternativeObservation alternativeObservation) {
		String sql = "UPDATE alternative_observations SET tagname=? WHERE alternative_observation_id=?";

		getJdbcTemplate().update(
				sql,
				new Object[] { alternativeObservation.getTagname(),
						alternativeObservation.getId() });

	}

	@Override
	public List<AlternativeObservation> findAlternativeObservationsInNetwork(
			Network network) {

		String sql = "SELECT * FROM alternative_observations WHERE network_id = ?";

		List<AlternativeObservation> alternativeObservations = getJdbcTemplate()
				.query(sql, new Object[] { network.getId() },
						new AlternativeObservationMapper());

		return alternativeObservations;
	}

	private class AlternativeObservationMapper implements
			RowMapper<AlternativeObservation> {

		@Override
		public AlternativeObservation mapRow(ResultSet rs, int rowNum)
				throws SQLException {

			AlternativeObservation alternativeObservation = new AlternativeObservation();

			alternativeObservation.setId(rs
					.getLong("alternative_observation_id"));
			alternativeObservation.setTagname(rs.getString("tagname"));
			alternativeObservation
					.setCoordinates(coordinateDao
							.findCoordinatesInAlternativeObservation(alternativeObservation));
			alternativeObservation
					.setHeightDifferences(heightDifferenceDao
							.findHeightDifferencesInAlternativeObservation(alternativeObservation));
			alternativeObservation
					.setVectors(vectorDao
							.findVectorsInAlternativeObservation(alternativeObservation));

			return null;
		}

	}

}
