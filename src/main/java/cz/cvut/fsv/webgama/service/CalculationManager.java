package cz.cvut.fsv.webgama.service;

import cz.cvut.fsv.webgama.domain.Calculation;

public interface CalculationManager {
	
	public void calculate(Calculation calculation, String username);
	
	public void deleteCalculation(Long id);

}
