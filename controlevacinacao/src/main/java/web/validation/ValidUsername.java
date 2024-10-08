package web.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import web.validation.validator.ValidUsernameValidator;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidUsernameValidator.class)
@Documented
public @interface ValidUsername {

    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

