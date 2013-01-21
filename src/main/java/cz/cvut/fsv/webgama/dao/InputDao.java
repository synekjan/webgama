package cz.cvut.fsv.webgama.dao;

import java.util.List;

import cz.cvut.fsv.webgama.domain.Input;
import cz.cvut.fsv.webgama.domain.User;

public interface InputDao {

	public void insert(Input input);

	public void delete(Input input);

	public void update(Input input);

	public List<Input> getInputList();
	
	public List<Input> findInputsByUser(User user);
	
	public int getInputCountByUser(User user);

}
