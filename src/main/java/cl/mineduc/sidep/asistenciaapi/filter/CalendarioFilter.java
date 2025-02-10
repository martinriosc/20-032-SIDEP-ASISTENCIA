package cl.mineduc.sidep.asistenciaapi.filter;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CalendarioFilter {

    private Long grupoId;     // fk_cale_id_grupo
    private String fechaDesde;  // para filtrar cale_fecha >= fechaDesde
    private String fechaHasta;  // para filtrar cale_fecha <= fechaHasta
    private Boolean trabajado;  // cale_trabajado
    private Integer limit;
    private Integer offset;
    private String orderBy;   // nombre de la columna
    private String order;     // ASC o DESC
}
