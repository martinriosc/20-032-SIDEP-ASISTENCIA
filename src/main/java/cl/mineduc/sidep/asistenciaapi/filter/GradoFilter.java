package cl.mineduc.sidep.asistenciaapi.filter;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GradoFilter {
    private Long unidadEducativaId;  // fk_grad_id_unidad_educativa
    private String nombre;           // grad_nombre
    private String fechaCreacionDesde;
    private String fechaCreacionHasta;
    private Integer limit;
    private Integer offset;
    private String orderBy;  // columna a ordenar
    private String order;    // ASC o DESC
}
