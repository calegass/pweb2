package web.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import web.validation.util.AttributesRelation;
import web.validation.validator.BigDecimalAttributesRelationValidator;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = BigDecimalAttributesRelationValidator.class)
@Documented
public @interface BigDecimalAttributesRelation {

    String attribute1();

    String attribute2();

    AttributesRelation relation();

    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        BigDecimalAttributesRelation[] value();
    }

}