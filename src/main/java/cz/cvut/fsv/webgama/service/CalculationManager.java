package cz.cvut.fsv.webgama.service;

import java.util.List;

import cz.cvut.fsv.webgama.domain.Calculation;
import cz.cvut.fsv.webgama.domain.ProcessOutput;

public interface CalculationManager {

	public ProcessOutput calculate(Calculation calculation, String username);

	public void deleteCalculation(Long id);

	public void deleteCalculationPrivilege(Long id);

	public String checkCalculationProgress(Long id);

	public boolean hasUserPrivilegeToCalculation(Long id, String username);

	public Long insertUserPrivelegeToCalculation(Long calculationId, String username);

	public Long getCalculationCountByUsername(String username);

	public Long getSharedCalculationCountByUsername(String username);

	public List<Calculation> getCalculationsbyUsername(String username);

	public List<Calculation> getSharedCalculationsbyUsername(String username);

	public Calculation getCalculationById(long id);

	public boolean isCalculationIdInDB(Long id);

	public Long getAllPointCount();

	public Long getPointCountByUsername(String username);

	public Long getClusterCountByUsername(String username);
	
	public void changeCalculationName(Long id, String name);

}
