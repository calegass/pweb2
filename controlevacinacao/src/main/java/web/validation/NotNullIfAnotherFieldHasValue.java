package web.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import web.validation.validator.NotNullIfAnotherFieldHasValueValidator;

import java.lang.annotation.*;

/**
 * Validates that field {@code dependFieldName} is not null if field
 * {@code fieldName} has value {@code fieldValue}.
 */
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotNullIfAnotherFieldHasValueValidator.class)
@Documented
public @interface NotNullIfAnotherFieldHasValue {

    String fieldName();

    String fieldValue();

    String dependFieldName();

    String message() default "{NotNullIfAnotherFieldHasValue.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        NotNullIfAnotherFieldHasValue[] value();
    }
}