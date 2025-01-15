package cl.mineduc.sidep.sostenedorapi.entities;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class ProcesoEntity {

    private String operacion;
    private Integer status;
    private String mensaje;


}
