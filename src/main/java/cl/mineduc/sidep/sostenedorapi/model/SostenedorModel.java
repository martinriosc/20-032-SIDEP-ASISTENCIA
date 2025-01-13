package cl.mineduc.sidep.sostenedorapi.model;

import cl.mineduc.sidep.sostenedorapi.annotation.RutConstraint;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@RutConstraint(message = "Rut no es válido")
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

    @Email
    private String mail;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

}
