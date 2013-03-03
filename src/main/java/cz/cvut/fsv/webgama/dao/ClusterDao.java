package cz.cvut.fsv.webgama.dao;

import java.util.List;

import cz.cvut.fsv.webgama.domain.Cluster;
import cz.cvut.fsv.webgama.domain.Network;

public interface ClusterDao {

	public void insert(Cluster cluster, Integer networkId);

	public void delete(Cluster cluster);

	public void update(Cluster cluster);

	public List<Cluster> findClustersInNetwork(Network network);

}
