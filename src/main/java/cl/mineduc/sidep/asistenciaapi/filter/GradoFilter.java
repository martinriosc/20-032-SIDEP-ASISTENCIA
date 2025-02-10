package cl.mineduc.sidep.asistenciaapi.filter;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GradoFilter {

    // FK
    private Long unidadEducativaId;  // fk_grad_id_unidad_educativa

    // Campos de la tabla
    private String nombre;           // grad_nombre

    // Rango de fechas si corresponde
    private String fechaCreacionDesde;
    private String fechaCreacionHasta;

    // Paginación y orden
    private Integer limit;
    private Integer offset;
    private String orderBy;  // columna a ordenar
    private String order;    // ASC o DESC
}
