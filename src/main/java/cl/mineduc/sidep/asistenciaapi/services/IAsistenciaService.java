package cl.mineduc.sidep.asistenciaapi.services;

import cl.mineduc.sidep.asistenciaapi.model.AsistenciaIndividualModel;
import cl.mineduc.sidep.asistenciaapi.model.AsistenciaModel;
import cl.mineduc.sidep.asistenciaapi.model.PaginationResultModel;

import java.util.List;

public interface IAsistenciaService {

    /**
     * Actualiza la asistencia de un pupilo en un día específico.
     */
    AsistenciaModel updateAsistenciaPupiloPorDia(AsistenciaIndividualModel asistenciaModel);

    /**
     * Actualiza la asistencia de forma grupal en un día específico.
     */
    AsistenciaModel updateAsistenciaGrupalPupiloPorDia(List<AsistenciaIndividualModel> asistenciaModel);

    /**
     * Encuentra asistencias filtrando por RBD, enseñanza, grado, letra, y rut.
     */
    PaginationResultModel<AsistenciaModel> findAsistencia(String rbd, String ensenanza, String grado, String letra, Integer rut);

    /**
     * Encuentra asistencias filtrando por RBD, enseñanza, grado, letra, mes, día y rut.
     */
    PaginationResultModel<AsistenciaModel> findAsistenciaPorMesAndDia(String rbd, String ensenanza, String grado, String letra,
                                                                      String mes, String dia, Integer rut);

    /**
     * Encuentra todas las asistencias dentro de un período, y filtros de establecimiento/ubicación,
     * con paginación.
     */
    PaginationResultModel<AsistenciaModel> findAllAsistencia(String periodoDesde, String periodoHasta,
                                                             String establecimiento, String region,
                                                             String provincia, String comuna,
                                                             Integer pageSize, Integer pageNumber);
}
