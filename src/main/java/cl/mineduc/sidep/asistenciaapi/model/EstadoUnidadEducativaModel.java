package cl.mineduc.sidep.asistenciaapi.model;

import lombok.Data;

import java.util.Date;


@Data
public class EstadoUnidadEducativaModel {
    private Integer idEstadoUnidadEducativa;   // pk_eued_id_estado_unidad_educativa
    private String nombre;                     // eued_nombre
    private Date fechaCreacion;                // eued_fecha_creacion
    private Date fechaActualizacion;           // eued_fecha_actualizacion
}
