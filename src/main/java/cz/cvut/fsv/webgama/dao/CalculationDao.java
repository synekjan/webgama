package cz.cvut.fsv.webgama.dao;

import java.util.List;

import cz.cvut.fsv.webgama.domain.Calculation;
import cz.cvut.fsv.webgama.domain.User;

public interface CalculationDao {
	
	public void insert(Calculation calculation);

	public void delete(Calculation calculation);

	public void update(Calculation calculation);

	public List<Calculation> getCalculationList();
	
	public List<Calculation> findCalculationsByUser(User user);
	
	public Calculation findCalculationById(Long id);
	
	public Long getCalculationCountByUser(User user);
	
	public boolean isCalculationIdInDB(Long id);

}
