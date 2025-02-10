package cl.mineduc.sidep.asistenciaapi.model;

import lombok.Data;

import java.util.Date;


@Data
public class EstadoCivilModel {
    private Integer idEstadoCivil;        // pk_esci_id_estado_civil
    private String nombre;                // esci_nombre
    private Date fechaCreacion;           // esci_fecha_creacion
    private Date fechaActualizacion;      // esci_fecha_actualizacion
}
