package cl.mineduc.sidep.asistenciaapi.repositories;

import cl.mineduc.sidep.asistenciaapi.entities.AsistenciaEntity;

public interface AsistenciaTableRepository {

    void save(AsistenciaEntity asistencia);

    void update(Long id, AsistenciaEntity as);

    Boolean validarAsistencia(Long calendario, Long matriculaGrupo);

    Boolean validarGrupoTieneDocenteAsistente(Long matriculaGrupo);

    Boolean validarFechaCalendarioHabil(Long calendario);

    Long findGrupoByRbdLetraNivelJornada(Long nivelGrado, String letra, Long jornada, Integer rbd);

    Long findCalendarioByGrupo(Long grupo);

    Long findByRut(Integer rut);

    Long findUnidadEducativaByRbd(Integer rbd);

    Long findMatriculaUnidadEducativa(Long parvulo, Long unidadEducativa);

    Long findMatriculaGrupo(Long grupo, Long matriculaUe);

    Long findNivelGradoIdByNombre(Long grado);

    Long findGradoByUnidadEducativaAndNivelGrado(Long unidadEducativa, Long nivelGrado);

    Long findGrupoByGradoLetra(Long grado, String letra);

    Long findCalendarioByGrupoFecha(Long grupo, String ano, String mes, String dia);

    Long findPersonaByRut(Integer rut);

    Long findParvuloByPersona(Long persona);


}
