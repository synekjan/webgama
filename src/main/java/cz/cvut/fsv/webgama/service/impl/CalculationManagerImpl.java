package cz.cvut.fsv.webgama.service.impl;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.transaction.annotation.Transactional;

import cz.cvut.fsv.webgama.dao.CalculationDao;
import cz.cvut.fsv.webgama.dao.CalculationPrivilegeDao;
import cz.cvut.fsv.webgama.dao.PointDao;
import cz.cvut.fsv.webgama.domain.Calculation;
import cz.cvut.fsv.webgama.domain.CalculationPrivilege;
import cz.cvut.fsv.webgama.domain.Output;
import cz.cvut.fsv.webgama.domain.Privilege;
import cz.cvut.fsv.webgama.domain.ProcessOutput;
import cz.cvut.fsv.webgama.domain.User;
import cz.cvut.fsv.webgama.service.CalculationManager;
import cz.cvut.fsv.webgama.service.ProcessManager;
import cz.cvut.fsv.webgama.service.UserManager;

public class CalculationManagerImpl implements CalculationManager {

	private CalculationDao calculationDao;

	private CalculationPrivilegeDao calculationPrivilegeDao;

	private ProcessManager processManager;

	private UserManager userManager;

	private PointDao pointDao;

	public void setCalculationDao(CalculationDao calculationDao) {
		this.calculationDao = calculationDao;
	}

	public void setProcessManager(ProcessManager processManager) {
		this.processManager = processManager;
	}

	public void setCalculationPrivilegeDao(CalculationPrivilegeDao calculationPrivilegeDao) {
		this.calculationPrivilegeDao = calculationPrivilegeDao;
	}

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	public void setPointDao(PointDao pointDao) {
		this.pointDao = pointDao;
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
	public void deleteCalculationPrivilege(Long id) {
		calculationPrivilegeDao.deleteById(id);
	}

	@Override
	public String checkCalculationProgress(Long id) {

		String progress = calculationDao.findProgressById(id);

		return progress;
	}

	@Override
	public boolean hasUserPrivilegeToCalculation(Long id, String username) {

		Calculation calculation = getCalculationById(id);

		// if user is owner
		if (username.equals(calculation.getUser().getUsername())) {
			return true;
		}

		// if user has permission
		if (calculationPrivilegeDao.hasUserPrivilegeToCalculation(id, username)) {
			return true;
		}

		return false;
	}

	@Override
	public Long insertUserPrivelegeToCalculation(Long calculationId, String username) {

		if (calculationPrivilegeDao.hasUserPrivilegeToCalculation(calculationId, username))
			return -2L;

		List<User> users = userManager.getUsersByUsername(username);
		if (users.isEmpty())
			return -1L;

		CalculationPrivilege calculationPrivilege = new CalculationPrivilege();
		calculationPrivilege.setPrivilege(new Privilege());
		calculationPrivilege.setUser(users.get(0));

		Long privilegeId = calculationPrivilegeDao.insert(calculationPrivilege, calculationId);

		return privilegeId;
	}

	@Transactional
	@Override
	public List<Calculation> getCalculationsbyUsername(String username) {

		return calculationDao.findCalculationsOnlyByUser(userManager.getUser(username));
	}

	@Transactional
	@Override
	public List<Calculation> getSharedCalculationsbyUsername(String username) {

		return calculationDao.findSharedCalculationsOnlyByUser(userManager.getUser(username));
	}

	@Transactional
	@Override
	public Long getCalculationCountByUsername(String username) {

		return calculationDao.countCalculationsByUser(userManager.getUser(username));
	}

	@Transactional
	@Override
	public Long getSharedCalculationCountByUsername(String username) {

		return calculationDao.countSharedCalculationsByUser(userManager.getUser(username));
	}

	@Transactional
	@Override
	public Calculation getCalculationById(long id) {

		return calculationDao.findCalculationById(id);
	}

	@Transactional
	@Override
	public boolean isCalculationIdInDB(Long id) {

		return calculationDao.isCalculationIdInDB(id);
	}

	@Override
	public Long getAllPointCount() {

		Long pointCount = pointDao.countAllPoints() + 14567;

		return pointCount;
	}

	@Override
	public Long getPointCountByUsername(String username) {

		User user = userManager.getUser(username);
		Long count = calculationDao.countPointsByUser(user.getId());
		return count == null ? 0 : count;
	}

	@Override
	public Long getClusterCountByUsername(String username) {

		User user = userManager.getUser(username);
		Long count = calculationDao.countClustersByUser(user.getId());
		return count == null ? 0 : count;
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
