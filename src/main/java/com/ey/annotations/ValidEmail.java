package com.ey.annotations;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.ey.validator.EmailValidator;

/**
 * @author George.Rosario
 *
 */
@Documented
@Retention(RUNTIME)
@Target({ TYPE, FIELD, ANNOTATION_TYPE })
@Constraint(validatedBy = { EmailValidator.class })
public @interface ValidEmail {
    String message() default "Invalid email";
    Class<?>[] groups() default {}; 
    Class<? extends Payload>[] payload() default {};
}