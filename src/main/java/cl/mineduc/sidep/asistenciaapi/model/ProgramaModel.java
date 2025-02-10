package cl.mineduc.sidep.asistenciaapi.model;

import lombok.Data;

import java.util.Date;


@Data
public class ProgramaModel {
    private Integer idPrograma;           // pk_prog_id_programa
    private String nombre;                // prog_nombre
    private Date fechaCreacion;           // prog_fecha_creacion
    private Date fechaActualizacion;      // prog_fecha_actualizacion
}
