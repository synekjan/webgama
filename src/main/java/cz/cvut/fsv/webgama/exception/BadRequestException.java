package cz.cvut.fsv.webgama.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

	private static final long serialVersionUID = -4258673901184543937L;
	
	public BadRequestException() {
		
	}
	
	public BadRequestException(String exceptionMessage) {
		super(exceptionMessage);
	}
}
