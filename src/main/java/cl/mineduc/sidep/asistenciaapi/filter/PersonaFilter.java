package cl.mineduc.sidep.asistenciaapi.filter;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PersonaFilter {

    // Llaves foráneas
    private Long etniaId;
    private Long nacionalidadId;
    private Long estadoCivilId;
    private Long sexoId;

    // Campos principales
    private Integer rut;
    private String dv;
    private String nombre;
    private String primerApellido;
    private String segundoApellido;

    // Rango de fechas de nacimiento, por ejemplo
    private String fechaNacimientoDesde;
    private String fechaNacimientoHasta;

    // Paginación y orden
    private Integer limit;
    private Integer offset;
    private String orderBy; // columna a ordenar
    private String order;   // ASC o DESC
}
