package cl.mineduc.sidep.asistenciaapi.model;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class AsistenciaModel {
    private Long id;
    private String rbd;
    private String ensenanza;
    private String grado;
    private String letra;
    private Integer rut;
    private LocalDate fecha;
    private Boolean asistio;
    private LocalDateTime fechaRegistro;
    private LocalDateTime fechaActualizacion;
}
