package cz.cvut.fsv.webgama.dao;

import java.util.List;

import cz.cvut.fsv.webgama.domain.Distance;
import cz.cvut.fsv.webgama.domain.Observation;

public interface DistanceDao {

	public void insert(Distance distance, Integer observationId);

	public void delete(Distance distance);

	public void update(Distance distance);

	public List<Distance> findDistancesInObservation(Observation observation);
}
