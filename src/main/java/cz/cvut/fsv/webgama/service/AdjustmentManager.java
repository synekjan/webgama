package cz.cvut.fsv.webgama.service;

import java.util.List;
import java.util.Locale;

import org.springframework.web.multipart.MultipartFile;

import cz.cvut.fsv.webgama.domain.Calculation;
import cz.cvut.fsv.webgama.domain.ProcessOutput;
import cz.cvut.fsv.webgama.form.AdjustmentPageForm;

public interface AdjustmentManager {

	public ProcessOutput adjustFromFile(MultipartFile file, String username, Locale locale);

	public List<Calculation> getCalculationsbyUsername(String username);

	public Calculation getCalculationById(long id);

	public long getCalculationCountbyUsername(String username);

	public boolean isCalculationIdInDB(Long id);
	
	public void insertNewCalculation(AdjustmentPageForm adjustmentForm, String username, Locale locale);
	
	public void updateInputInCalculation(AdjustmentPageForm adjustmentForm, Calculation calculation);

}
