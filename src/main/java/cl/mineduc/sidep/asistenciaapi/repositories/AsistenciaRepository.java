package cl.mineduc.sidep.asistenciaapi.repositories;

import cl.mineduc.sidep.asistenciaapi.entities.AsistenciaEntity;
import cl.mineduc.sidep.asistenciaapi.exceptions.SidepException;
import cl.mineduc.sidep.asistenciaapi.filter.AsistenciaFilter;
import cl.mineduc.sidep.asistenciaapi.mappers.AsistenciaMapper;
import cl.mineduc.sidep.asistenciaapi.model.AsistenciaModel;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AsistenciaRepository {

    private final AsistenciaMapper asistenciaMapper;

    /**
     * Obtiene una lista de asistencias según el filtro.
     */
    public List<AsistenciaModel> findAll(AsistenciaFilter filter) {
        try {
            return asistenciaMapper.findAll(filter);
        } catch (MyBatisSystemException e) {
            throw new SidepException("Error al obtener las asistencias", e);
        }
    }

    /**
     * Cuenta el total de asistencias que cumplen el filtro.
     */
    public Long countTotal(AsistenciaFilter filter) {
        try {
            return asistenciaMapper.countTotal(filter);
        } catch (MyBatisSystemException e) {
            throw new SidepException("Error al contar las asistencias", e);
        }
    }

    /**
     * Actualiza una asistencia en la BDD.
     */
    public void update(AsistenciaEntity entity) {
        try {
            asistenciaMapper.update(entity, entity.getId());
        } catch (MyBatisSystemException e) {
            throw new SidepException("Error al actualizar la asistencia", e);
        }
    }

    /**
     * Inserta una nueva asistencia y setea la PK generada en la entidad.
     */
    public void save(AsistenciaEntity entity) {
        try {
            Long newId = asistenciaMapper.insert(entity);
            entity.setId(newId);
        } catch (MyBatisSystemException e) {
            throw new SidepException("Error al insertar la asistencia", e);
        }
    }

    /**
     * Busca una asistencia por su ID.
     */
    public AsistenciaModel findById(Long id) {
        try {
            return asistenciaMapper.findById(id);
        } catch (MyBatisSystemException e) {
            throw new SidepException("Error al buscar la asistencia por id", e);
        }
    }

    /**
     * Elimina un registro de asistencia por su ID.
     */
    public void delete(Long id) {
        try {
            asistenciaMapper.delete(id);
        } catch (MyBatisSystemException e) {
            throw new SidepException("Error al eliminar la asistencia", e);
        }
    }
}
