package cl.mineduc.sidep.asistenciaapi.model;

import lombok.Data;

import java.util.Date;


@Data
public class JornadaModel {
    private Integer idJornada;              // pk_jorn_id_jornada
    private Integer idUnidadEducativa;      // fk_jorn_id_unidad_educativa
    private Integer idPeriodo;              // fk_jorn_id_periodo
    private Integer idTipoJornada;          // fk_jorn_id_tipo_jornada
    private String nombre;                // jorn_nombre
    private String horarioInicio;         // jorn_horario_inicio
    private String horarioTermino;        // jorn_horario_termino
    private Date fechaCreacion;           // jorn_fecha_creacion
    private Date fechaActualizacion;      // jorn_fecha_actualizacion

}
