package cl.mineduc.sidep.asistenciaapi.filter;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UnidadEducativaFilter {
    private Long programaId;
    private Long modalidadId;
    private Long mecanismoFinanciamientoId;
    private Long sostenedorId;
    private Long estadoUnidadEducativaId;
    private Long generoId;
    private Long estadoFuncionamientoEnsenianzaId;
    private String nombre;      // uned_nombre
    private Integer rbd;        // uned_rbd
    private Integer dvRbd;      // uned_dv_rbd
    private String dependencia; // uned_dependencia
    private String sitioWeb;    // uned_sitio_web
    private String softwarePropio;
    private Boolean autorizaIntercambioCorreo;
    private String codigoAreaTelefono;
    private String telefono;
    private String celular;
    private String mail;
    private String resolucion;
    private String fechaInicioRoDesde;
    private String fechaInicioRoHasta;
    private Integer limit;
    private Integer offset;
    private String orderBy; // columna a ordenar
    private String order;   // ASC o DESC
}
