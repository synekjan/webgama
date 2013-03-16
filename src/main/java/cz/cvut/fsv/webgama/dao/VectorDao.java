package cz.cvut.fsv.webgama.dao;

import java.util.List;

import cz.cvut.fsv.webgama.domain.Cluster;
import cz.cvut.fsv.webgama.domain.Vector;

public interface VectorDao {

	public void insert(Vector vector, Long clusterId);

	public void delete(Vector vector);

	public void update(Vector vector);

	public List<Vector> findVectorsInCluster(Cluster cluster);
}
