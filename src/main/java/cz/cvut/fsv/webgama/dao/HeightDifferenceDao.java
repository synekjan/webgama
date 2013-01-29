package cz.cvut.fsv.webgama.dao;

import java.util.List;

import cz.cvut.fsv.webgama.domain.AlternativeObservation;
import cz.cvut.fsv.webgama.domain.HeightDifference;

public interface HeightDifferenceDao {

	public void insert(HeightDifference heightDifference,
			Integer alternativeObservationId);

	public void delete(HeightDifference heightDifference);

	public void update(HeightDifference heightDifference);

	public List<HeightDifference> findHeightDifferencesInAlternativeObservation(
			AlternativeObservation alternativeObservation);

}
