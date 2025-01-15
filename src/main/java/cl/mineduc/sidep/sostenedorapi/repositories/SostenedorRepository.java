package cl.mineduc.sidep.sostenedorapi.repositories;

import cl.mineduc.sidep.sostenedorapi.entities.SostenedorEntity;
import cl.mineduc.sidep.sostenedorapi.filter.SostenedorFilter;
import cl.mineduc.sidep.sostenedorapi.model.SostenedorModel;

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
