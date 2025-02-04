package cl.mineduc.sidep.sostenedorapi.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class SostenedorModel {

    private Long id;
    private ComunaModel comuna;
    private CalidadJuridica calidadJuridica;

    @NotBlank(message = "Nombre no puede estar vacio")
    private String nombre;

    @NotNull(message = "RUN no puede estar vacio")
    private Integer rut;

    @NotBlank(message = "Digito Verificador no puede estar vacio")
    private String dv;
    private String direccion;
    private String codigoAreaTelefono;
    private String telefono;
    private String celular;

    @Email(message = "Debe ser un mail válido")
    private String mail;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

}
