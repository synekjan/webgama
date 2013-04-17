package cz.cvut.fsv.webgama.dao;

import cz.cvut.fsv.webgama.domain.Calculation;
import cz.cvut.fsv.webgama.domain.Output;

public interface OutputDao {

	public void insert(Output output, Long calculationId);

	public void delete(Output output);

	public void update(Output output);

	public Output findOutputInCalculation(Calculation calculation);
	
	public Output findOutputWithoutResultsInCalculation(Calculation calculation);

}
