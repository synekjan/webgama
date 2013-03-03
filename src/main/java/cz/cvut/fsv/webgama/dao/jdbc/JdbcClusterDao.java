package cz.cvut.fsv.webgama.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import cz.cvut.fsv.webgama.dao.ClusterDao;
import cz.cvut.fsv.webgama.dao.CoordinateDao;
import cz.cvut.fsv.webgama.dao.HeightDifferenceDao;
import cz.cvut.fsv.webgama.dao.ObservationDao;
import cz.cvut.fsv.webgama.dao.VectorDao;
import cz.cvut.fsv.webgama.domain.Cluster;
import cz.cvut.fsv.webgama.domain.Coordinate;
import cz.cvut.fsv.webgama.domain.HeightDifference;
import cz.cvut.fsv.webgama.domain.Network;
import cz.cvut.fsv.webgama.domain.Observation;
import cz.cvut.fsv.webgama.domain.Vector;

public class JdbcClusterDao extends JdbcDaoSupport implements ClusterDao {

	private CoordinateDao coordinateDao;
	private HeightDifferenceDao heightDifferenceDao;
	private VectorDao vectorDao;
	private ObservationDao observationDao;

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

	public void setObservationDao(ObservationDao observationDao) {
		this.observationDao = observationDao;
	}

	@Override
	public void insert(Cluster cluster, Integer networkId) {

		String sql = "INSERT INTO clusters (network_id, tagname) VALUES (?, ?) RETURNING cluster_id";

		int clusterId = getJdbcTemplate().queryForInt(sql,
				new Object[] { networkId, cluster.getTagname() });

		switch (cluster.getTagname()) {
		case "obs":
			for (Observation observation : cluster.getObservations()) {
				observationDao.insert(observation, clusterId);
			}
			break;
		case "coordinates":
			for (Coordinate coordinate : cluster.getCoordinates()) {
				coordinateDao.insert(coordinate, clusterId);
			}
			break;
		case "height-differences":
			for (HeightDifference heightDifference : cluster
					.getHeightDifferences()) {
				heightDifferenceDao.insert(heightDifference, clusterId);
			}
			break;
		case "vectors":
			for (Vector vector : cluster.getVectors()) {
				vectorDao.insert(vector, clusterId);
			}
			break;
		default:
			logger.error("Unrecognized cluster during inserting - "
					+ cluster.getTagname());
			break;
		}
	}

	@Override
	public void delete(Cluster cluster) {
		String sql = "DELETE FROM clusters WHERE cluster_id = ?";

		getJdbcTemplate().update(sql, new Object[] { cluster.getId() });

	}

	@Override
	public void update(Cluster cluster) {
		String sql = "UPDATE clusters SET tagname=? WHERE cluster_id=?";

		getJdbcTemplate().update(sql,
				new Object[] { cluster.getTagname(), cluster.getId() });

	}

	@Override
	public List<Cluster> findClustersInNetwork(Network network) {

		String sql = "SELECT * FROM clusters WHERE network_id = ?";

		List<Cluster> clusters = getJdbcTemplate().query(sql,
				new Object[] { network.getId() }, new ClusterMapper());

		return clusters;
	}

	private class ClusterMapper implements RowMapper<Cluster> {

		@Override
		public Cluster mapRow(ResultSet rs, int rowNum) throws SQLException {

			Cluster cluster = new Cluster();

			cluster.setId(rs.getLong("cluster_id"));
			cluster.setTagname(rs.getString("tagname"));
			/* cluster.setCovmat(rs.getObject("covmat_id")); */
			cluster.setCoordinates(coordinateDao
					.findCoordinatesInCluster(cluster));
			cluster.setHeightDifferences(heightDifferenceDao
					.findHeightDifferencesInCluster(cluster));
			cluster.setVectors(vectorDao.findVectorsInCluster(cluster));
			cluster.setObservations(observationDao
					.findObservationsInCluster(cluster));

			return cluster;
		}
	}

}
