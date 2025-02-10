package cl.mineduc.sidep.asistenciaapi.mappers;

import cl.mineduc.sidep.asistenciaapi.entities.PersonaEntity;
import cl.mineduc.sidep.asistenciaapi.filter.PersonaFilter;
import cl.mineduc.sidep.asistenciaapi.model.PersonaModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PersonaMapper {

    List<PersonaModel> findAll(PersonaFilter filter);

    PersonaModel findById(@Param("id") Long id);

    Long insert(PersonaEntity entity);

    void update(@Param("e") PersonaEntity entity, @Param("id") Long id);

    void delete(@Param("id") Long id);

    Long countTotal(PersonaFilter filter);
}
