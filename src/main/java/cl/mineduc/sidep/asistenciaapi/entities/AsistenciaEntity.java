package cl.mineduc.sidep.asistenciaapi.entities;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AsistenciaEntity {

    private Long id; // pk_asis_id_asistencia

    private Long calendarioId;      // fk_asis_id_calendario
    private Long matriculaGrupoId;  // fk_asis_id_matricula_grupo
    private LocalDateTime fechaCreacion;      // asis_fecha_creacion
    private LocalDateTime fechaActualizacion; // asis_fecha_actualizacion
}
