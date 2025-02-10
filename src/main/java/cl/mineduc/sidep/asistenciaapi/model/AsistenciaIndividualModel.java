package cl.mineduc.sidep.asistenciaapi.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class AsistenciaIndividualModel {

    private Long id;

    @NotBlank(message = "RBD no puede estar vacio")
    private String rbd;

    @NotBlank(message = "Ensenanza no puede estar vacio")
    private String ensenanza;

    @NotBlank(message = "Grado no puede estar vacio")
    private String grado;

    @NotBlank(message = "Letra no puede estar vacio")
    private String letra;

    @NotBlank(message = "Mes no puede estar vacio")
    private String mes;

    @NotBlank(message = "Dia no puede estar vacio")
    private String dia;

    @NotNull(message = "RUN no puede estar vacio")
    private Integer rut;

    @NotBlank(message = "Flag presente no puede estar vacio")
    private Boolean presente;

    private LocalDate fecha;

}
