package cl.mineduc.sidep.asistenciaapi.model;

import lombok.Data;

import java.util.Date;


@Data
public class SexoModel {
    private Integer idSexo;             // pk_sexo_id_sexo
    private String nombre;              // sexo_nombre
    private Date fechaCreacion;         // sexo_fecha_creacion
    private Date fechaActualizacion;    // sexo_fecha_actualizacion
}
