package cl.mineduc.sidep.asistenciaapi.entities;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class ProcesoEntity {

    private String operacion;
    private Integer status;
    private String mensaje;


}
