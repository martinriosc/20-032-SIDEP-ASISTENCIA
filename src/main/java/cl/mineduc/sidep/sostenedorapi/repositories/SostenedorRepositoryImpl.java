package cl.mineduc.sidep.sostenedorapi.repositories;

import cl.mineduc.sidep.sostenedorapi.entities.SostenedorEntity;
import cl.mineduc.sidep.sostenedorapi.exceptions.SostenedorException;
import cl.mineduc.sidep.sostenedorapi.filter.SostenedorFilter;
import cl.mineduc.sidep.sostenedorapi.mappers.SostenedorMapper;
import cl.mineduc.sidep.sostenedorapi.model.SostenedorModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class SostenedorRepositoryImpl implements SostenedorRepository {

    private final SostenedorMapper sostenedorMapper;

    @Override
    public List<SostenedorModel> findAll(SostenedorFilter filter) {
        try {
            return this.sostenedorMapper.findAll(filter);
        } catch (DataAccessException e) {
            log.error(e.getMessage());
            throw new SostenedorException("Error al obtener sostenedores", e);
        }
    }

    @Override
    public SostenedorModel findById(Long id) {
        try {
            return this.sostenedorMapper.findById(id);
        } catch (DataAccessException e) {
            log.error(e.getMessage());
            throw new SostenedorException("Error al obtener sostenedor", e);
        }
    }

    @Override
    public void save(SostenedorEntity e) {
        try {
            Long id = this.sostenedorMapper.insert(e);
            e.setId(id);
        } catch (DataAccessException ex){
            log.error(ex.getMessage());
            throw new SostenedorException("Error al guardar sostenedor", ex);
        }
    }
}
