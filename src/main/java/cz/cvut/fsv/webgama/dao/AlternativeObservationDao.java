package cz.cvut.fsv.webgama.dao;

import java.util.List;

import cz.cvut.fsv.webgama.domain.AlternativeObservation;
import cz.cvut.fsv.webgama.domain.Network;

public interface AlternativeObservationDao {

	public void insert(AlternativeObservation alternativeObservation,
			Integer networkId);

	public void delete(AlternativeObservation alternativeObservation);

	public void update(AlternativeObservation alternativeObservation);

	public List<AlternativeObservation> findAlternativeObservationsInNetwork(
			Network network);

}
