package cl.mineduc.sidep.asistenciaapi.repositories;

import cl.mineduc.sidep.asistenciaapi.entities.AsistenciaEntity;
import cl.mineduc.sidep.asistenciaapi.exceptions.SostenedorException;
import cl.mineduc.sidep.asistenciaapi.mappers.AsistenciaTableMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class AsistenciaTableRepositoryImpl implements AsistenciaTableRepository {

    private final AsistenciaTableMapper asistenciaTableMapper;

    @Override
    public void save(AsistenciaEntity asistencia) {
        try {
            this.asistenciaTableMapper.insert(asistencia);
        } catch (MyBatisSystemException e) {
            log.error(e.getMessage(), e);
            throw new SostenedorException("Error al guardar asistencia", e);
        }
    }

    @Override
    public void update(Long id, AsistenciaEntity as) {
        try {
            this.asistenciaTableMapper.update(id, as);
        } catch (MyBatisSystemException e) {
            log.error(e.getMessage(), e);
            throw new SostenedorException("Error al actualizar asistencia", e);
        }
    }

    @Override
    public Boolean validarAsistencia(Long calendario, Long matriculaGrupo) {
        try {
            return this.asistenciaTableMapper.validarAsistencia(calendario, matriculaGrupo);
        } catch (MyBatisSystemException e) {
            log.error(e.getMessage(), e);
            throw new SostenedorException("Error al validar asistencia", e);
        }
    }

    @Override
    public Boolean validarGrupoTieneDocenteAsistente(Long matriculaGrupo) {
        try {
            return this.asistenciaTableMapper.validarGrupoTieneDocenteAsistente(matriculaGrupo);
        } catch (MyBatisSystemException e) {
            log.error(e.getMessage(), e);
            throw new SostenedorException("Error al validar docente o asistente", e);
        }
    }

    @Override
    public Boolean validarFechaCalendarioHabil(Long calendario) {
        try {
            return this.asistenciaTableMapper.validarFechaCalendarioHabil(calendario);
        } catch (MyBatisSystemException e) {
            log.error(e.getMessage(), e);
            throw new SostenedorException("Error al validar fecha calendario habil", e);
        }
    }

    @Override
    public Long findGrupoByRbdLetraNivelJornada(Long nivelGrado, String letra ,Long jornada, Integer rbd) {
        try {
            return this.asistenciaTableMapper.findGrupoByRbdLetraNivelJornada(jornada, rbd, letra, nivelGrado);
        } catch (MyBatisSystemException e) {
            log.error(e.getMessage(), e);
            throw new SostenedorException("Error al buscar grupo por jornada y grado", e);
        }
    }

    @Override
    public Long findCalendarioByGrupo(Long grupo) {
        try {
            return this.asistenciaTableMapper.findCalendarioByGrupo(grupo);
        } catch (MyBatisSystemException e) {
            log.error(e.getMessage(), e);
            throw new SostenedorException("Error al buscar calendario por grupo", e);
        }
    }

    @Override
    public Long findByRut(Integer rut) {
        try {
            return this.asistenciaTableMapper.findByRut(rut);
        } catch (MyBatisSystemException e) {
            log.error(e.getMessage(), e);
            throw new SostenedorException("Error al buscar por rut", e);
        }
    }

    @Override
    public Long findUnidadEducativaByRbd(Integer rbd) {
        try {
            return this.asistenciaTableMapper.findUnidadEducativaByRbd(rbd);
        } catch (MyBatisSystemException e) {
            log.error(e.getMessage(), e);
            throw new SostenedorException("Error al buscar unidad educativa por rbd", e);
        }
    }

    @Override
    public Long findMatriculaUnidadEducativa(Long parvulo, Long unidadEducativa) {
        try {
            return this.asistenciaTableMapper.findMatriculaUnidadEducativa(parvulo, unidadEducativa);
        } catch (MyBatisSystemException e) {
            log.error(e.getMessage(), e);
            throw new SostenedorException("Error al buscar matricula unidad educativa", e);
        }
    }

    @Override
    public Long findMatriculaGrupo(Long grupo, Long matriculaUe) {
        try {
            return this.asistenciaTableMapper.findMatriculaGrupo(grupo, matriculaUe);
        } catch (MyBatisSystemException e) {
            log.error(e.getMessage(), e);
            throw new SostenedorException("Error al buscar matricula grupo", e);
        }
    }

    @Override
    public Long findNivelGradoIdByNombre(Long grado) {
        try {
            return this.asistenciaTableMapper.findNivelGradoIdByNombre(grado);
        } catch (MyBatisSystemException e) {
            log.error(e.getMessage(), e);
            throw new SostenedorException("Error al buscar findNivelGradoIdByNombre", e);
        }
    }

    @Override
    public Long findGradoByUnidadEducativaAndNivelGrado(Long unidadEducativa, Long nivelGrado) {
        try {
            return this.asistenciaTableMapper.findGradoByUnidadEducativaAndNivelGrado(unidadEducativa, nivelGrado);
        } catch (MyBatisSystemException e) {
            log.error(e.getMessage(), e);
            throw new SostenedorException("Error al buscar findGradoByUnidadEducativaAndNivelGrado", e);
        }
    }

    @Override
    public Long findGrupoByGradoLetra(Long grado, String letra) {
        try {
            return this.asistenciaTableMapper.findGrupoByGradoLetra(grado, letra);
        } catch (MyBatisSystemException e) {
            log.error(e.getMessage(), e);
            throw new SostenedorException("Error al buscar findGrupoByGradoLetra", e);
        }
    }

    @Override
    public Long findCalendarioByGrupoFecha(Long grupo, String ano, String mes, String dia) {
        try {
            return this.asistenciaTableMapper.findCalendarioByGrupoFecha(grupo, ano, mes, dia);
        } catch (MyBatisSystemException e) {
            log.error(e.getMessage(), e);
            throw new SostenedorException("Error al buscar findCalendarioByGrupoFecha", e);
        }
    }

    @Override
    public Long findPersonaByRut(Integer rut) {
        try {
            return this.asistenciaTableMapper.findPersonaByRut(rut);
        } catch (MyBatisSystemException e) {
            log.error(e.getMessage(), e);
            throw new SostenedorException("Error al buscar findPersonaByRut", e);
        }
    }

    @Override
    public Long findParvuloByPersona(Long persona) {
        try {
            return this.asistenciaTableMapper.findParvuloByPersona(persona);
        } catch (MyBatisSystemException e) {
            log.error(e.getMessage(), e);
            throw new SostenedorException("Error al buscar findParvuloByPersona", e);
        }
    }
}
