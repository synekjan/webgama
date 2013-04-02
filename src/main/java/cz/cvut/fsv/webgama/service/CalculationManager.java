package cz.cvut.fsv.webgama.service;

import cz.cvut.fsv.webgama.domain.Calculation;
import cz.cvut.fsv.webgama.domain.ProcessOutput;

public interface CalculationManager {
	
	public ProcessOutput calculate(Calculation calculation, String username);
	
	public void deleteCalculation(Long id);
	
	public String checkCalculationProgress(Long id);

}
