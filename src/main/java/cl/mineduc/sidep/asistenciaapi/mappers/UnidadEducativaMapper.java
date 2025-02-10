package cl.mineduc.sidep.asistenciaapi.mappers;

import cl.mineduc.sidep.asistenciaapi.entities.UnidadEducativaEntity;
import cl.mineduc.sidep.asistenciaapi.filter.UnidadEducativaFilter;
import cl.mineduc.sidep.asistenciaapi.model.UnidadEducativaModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UnidadEducativaMapper {

    /**
     * Retorna una lista de unidades educativas filtradas según {@link UnidadEducativaFilter}.
     */
    List<UnidadEducativaModel> findAll(UnidadEducativaFilter filter);

    /**
     * Retorna la unidad educativa cuyo PK coincide con el parámetro <code>id</code>.
     */
    UnidadEducativaModel findById(@Param("id") Long id);

    /**
     * Inserta una nueva unidad educativa y retorna el ID autogenerado.
     */
    Long insert(UnidadEducativaEntity entity);

    /**
     * Actualiza la unidad educativa cuyo PK es <code>id</code>.
     */
    void update(@Param("e") UnidadEducativaEntity entity, @Param("id") Long id);

    /**
     * Elimina la unidad educativa cuyo PK es <code>id</code>.
     */
    void delete(@Param("id") Long id);

    /**
     * Retorna la cantidad total de registros que cumplen con el filtro.
     */
    Long countTotal(UnidadEducativaFilter filter);
}
