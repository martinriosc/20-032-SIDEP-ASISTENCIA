package cl.mineduc.sidep.asistenciaapi.model;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class AsistenciaModel {
    private Long id;
    private String rbd;
    private String grado;
    private String letra;
    private Long rut;
    private Boolean presente;
    private String calendarioFecha;
    private String calendarioTrabajado;
    private String fechaRegistro;
    private String fechaActualizacion;
    private Long calendarioId;
    private Long matriculaGrupoId;
    private String jsonAsistencia;
    private String jsonAsistencia2;

}
