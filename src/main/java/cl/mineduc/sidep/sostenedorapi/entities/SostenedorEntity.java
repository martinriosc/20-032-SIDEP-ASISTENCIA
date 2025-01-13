package cl.mineduc.sidep.sostenedorapi.entities;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SostenedorEntity {

    private Long id;
    private Long comuna;
    private Long calidadJurifica;
    private String nombre;
    private String rut;
    private String dv;
    private String direccion;
    private String codigoAreaTelefono;
    private String telefono;
    private String celular;
    private String mail;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

}
