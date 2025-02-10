package cl.mineduc.sidep.asistenciaapi.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Representa un grado (nivel de parvularia) a nivel de modelo de servicio.
 */
@Data
public class GradoModel {
    private Long id;                         // pk_grad_id_grado
    private Long unidadEducativaId;          // fk_grad_id_unidad_educativa (si lo expones)

    private String nombre;                   // grad_nombre

    private LocalDateTime fechaCreacion;     // grad_fecha_creacion
    private LocalDateTime fechaActualizacion; // grad_fecha_actualizacion
}
