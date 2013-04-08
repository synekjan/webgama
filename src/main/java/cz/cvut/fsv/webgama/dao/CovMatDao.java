package cz.cvut.fsv.webgama.dao;

import cz.cvut.fsv.webgama.domain.Cluster;
import cz.cvut.fsv.webgama.domain.CovMat;

public interface CovMatDao {
	
	public void insert(CovMat covMat, Long clusterId);

	public void delete(CovMat covMat);

	public void update(CovMat covMat);

	public CovMat findCovMatForCluster(Cluster cluster);

}
