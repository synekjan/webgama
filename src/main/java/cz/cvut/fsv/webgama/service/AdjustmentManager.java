package cz.cvut.fsv.webgama.service;

import org.springframework.web.multipart.MultipartFile;

public interface AdjustmentManager {
	
	public void adjustFromFile(MultipartFile file, String username);

}
