package cz.cvut.fsv.webgama.dao;

import java.util.List;

import cz.cvut.fsv.webgama.domain.Calculation;
import cz.cvut.fsv.webgama.domain.CalculationPrivilege;

public interface CalculationPrivilegeDao {

	public Long insert(CalculationPrivilege calculationPrivilege, Long calculationId);

	public void delete(CalculationPrivilege calculationPrivilege);
	
	public void deleteById(Long id);

	public void update(CalculationPrivilege calculationPrivilege);

	public List<CalculationPrivilege> findAllPrivilegesforCalculation(Calculation calculation);
	
	public Boolean hasUserPrivilegeToCalculation(Long id, String username);

}
