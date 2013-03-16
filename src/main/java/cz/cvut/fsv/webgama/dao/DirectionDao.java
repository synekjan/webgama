package cz.cvut.fsv.webgama.dao;

import java.util.List;

import cz.cvut.fsv.webgama.domain.Direction;
import cz.cvut.fsv.webgama.domain.Observation;

public interface DirectionDao {

	public void insert(Direction direction, Long observationId);

	public void delete(Direction direction);

	public void update(Direction direction);

	public List<Direction> findDirectionsInObservation(Observation observation);
}
