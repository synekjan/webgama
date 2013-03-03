package cz.cvut.fsv.webgama.dao;

import java.util.List;

import cz.cvut.fsv.webgama.domain.Cluster;
import cz.cvut.fsv.webgama.domain.Coordinate;

public interface CoordinateDao {

	public void insert(Coordinate coordinate, Integer clusterId);

	public void delete(Coordinate coordinate);

	public void update(Coordinate coordinate);

	public List<Coordinate> findCoordinatesInCluster(Cluster cluster);

}
