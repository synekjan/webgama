package cz.cvut.fsv.webgama.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DoubleDigitsValidator.class)
public @interface DoubleDigits {

	String message() default "{cz.cvut.fsv.webgama.validator.DoubleDigits.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
