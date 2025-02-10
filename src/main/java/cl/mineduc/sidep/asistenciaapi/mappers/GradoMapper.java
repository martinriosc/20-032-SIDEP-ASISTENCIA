package cl.mineduc.sidep.asistenciaapi.mappers;

import cl.mineduc.sidep.asistenciaapi.entities.GradoEntity;
import cl.mineduc.sidep.asistenciaapi.filter.GradoFilter;
import cl.mineduc.sidep.asistenciaapi.model.GradoModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GradoMapper {
    List<GradoModel> findAll(GradoFilter filter);
    GradoModel findById(@Param("id") Long id);
    Long insert(GradoEntity entity);
    void update(@Param("e") GradoEntity entity, @Param("id") Long id);
    void delete(@Param("id") Long id);
    Long countTotal(GradoFilter filter);
}
