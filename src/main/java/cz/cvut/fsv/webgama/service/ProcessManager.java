package cz.cvut.fsv.webgama.service;

import cz.cvut.fsv.webgama.domain.ProcessOutput;

public interface ProcessManager {

	public ProcessOutput runExternalGama(String feed, String username);

}
