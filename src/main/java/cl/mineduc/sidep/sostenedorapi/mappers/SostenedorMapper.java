package cl.mineduc.sidep.sostenedorapi.mappers;

import cl.mineduc.sidep.sostenedorapi.entities.SostenedorEntity;
import cl.mineduc.sidep.sostenedorapi.filter.SostenedorFilter;
import cl.mineduc.sidep.sostenedorapi.model.SostenedorModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SostenedorMapper {

    List<SostenedorModel> findAll(SostenedorFilter filter);

    SostenedorModel findById(@Param("id") Long id);

    Long insert(SostenedorEntity entity);

    Boolean existsByRut(@Param("rut") Integer rut, @Param("dv") String dv);

    void updateSostenedor(@Param("s") SostenedorEntity entity, @Param("id") Long id);

    Boolean hasUnidadEducativa(@Param("id") Long id);

    void delete(@Param("id") Long id);

    Long countTotal(SostenedorFilter filter);

}
