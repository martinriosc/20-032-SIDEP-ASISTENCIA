package cl.mineduc.sidep.asistenciaapi.mappers;

import cl.mineduc.sidep.asistenciaapi.entities.GrupoEntity;
import cl.mineduc.sidep.asistenciaapi.filter.GrupoFilter;
import cl.mineduc.sidep.asistenciaapi.model.GrupoModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GrupoMapper {
    List<GrupoModel> findAll(GrupoFilter filter);
    GrupoModel findById(@Param("id") Long id);
    Long insert(GrupoEntity entity);
    void update(@Param("e") GrupoEntity entity, @Param("id") Long id);
    void delete(@Param("id") Long id);
    Long countTotal(GrupoFilter filter);
}
