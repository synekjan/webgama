package cz.cvut.fsv.webgama.dao;

import cz.cvut.fsv.webgama.domain.Input;
import cz.cvut.fsv.webgama.domain.Network;

public interface NetworkDao {

	public void insert(Network network, Long inputId);

	public void delete(Network network);

	public void update(Network network);
	
	public Network findNetworkByInput(Input input);
}
