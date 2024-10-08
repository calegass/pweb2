package web.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import web.validation.validator.TwoEqualAttributesValidator;

import java.lang.annotation.*;

@Constraint(validatedBy = TwoEqualAttributesValidator.class)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TwoEqualAttributes {
    String message() default "Os valores são diferentes";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String attribute1();

    String attribute2();

    @Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        TwoEqualAttributes[] value();
    }
}