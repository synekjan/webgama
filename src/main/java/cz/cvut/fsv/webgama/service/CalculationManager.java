package cz.cvut.fsv.webgama.service;

public interface CalculationManager {
	
	public void calculate(Long id, String username);
	
	public void deleteCalculation(Long id);

}
