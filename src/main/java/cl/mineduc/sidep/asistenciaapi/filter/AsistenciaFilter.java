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
    private String grado;
    private String letra;
    private String mes;
    private String dia;
    private Long rut;
    private String periodoDesde;
    private String periodoHasta;
    private String matriculaUe;
    private String establecimiento;
    private String calendarioFecha;
    private String region;
    private String provincia;
    private String comuna;
    private Integer limit;
    private Integer offset;
    private String orderBy;
    private String order;
}
