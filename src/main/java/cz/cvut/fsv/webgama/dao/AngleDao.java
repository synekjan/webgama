package cz.cvut.fsv.webgama.dao;

import java.util.List;

import cz.cvut.fsv.webgama.domain.Angle;
import cz.cvut.fsv.webgama.domain.Observation;

public interface AngleDao {

	public void insert(Angle angle, Integer observationId);

	public void delete(Angle angle);

	public void update(Angle angle);

	public List<Angle> findAnglesInObservation(Observation observation);
}
