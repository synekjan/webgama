package cz.cvut.fsv.webgama.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -3430083339780313342L;
	
	public ResourceNotFoundException() {
		
	}
	
	public ResourceNotFoundException(String exceptionMessage) {
		super(exceptionMessage);
	}
}
