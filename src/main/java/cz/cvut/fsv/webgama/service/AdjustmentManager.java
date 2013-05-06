package cz.cvut.fsv.webgama.service;

import java.util.Locale;

import org.springframework.web.multipart.MultipartFile;

import cz.cvut.fsv.webgama.domain.Calculation;
import cz.cvut.fsv.webgama.domain.Input;
import cz.cvut.fsv.webgama.domain.ProcessOutput;
import cz.cvut.fsv.webgama.form.AdjustmentPageForm;

public interface AdjustmentManager {

	public ProcessOutput adjustFromFile(MultipartFile file, String username, Locale locale);

	public void insertNewCalculation(AdjustmentPageForm adjustmentForm, String username, Locale locale);

	public void updateInputInCalculation(AdjustmentPageForm adjustmentForm, Calculation calculation);

	public void handleWizardForm(Input input, String username, Locale locale);

}
