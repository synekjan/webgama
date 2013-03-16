package cz.cvut.fsv.webgama.dao;

import cz.cvut.fsv.webgama.domain.Calculation;
import cz.cvut.fsv.webgama.domain.Input;

public interface InputDao {

	public void insert(Input input, Long calculationId);

	public void delete(Input input);

	public void update(Input input);

	public Input findInputById(Long id);

	public Input findInputInCalculation(Calculation calculation);

}
