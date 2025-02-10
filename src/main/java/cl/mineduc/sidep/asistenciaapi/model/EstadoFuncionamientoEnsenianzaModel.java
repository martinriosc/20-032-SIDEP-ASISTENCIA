package cl.mineduc.sidep.asistenciaapi.model;

import lombok.Data;

import java.util.Date;

@Data
public class EstadoFuncionamientoEnsenianzaModel {
    private Integer idEstadoFuncionamientoEnsenianza;  // pk_efen_id_estado_funcionamiento_ensenianza
    private String nombre;                             // efen_nombre
    private Date fechaCreacion;                        // efen_nombre_fecha_creacion (nombre del campo según la tabla)
    private Date fechaActualizacion;                   // efen_fecha_actualizacion
}
