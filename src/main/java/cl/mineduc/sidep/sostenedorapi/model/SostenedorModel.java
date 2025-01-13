package cl.mineduc.sidep.sostenedorapi.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SostenedorModel {

    private Long id;
    private ComunaModel comuna;
    private CalidadJuridica calidadJuridica;
    private String nombre;
    private Integer rut;
    private String dv;
    private String direccion;
    private String codigoAreaTelefono;
    private String telefono;
    private String celular;
    private String mail;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

}
