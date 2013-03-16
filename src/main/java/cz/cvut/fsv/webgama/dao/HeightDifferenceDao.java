package cz.cvut.fsv.webgama.dao;

import java.util.List;

import cz.cvut.fsv.webgama.domain.Cluster;
import cz.cvut.fsv.webgama.domain.HeightDifference;

public interface HeightDifferenceDao {

	public void insert(HeightDifference heightDifference, Long clusterId);

	public void delete(HeightDifference heightDifference);

	public void update(HeightDifference heightDifference);

	public List<HeightDifference> findHeightDifferencesInCluster(Cluster cluster);

}
