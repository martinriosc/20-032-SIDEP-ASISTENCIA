package cl.mineduc.sidep.asistenciaapi.model;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Representa la información principal de una unidad educativa.
 */
@Data
public class UnidadEducativaModel {
    private Long id;            // pk_uned_id_unidad_educativa

    // Llaves foráneas
    private Long programaId;
    private Long modalidadId;
    private Long mecanismoFinanciamientoId;
    private Long sostenedorId;
    private Long estadoUnidadEducativaId;
    private Long generoId;
    private Long estadoFuncionamientoEnsenianzaId;

    private String nombre;                // uned_nombre
    private Integer rbd;                  // uned_rbd
    private Integer dvRbd;                // uned_dv_rbd
    private String dependencia;           // uned_dependencia
    private String sitioWeb;             // uned_sitio_web
    private String softwarePropio;        // uned_software_propio
    private Boolean autorizaIntercambioCorreo; // uned_autoriza_intercambio_correo
    private String codigoAreaTelefono;    // ue_codigo_area_telefono
    private String telefono;              // uned_telefono
    private String celular;               // uned_celular
    private String mail;                  // uned_mail
    private String resolucion;            // uned_resolucion

    private LocalDate fechaResolucion;    // uned_fecha_resolucion
    private Integer codigoInstitucion;    // uned_codigo_institucion
    private LocalDate fechaInicioRo;      // uned_fecha_inicio_ro
    private LocalDate fechaTerminoRo;     // uned_fecha_termino_ro

    private LocalDateTime fechaCreacion;      // uned_fecha_creacion
    private LocalDateTime fechaActualizacion; // uned_fecha_actualizacion
}
