package cz.cvut.fsv.webgama.dao;

import java.util.List;

import cz.cvut.fsv.webgama.domain.Observation;
import cz.cvut.fsv.webgama.domain.SlopeDistance;

public interface SlopeDistanceDao {

	public void insert(SlopeDistance slopeDistance, Long observationId);

	public void delete(SlopeDistance slopeDistance);

	public void update(SlopeDistance slopeDistance);

	public List<SlopeDistance> findSlopeDistancesInObservation(
			Observation observation);

}
