package cl.mineduc.sidep.asistenciaapi.mappers;

import cl.mineduc.sidep.asistenciaapi.entities.AsistenciaEntity;
import cl.mineduc.sidep.asistenciaapi.model.AsistenciaModel;
import org.apache.ibatis.annotations.Param;

public interface AsistenciaTableMapper {

    void insert(AsistenciaEntity asistencia);

    void update(@Param("id") Long id, @Param("as") AsistenciaEntity as);

    Boolean validarAsistencia(@Param("calendario") Long calendario, @Param("matriculaGrupo") Long matriculaGrupo);

    Boolean validarGrupoTieneDocenteAsistente(Long matriculaGrupo);

    Boolean validarFechaCalendarioHabil(Long calendario);

    Long findGrupoByRbdLetraNivelJornada(@Param("tipoJornada") Long tipoJornada,
                                         @Param("rbd") Integer rbd,
                                         @Param("letra") String letra,
                                         @Param("nivelGrado") Long nivelGrado);

    Long findCalendarioByGrupo(Long grupo);

    Long findByRut(Integer rut);

    Long findUnidadEducativaByRbd(Integer rbd);

    Long findMatriculaUnidadEducativa(@Param("parvulo") Long parvulo, @Param("unidadEducativa") Long unidadEducativa);

    Long findMatriculaGrupo(@Param("grupo") Long grupo, @Param("matriculaUe") Long matriculaUe);

    Long findNivelGradoIdByNombre(Long grado);

    Long findGradoByUnidadEducativaAndNivelGrado(Long unidadEducativa, Long nivelGrado);

    Long findGrupoByGradoLetra(Long grado, String letra);

    Long findCalendarioByGrupoFecha(Long grupo, String anio, String mes, String dia);

    Long findPersonaByRut(Integer rut);

    Long findParvuloByPersona(Long persona);


}
