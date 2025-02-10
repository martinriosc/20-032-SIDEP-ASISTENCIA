package cl.mineduc.sidep.asistenciaapi.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Representa un grupo dentro de una unidad educativa.
 */
@Data
public class GrupoModel {
    private Long id;                         // pk_grup_id_grupo
    private Long jornadaId;                  // fk_grup_id_jornada
    private Long gradoId;                    // fk_grup_id_grado

    private String letra;                    // grup_letra
    private Integer cupo;                    // grup_cupo

    private LocalDateTime fechaCreacion;     // grup_fecha_creacion
    private LocalDateTime fechaActualizacion; // grup_fecha_actualizacion
}
