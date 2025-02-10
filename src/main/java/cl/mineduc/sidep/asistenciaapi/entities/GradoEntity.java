package cl.mineduc.sidep.asistenciaapi.entities;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GradoEntity {

    private Long id;  // pk_grad_id_grado

    private Long unidadEducativaId; // fk_grad_id_unidad_educativa

    private String nombre;          // grad_nombre

    private LocalDateTime fechaCreacion;      // grad_fecha_creacion
    private LocalDateTime fechaActualizacion; // grad_fecha_actualizacion
}
