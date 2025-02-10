package cl.mineduc.sidep.asistenciaapi.model;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Representa un calendario asociado a la asistencia (si se maneja a ese nivel).
 */
@Data
public class CalendarioModel {
    private Long id;                 // pk_cale_id_calendario
    private LocalDate fecha;         // cale_fecha
    private Boolean trabajado;       // cale_trabajado
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
}
