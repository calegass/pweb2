package web.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import web.validation.util.AttributesRelation;
import web.validation.validator.DoubleAttributesRelationValidator;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DoubleAttributesRelationValidator.class)
@Documented
public @interface DoubleAttributesRelation {

    String attribute1();

    String attribute2();

    AttributesRelation relation();

    double epsilon() default 0.0001;

    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        DoubleAttributesRelation[] value();
    }

}