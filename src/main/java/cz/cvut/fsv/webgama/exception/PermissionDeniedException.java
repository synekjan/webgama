package cz.cvut.fsv.webgama.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class PermissionDeniedException extends RuntimeException {

	private static final long serialVersionUID = -775370151006004485L;
	
	public PermissionDeniedException() {
		
	}
	
	public PermissionDeniedException(String exceptionMessage) {
		super(exceptionMessage);
	}
}
