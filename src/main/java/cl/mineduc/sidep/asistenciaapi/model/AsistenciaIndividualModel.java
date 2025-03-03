package cl.mineduc.sidep.asistenciaapi.model;

import cl.mineduc.sidep.asistenciaapi.enums.TipoJornada;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class AsistenciaIndividualModel {

    private Long id;

    @NotNull(message = "RBD no puede estar vacio")
    private Integer rbd;

    @NotNull(message = "Grado no puede estar vacio")
    private Long grado;

    @NotBlank(message = "Letra no puede estar vacio")
    private String letra;

    @NotBlank(message = "Mes no puede estar vacio")
    private String mes;

    @NotBlank(message = "Dia no puede estar vacio")
    private String dia;

    @NotNull(message = "RUN no puede estar vacio")
    private Integer rut;

    @NotNull(message = "Flag presente no puede estar vacio")
    private Boolean presente;

    private Long calendarioId;

    private TipoJornada jornada;

}
