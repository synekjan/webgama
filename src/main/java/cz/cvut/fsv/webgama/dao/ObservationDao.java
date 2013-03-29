package cz.cvut.fsv.webgama.dao;

import cz.cvut.fsv.webgama.domain.Cluster;
import cz.cvut.fsv.webgama.domain.Observation;

public interface ObservationDao {

	public void insert(Observation observation, Long clusterId);

	public void delete(Observation observation);

	public void update(Observation observation);

	public Observation findObservationInCluster(Cluster cluster);
}
