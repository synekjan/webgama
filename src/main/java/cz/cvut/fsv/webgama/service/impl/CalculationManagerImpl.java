package cz.cvut.fsv.webgama.service.impl;

import org.joda.time.DateTime;

import cz.cvut.fsv.webgama.dao.CalculationDao;
import cz.cvut.fsv.webgama.domain.Calculation;
import cz.cvut.fsv.webgama.domain.Output;
import cz.cvut.fsv.webgama.domain.ProcessOutput;
import cz.cvut.fsv.webgama.service.CalculationManager;
import cz.cvut.fsv.webgama.service.ProcessManager;

public class CalculationManagerImpl implements CalculationManager {

	private CalculationDao calculationDao;

	private ProcessManager processManager;

	public void setCalculationDao(CalculationDao calculationDao) {
		this.calculationDao = calculationDao;
	}

	public void setProcessManager(ProcessManager processManager) {
		this.processManager = processManager;
	}

	@Override
	public ProcessOutput calculate(Calculation calculation, String username) {

		ProcessOutput processOutput = processManager.runExternalGama(calculation, username);

		Output output = new Output();
		output.setTime(new DateTime());
		output.setRunningTime(processOutput.getRunningTime());
		if (processOutput.getExitValue() != 0) {
			String errorStreamMessage = processOutput.getErrorMessage();
			if (errorStreamMessage == null || "".equals(errorStreamMessage)) {
				output.setLastError(processOutput.getXmlResult());
			} else {
				output.setLastError(errorStreamMessage);
			}
			calculation.setOutput(output);
			calculationDao.updateOutput(calculation);
		} else {
			output.setXmlContent(processOutput.getXmlResult());
			output.setHtmlContent(processOutput.getHtmlResult());
			output.setSvgContent(processOutput.getSvgResult());
			output.setTextContent(processOutput.getTextResult());
			calculation.setOutput(output);
			calculation.setProgress("calculated");
			calculationDao.updateOutput(calculation);
		}

		return processOutput;
	}

	@Override
	public void deleteCalculation(Long id) {
		calculationDao.deleteCalculationById(id);
	}

	@Override
	public String checkCalculationProgress(Long id) {

		String progress = calculationDao.findProgressById(id);

		return progress;
	}

	/*
	 * @Async public void exampleAsync() {
	 * 
	 * System.out.println("Start --- " + Thread.currentThread().getName());
	 * 
	 * long i = 0; while (i < 100000000L) { i++; }
	 * 
	 * System.out.println("Konec --- " + Thread.currentThread().getName()); }
	 */
}
