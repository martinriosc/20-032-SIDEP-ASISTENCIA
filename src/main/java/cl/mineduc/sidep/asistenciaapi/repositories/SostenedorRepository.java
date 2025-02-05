package cl.mineduc.sidep.asistenciaapi.repositories;

import cl.mineduc.sidep.asistenciaapi.entities.SostenedorEntity;
import cl.mineduc.sidep.asistenciaapi.filter.SostenedorFilter;
import cl.mineduc.sidep.asistenciaapi.model.SostenedorModel;

import java.util.List;

public interface SostenedorRepository {

    List<SostenedorModel> findAll(SostenedorFilter filter);

    SostenedorModel findById(Long id);

    void save(SostenedorEntity e);

    Boolean existsByRut(Integer rut, String dv);

    void update(SostenedorEntity e, Long id);

    Boolean hasUnidadEducativa(Long id);

    void delete(Long id);

    Long countTotal(SostenedorFilter filter);

}
