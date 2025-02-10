package cl.mineduc.sidep.asistenciaapi.entities;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GrupoEntity {

    private Long id;         // pk_grup_id_grupo

    private Long jornadaId;  // fk_grup_id_jornada
    private Long gradoId;    // fk_grup_id_grado

    private String letra;    // grup_letra
    private Integer cupo;    // grup_cupo

    // En la documentación aparecen dos campos con el mismo nombre (grup_fecha_actualizacion).
    // Asumimos que uno es fecha de creación y otro es fecha de última actualización:
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
}
