package cl.mineduc.sidep.asistenciaapi.filter;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PersonaFilter {
    private Long etniaId;
    private Long nacionalidadId;
    private Long estadoCivilId;
    private Long sexoId;
    private Integer rut;
    private String dv;
    private String nombre;
    private String primerApellido;
    private String segundoApellido;
    private String fechaNacimientoDesde;
    private String fechaNacimientoHasta;
    private Integer limit;
    private Integer offset;
    private String orderBy; // columna a ordenar
    private String order;   // ASC o DESC
}
