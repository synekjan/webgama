package cz.cvut.fsv.webgama.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import cz.cvut.fsv.webgama.domain.Input;

public interface AdjustmentManager {
	
	public String adjustFromFile(MultipartFile file, String username);
	
	public List<Input> getInputsbyUsername(String username);
	
	public Input getInputById(long id);
	
	public int getInputCountbyUsername(String username);

}
