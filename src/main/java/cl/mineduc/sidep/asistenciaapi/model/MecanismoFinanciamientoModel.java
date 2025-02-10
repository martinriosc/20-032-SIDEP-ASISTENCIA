package cl.mineduc.sidep.asistenciaapi.model;

import lombok.Data;

import java.util.Date;


@Data
public class MecanismoFinanciamientoModel {
    private Integer idMecanismoFinanciamiento;   // pk_mefi_id_mecanismo_financiamiento
    private String nombre;                       // mefi_nombre
    private Date fechaCreacion;                  // mefi_fecha_creacion
    private Date fechaActualizacion;             // mefi_fecha_actualizacion
}
