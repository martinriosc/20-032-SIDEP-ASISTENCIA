package cl.mineduc.sidep.asistenciaapi.entities;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class CalendarioEntity {

    private Long id;          // pk_cale_id_calendario
    private Long grupoId;     // fk_cale_id_grupo
    private LocalDate fecha;  // cale_fecha
    private Boolean trabajado; // cale_trabajado
    private LocalDateTime fechaCreacion;      // cale_fecha_creacion
    private LocalDateTime fechaActualizacion; // cale_fecha_actualizacion
}
