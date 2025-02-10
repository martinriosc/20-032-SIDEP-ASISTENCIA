package cl.mineduc.sidep.asistenciaapi.model;

import lombok.Data;

import java.util.Date;

@Data
public class ModalidadModel {
    private Integer idModalidad;       // pk_moda_id_modalidad
    private String nombre;             // moda_nombre
    private Date fechaCreacion;        // moda_fecha_creacion
    private Date fechaActualizacion;   // moda_fecha_actualizacion
}
