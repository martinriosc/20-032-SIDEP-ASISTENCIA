package cl.mineduc.sidep.asistenciaapi.mappers;

import cl.mineduc.sidep.asistenciaapi.entities.UnidadEducativaEntity;
import cl.mineduc.sidep.asistenciaapi.filter.UnidadEducativaFilter;
import cl.mineduc.sidep.asistenciaapi.model.UnidadEducativaModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UnidadEducativaMapper {
    List<UnidadEducativaModel> findAll(UnidadEducativaFilter filter);
    UnidadEducativaModel findById(@Param("id") Long id);
    Long insert(UnidadEducativaEntity entity);
    void update(@Param("e") UnidadEducativaEntity entity, @Param("id") Long id);
    void delete(@Param("id") Long id);
    Long countTotal(UnidadEducativaFilter filter);
}
