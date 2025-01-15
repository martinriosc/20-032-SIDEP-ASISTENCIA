package cl.mineduc.sidep.sostenedorapi.utils;

import cl.mineduc.sidep.sostenedorapi.entities.ProcesoEntity;

public class ProcesoUtils {

    private ProcesoUtils() {
        super();
    }

    public static String getOperacion(String method, String uri) {
        return String.format("%s - %s", method, uri);
    }

    public static ProcesoEntity getProcesoEntity(Integer status, String mensaje, String operacion) {
        return ProcesoEntity
                .builder()
                .status(status)
                .mensaje(mensaje)
                .operacion(operacion).build();
    }

}
