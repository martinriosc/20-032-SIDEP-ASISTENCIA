package cl.mineduc.sidep.asistenciaapi.entities;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SostenedorEntity {

    private Long id;
    private Long comuna;
    private Long calidadJuridica;
    private String nombre;
    private Integer run;
    private String dv;
    private String direccion;
    private String codigoAreaTelefono;
    private String telefono;
    private String celular;
    private String mail;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

}
