package cl.mineduc.sidep.asistenciaapi.mappers;

import cl.mineduc.sidep.asistenciaapi.entities.AsistenciaEntity;
import cl.mineduc.sidep.asistenciaapi.filter.AsistenciaFilter;
import cl.mineduc.sidep.asistenciaapi.model.AsistenciaModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AsistenciaMapper {

    /**
     * Encuentra todas las asistencias según el filtro.
     */
    List<AsistenciaModel> findAll(AsistenciaFilter filter);

    /**
     * Retorna un registro de asistencia por su ID.
     */
    AsistenciaModel findById(@Param("id") Long id);

    /**
     * Inserta un registro en la tabla asistencia. Retorna la PK generada.
     */
    Long insert(AsistenciaEntity entity);

    /**
     * Actualiza un registro de asistencia.
     */
    void update(@Param("e") AsistenciaEntity entity, @Param("id") Long id);

    /**
     * Elimina un registro de asistencia por ID.
     */
    void delete(@Param("id") Long id);

    /**
     * Retorna la cantidad total de registros que cumplen con el filtro.
     */
    Long countTotal(AsistenciaFilter filter);
}
