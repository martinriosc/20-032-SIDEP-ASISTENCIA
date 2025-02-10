package cl.mineduc.sidep.asistenciaapi.entities;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AsistenciaEntity {

    private Long id; // pk_asis_id_asistencia

    private Long calendarioId;      // fk_asis_id_calendario
    private Long matriculaGrupoId;  // fk_asis_id_matricula_grupo

    // Si en tu DB realmente son dos columnas:
    // "asis_json_asistencia" y "asis_json_asistencia_2"
    // las mapeamos aquí como strings (o un tipo JSON si lo soporta):
    private String jsonAsistencia;   // asis_json_asistencia
    private String jsonAsistencia2;  // asis_json_asistencia_2

    private LocalDateTime fechaCreacion;      // asis_fecha_creacion
    private LocalDateTime fechaActualizacion; // asis_fecha_actualizacion
}
