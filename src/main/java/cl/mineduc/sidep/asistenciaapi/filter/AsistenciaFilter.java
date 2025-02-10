package cl.mineduc.sidep.asistenciaapi.filter;

import lombok.Builder;
import lombok.Data;

/**
 * Filtro para la búsqueda de asistencias.
 * Combina campos de ejemplo usados en distintos endpoints del AsistenciaController.
 */
@Data
@Builder
public class AsistenciaFilter {
    private String rbd;
    private String ensenanza;
    private String grado;
    private String letra;

    private String mes;
    private String dia;

    private Integer rut;

    // Campos para filtrar por periodos (por ejemplo, fechaDesde / fechaHasta)
    private String periodoDesde;
    private String periodoHasta;

    // Campos para filtrar por ubicación (ej. región, provincia, comuna, establecimiento)
    private String establecimiento;
    private String region;
    private String provincia;
    private String comuna;

    // Campos para la paginación y ordenamiento
    private Integer limit;
    private Integer offset;
    private String orderBy;
    private String order; // ASC | DESC
}
