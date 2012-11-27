package cz.cvut.fsv.webgama.service;

import cz.cvut.fsv.webgama.domain.Input;

public interface ProcessManager {
	
	public String runExternalGama(Input input, String username);

}
