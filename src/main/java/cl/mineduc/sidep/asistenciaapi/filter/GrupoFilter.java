package cl.mineduc.sidep.asistenciaapi.filter;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GrupoFilter {

    // IDs for foreign keys
    private Long jornadaId;    // fk_grup_id_jornada
    private Long gradoId;      // fk_grup_id_grado

    // Campos propios
    private String letra;      // grup_letra
    private Integer cupo;      // grup_cupo

    // Ejemplos de rango de fechas de creación/actualización
    private String fechaCreacionDesde;
    private String fechaCreacionHasta;

    // Paginación y orden
    private Integer limit;
    private Integer offset;
    private String orderBy;  // nombre de la columna
    private String order;    // ASC o DESC
}
