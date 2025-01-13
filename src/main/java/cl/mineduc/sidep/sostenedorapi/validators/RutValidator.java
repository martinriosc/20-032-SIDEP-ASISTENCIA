package cl.mineduc.sidep.sostenedorapi.validators;

import cl.mineduc.sidep.sostenedorapi.annotation.RutConstraint;
import cl.mineduc.sidep.sostenedorapi.model.SostenedorModel;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RutValidator implements ConstraintValidator<RutConstraint, Object> {
    @Override
    public void initialize(RutConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @SneakyThrows
    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {

        if (!(o instanceof SostenedorModel)) {
            throw new IllegalAccessException("@RutValidator solo aplica para SostenedorModel");
        }

        SostenedorModel s = (SostenedorModel) o;

        return this.validateRut(String.valueOf(s.getRut()), s.getDv());
    }

    private boolean validateRut(String rut, String dv) {
        try {
            int intRut = Integer.parseInt(rut.replace("\\.", ""));

            if (StringUtils.isNotBlank(dv)) {
                char dvChar = dv.charAt(0);

                int m = 0;
                int s = 1;
                for (; intRut != 0; intRut /= 10) {
                    s = (s + intRut % 10 * (9 - m++ % 6)) % 11;
                }
                if (dvChar == (char) (s != 0 ? s + 47 : 75)) {
                    return true;
                }

            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

}
