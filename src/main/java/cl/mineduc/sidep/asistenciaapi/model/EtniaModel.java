package cl.mineduc.sidep.asistenciaapi.model;

import lombok.Data;

import java.util.Date;


@Data
public class EtniaModel {
    private Integer idEtnia;           // pk_etni_id_etnia
    private String nombre;             // etni_nombre
    private Date fechaCreacion;        // etni_fecha_creacion
    private Date fechaActualizacion;   // etni_fecha_actualizacion
}
