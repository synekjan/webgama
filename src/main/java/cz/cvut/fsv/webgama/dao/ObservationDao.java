package cz.cvut.fsv.webgama.dao;

import java.util.List;

import cz.cvut.fsv.webgama.domain.Network;
import cz.cvut.fsv.webgama.domain.Observation;

public interface ObservationDao {
	
	public void insert(Observation observation, Integer networkId);

	public void delete(Observation observation);

	public void update(Observation observation);

	public List<Observation> findObservationsInNetwork(Network network);
}
