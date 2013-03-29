package cz.cvut.fsv.webgama.dao;

import java.util.List;

import cz.cvut.fsv.webgama.domain.Network;
import cz.cvut.fsv.webgama.domain.Point;

public interface PointDao {

	public void insert(Point point, Long networkId);

	public void delete(Point point);

	public void update(Point point);

	public List<Point> findPointsInNetwork(Network network);
}
