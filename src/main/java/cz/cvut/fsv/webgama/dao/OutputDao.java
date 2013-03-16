package cz.cvut.fsv.webgama.dao;

import cz.cvut.fsv.webgama.domain.Calculation;
import cz.cvut.fsv.webgama.domain.Output;

public interface OutputDao {
	
	public Output findOutputInCalculation(Calculation calculation);

}
