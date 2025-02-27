package cl.mineduc.sidep.asistenciaapi.mappers;

import cl.mineduc.sidep.asistenciaapi.entities.AsistenciaEntity;
import cl.mineduc.sidep.asistenciaapi.filter.AsistenciaFilter;
import cl.mineduc.sidep.asistenciaapi.model.AsistenciaModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AsistenciaMapper {
    List<AsistenciaModel> findAll(AsistenciaFilter filter);
    AsistenciaModel findById(@Param("id") Long id);

    Long insert(AsistenciaEntity entity);

    // Un solo parámetro para update
    void update(AsistenciaEntity entity);

    void delete(@Param("id") Long id);

    Long countTotal(AsistenciaFilter filter);
}
