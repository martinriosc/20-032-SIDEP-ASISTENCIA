package cl.mineduc.sidep.sostenedorapi.annotation;

import cl.mineduc.sidep.sostenedorapi.validators.RutValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = RutValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RutConstraint {

    String message() default "{run.invalid}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
