package cl.mineduc.sidep.sostenedorapi.filter;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SostenedorFilter {

    private String nombre;
    private String rut;
    private Long calidadJuridica;
    private Long comuna;

    private String orderBy;
    private String order;

    private Integer limit;
    private Integer offset;

}
