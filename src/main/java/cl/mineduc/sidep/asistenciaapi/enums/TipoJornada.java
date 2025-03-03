package cl.mineduc.sidep.asistenciaapi.enums;

import lombok.Getter;

@Getter
public enum TipoJornada {

    MANANA(1L),
    TARDE(2L),
    MANANA_TARDE(3L),
    VESPERTINO(4L);

    private final Long id;

    TipoJornada(Long id) {
        this.id = id;
    }

    public static TipoJornada valueOf(Long id) {
        for (TipoJornada tipoJornada : TipoJornada.values()) {
            if (tipoJornada.id.equals(id)) {
                return tipoJornada;
            }
        }

        return null;
    }

}
