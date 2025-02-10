package cl.mineduc.sidep.asistenciaapi.mappers;

import cl.mineduc.sidep.asistenciaapi.entities.CalendarioEntity;
import cl.mineduc.sidep.asistenciaapi.filter.CalendarioFilter;
import cl.mineduc.sidep.asistenciaapi.model.CalendarioModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CalendarioMapper {

    List<CalendarioModel> findAll(CalendarioFilter filter);

    CalendarioModel findById(@Param("id") Long id);

    Long insert(CalendarioEntity entity);

    void update(@Param("e") CalendarioEntity entity, @Param("id") Long id);

    void delete(@Param("id") Long id);

    Long countTotal(CalendarioFilter filter);
}
