package cz.cvut.fsv.webgama.dao;

import java.util.List;

import cz.cvut.fsv.webgama.domain.Calculation;
import cz.cvut.fsv.webgama.domain.User;

public interface CalculationDao {

	public void insert(Calculation calculation);

	public void delete(Calculation calculation);

	public void update(Calculation calculation);
	
	public void updateOutput(Calculation calculation);

	public List<Calculation> getCalculationList();

	public List<Calculation> findCalculationsByUser(User user);
	
	public List<Calculation> findCalculationsOnlyByUser(User user);
	
	public List<Calculation> findSharedCalculationsOnlyByUser(User user);

	public Calculation findCalculationById(Long id);
	
	public Calculation findCalculationByInputId(Long id);

	public Long countCalculationsByUser(User user);
	
	public Long countSharedCalculationsByUser(User user);
	
	public Long countPointsByUser(Long userId);
	
	public Long countClustersByUser(Long userId);

	public void deleteCalculationById(Long id);

	public boolean isCalculationIdInDB(Long id);
	
	public void updateProgress(Calculation calculation, String progress);
	
	public String findProgressById(Long id);
	
	public void updateCalculationName(Long id, String name);

}
