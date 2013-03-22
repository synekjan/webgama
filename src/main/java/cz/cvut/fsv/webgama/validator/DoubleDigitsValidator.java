package cz.cvut.fsv.webgama.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DoubleDigitsValidator implements ConstraintValidator<DoubleDigits, Double> {

	@Override
	public void initialize(DoubleDigits constraintAnnotation) {

	}

	@Override
	public boolean isValid(Double value, ConstraintValidatorContext context) {

		return true;

	}
}
