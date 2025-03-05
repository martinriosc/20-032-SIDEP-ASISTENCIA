package cl.mineduc.sidep.asistenciaapi.repositories;

import cl.mineduc.sidep.asistenciaapi.entities.AsistenciaEntity;
import cl.mineduc.sidep.asistenciaapi.exceptions.SidepException;
import cl.mineduc.sidep.asistenciaapi.filter.AsistenciaFilter;
import cl.mineduc.sidep.asistenciaapi.mappers.AsistenciaMapper;
import cl.mineduc.sidep.asistenciaapi.model.AsistenciaModel;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
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
     * Actualiza una asistencia existente en la BDD y retorna la asistencia actualizada
     * (cargada desde la BD).
     */
    public AsistenciaModel update(Long id, AsistenciaEntity entity) {
        try {
            asistenciaMapper.update(id, entity);

            // Retornamos la asistencia consultándola nuevamente desde la BD
            // para obtener los datos frescos.
            return this.findById(entity.getId());

        } catch (MyBatisSystemException e) {
            throw new SidepException("Error al actualizar la asistencia", e);
        }
    }

    /**
     * Inserta una nueva asistencia en la BDD y retorna la asistencia creada
     * (cargada desde la BD).
     */
    public AsistenciaModel save(AsistenciaEntity entity) {
        try {
            Long newId = asistenciaMapper.insert(entity);

            // Retornamos la asistencia recién insertada, consultándola por su nuevo ID
            return this.findById(newId);

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


    public AsistenciaModel findByCalendarioAndMatriculaGrupo(@Param("calendarioId") Long calendarioId,
                                                      @Param("matriculaGrupoId") Long matriculaGrupoId) {
        try {
            return asistenciaMapper.findByCalendarioAndMatriculaGrupo(calendarioId, matriculaGrupoId);
        } catch (MyBatisSystemException e) {
            throw new SidepException("Error al findByCalendarioAndMatriculaGrupo", e);
        }
    }
}
