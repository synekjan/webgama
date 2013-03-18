package cz.cvut.fsv.webgama.service;

import cz.cvut.fsv.webgama.domain.Calculation;
import cz.cvut.fsv.webgama.domain.ProcessOutput;

public interface ProcessManager {

	public ProcessOutput runExternalGama(Calculation calculation, String username);

}
