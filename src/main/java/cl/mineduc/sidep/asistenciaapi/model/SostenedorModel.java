package cl.mineduc.sidep.asistenciaapi.model;

import lombok.Data;

import java.util.Date;


@Data
public class SostenedorModel {
    private Integer idSostenedor;          // pk_sost_id_sostenedor
    private Integer idCalidadJuridica;      // fk_sost_id_calidad_juridica (puede ser nulo)
    private Integer idComuna;               // fk_sost_id_comuna (puede ser nulo)
    private String nombre;                // sost_nombre
    private Integer rut;                  // sost_rut (puede ser nulo)
    private String dv;                    // sost_dv (puede ser nulo)
    private String direccion;             // sost_direccion (puede ser nulo)
    private String codigoAreaTelefono;    // sost_codigo_area_telefono (puede ser nulo)
    private String telefono;              // sost_telefono (puede ser nulo)
    private String celular;               // sost_celular (puede ser nulo)
    private String mail;                  // sost_mail (puede ser nulo)
    private Date fechaCreacion;           // sost_fecha_creacion
    private Date fechaActualizacion;      // sost_fecha_actualizacion
}
