package cl.mineduc.sidep.asistenciaapi.repositories;

import cl.mineduc.sidep.asistenciaapi.entities.CalendarioEntity;
import cl.mineduc.sidep.asistenciaapi.exceptions.SidepException;
import cl.mineduc.sidep.asistenciaapi.filter.CalendarioFilter;
import cl.mineduc.sidep.asistenciaapi.mappers.CalendarioMapper;
import cl.mineduc.sidep.asistenciaapi.model.CalendarioModel;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class CalendarioRepository {

    private final CalendarioMapper calendarioMapper;

    /**
     * Obtiene un lista de calendarios según el filtro.
     */
    public List<CalendarioModel> findAll(CalendarioFilter filter) {
        try {
            return calendarioMapper.findAll(filter);
        } catch (MyBatisSystemException e) {
            throw new SidepException("Error al obtener els calendarios", e);
        }
    }

    /**
     * Cuenta el total de calendarios que cumplen el filtro.
     */
    public Long countTotal(CalendarioFilter filter) {
        try {
            return calendarioMapper.countTotal(filter);
        } catch (MyBatisSystemException e) {
            throw new SidepException("Error al contar els calendarios", e);
        }
    }

    /**
     * Actualiza un calendario en el BDD.
     */
    public void update(CalendarioEntity entity) {
        try {
            calendarioMapper.update(entity, entity.getId());
        } catch (MyBatisSystemException e) {
            throw new SidepException("Error al actualizar el calendario", e);
        }
    }

    /**
     * Inserta un nueva calendario y setea el PK generada en el entidad.
     */
    public void save(CalendarioEntity entity) {
        try {
            Long newId = calendarioMapper.insert(entity);
            entity.setId(newId);
        } catch (MyBatisSystemException e) {
            throw new SidepException("Error al insertar el calendario", e);
        }
    }

    /**
     * Busca un calendario por su ID.
     */
    public CalendarioModel findById(Long id) {
        try {
            return calendarioMapper.findById(id);
        } catch (MyBatisSystemException e) {
            throw new SidepException("Error al buscar el calendario por id", e);
        }
    }

    /**
     * Elimina un registro de calendario por su ID.
     */
    public void delete(Long id) {
        try {
            calendarioMapper.delete(id);
        } catch (MyBatisSystemException e) {
            throw new SidepException("Error al eliminar el calendario", e);
        }
    }

    /**
     * Busca un calendario por Dia y Mes.
     */
    public CalendarioModel findByDiaMesAnio(int dia, int mes, int anio) {
        Map<String, Object> params = new HashMap<>();
        params.put("dia", dia);
        params.put("mes", mes);
        params.put("anio", anio);
        return calendarioMapper.findByDiaMesAnio(params);
    }

}
