package cz.cvut.fsv.webgama.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import cz.cvut.fsv.webgama.form.UploadForm;

public class UploadValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return UploadForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		UploadForm uploadForm = (UploadForm) target;

		// check if file was added
		if (uploadForm.getFile().isEmpty()) {
			errors.rejectValue("file", "Empty", "no file was chosen");
		}

		// check if file is smaller than 10MB
		if (uploadForm.getFile().getSize() > 10485760L) {
			errors.rejectValue("file", "Larger", "uploaded file is larger than 10MB");
		}
	}
}
