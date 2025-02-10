package cl.mineduc.sidep.asistenciaapi.model;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class CalendarioModel {
    private Long id;
    private LocalDate fecha;
    private Boolean trabajado;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
}
