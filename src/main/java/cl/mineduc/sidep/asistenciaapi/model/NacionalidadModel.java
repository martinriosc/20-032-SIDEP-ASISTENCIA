package cl.mineduc.sidep.asistenciaapi.model;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class NacionalidadModel {
    private Integer idNacionalidad;     // pk_naci_id_nacionalidad
    private String nombre;              // naci_nombre
    private Date fechaCreacion;         // naci_fecha_creacion
    private Date fechaActualizacion;    // naci_fecha_actualizacion
}
