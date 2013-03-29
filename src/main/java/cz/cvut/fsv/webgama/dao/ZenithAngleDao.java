package cz.cvut.fsv.webgama.dao;

import java.util.List;

import cz.cvut.fsv.webgama.domain.Observation;
import cz.cvut.fsv.webgama.domain.ZenithAngle;

public interface ZenithAngleDao {

	public void insert(ZenithAngle zenithAngle, Long observationId);

	public void delete(ZenithAngle zenithAngle);

	public void update(ZenithAngle zenithAngle);

	public List<ZenithAngle> findZenithAnglesInObservation(Observation observation);
}
