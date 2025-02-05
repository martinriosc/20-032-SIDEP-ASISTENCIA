package cl.mineduc.sidep.asistenciaapi.repositories;

import cl.mineduc.sidep.asistenciaapi.entities.SostenedorEntity;
import cl.mineduc.sidep.asistenciaapi.exceptions.SostenedorException;
import cl.mineduc.sidep.asistenciaapi.filter.SostenedorFilter;
import cl.mineduc.sidep.asistenciaapi.mappers.SostenedorMapper;
import cl.mineduc.sidep.asistenciaapi.model.SostenedorModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
        } catch (MyBatisSystemException e) {
            log.error(e.getMessage());
            throw new SostenedorException("Error al obtener sostenedores", e);
        }
    }

    @Override
    public SostenedorModel findById(Long id) {
        try {
            return this.sostenedorMapper.findById(id);
        } catch (MyBatisSystemException e) {
            log.error(e.getMessage());
            throw new SostenedorException("Error al obtener sostenedor", e);
        }
    }

    @Transactional
    @Override
    public void save(SostenedorEntity e) {
        try {
            Long id = this.sostenedorMapper.insert(e);
            e.setId(id);
        } catch (MyBatisSystemException ex){
            log.error(ex.getMessage());
            throw new SostenedorException("Error al guardar sostenedor", ex);
        }
    }

    @Transactional
    @Override
    public Boolean existsByRut(Integer rut, String dv) {
        try {
            return this.sostenedorMapper.existsByRut(rut, dv);
        } catch (MyBatisSystemException e) {
            log.error(e.getMessage());
            throw new SostenedorException("Error al verificar si existe un sostenedor con RUT " + rut, e);
        }
    }

    @Transactional
    @Override
    public void update(SostenedorEntity e, Long id) {
        try {
            this.sostenedorMapper.updateSostenedor(e, id);
        } catch (MyBatisSystemException ex){
            log.error(ex.getMessage());
            throw new SostenedorException("Error al actualizar sostenedor", ex);
        }
    }

    @Transactional
    @Override
    public Boolean hasUnidadEducativa(Long id) {
        try {
            return this.sostenedorMapper.hasUnidadEducativa(id);
        } catch (MyBatisSystemException e) {
            log.error(e.getMessage());
            throw new SostenedorException("Error al verificar si existe en unidad educativa", e);
        }
    }

    @Transactional
    @Override
    public void delete(Long id) {
        try {
            this.sostenedorMapper.delete(id);
        } catch (MyBatisSystemException e) {
            log.error(e.getMessage());
            throw new SostenedorException("Error al borrar sostenedor", e);
        }
    }

    @Override
    public Long countTotal(SostenedorFilter filter) {
        try {
            return this.sostenedorMapper.countTotal(filter);
        } catch (MyBatisSystemException e) {
            log.error(e.getMessage());
            throw new SostenedorException("Error al obtener total de Registros", e);
        }
    }
}
