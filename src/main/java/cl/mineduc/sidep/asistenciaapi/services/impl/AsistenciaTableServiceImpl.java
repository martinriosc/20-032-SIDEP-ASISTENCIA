package cl.mineduc.sidep.asistenciaapi.services.impl;

import cl.mineduc.sidep.asistenciaapi.entities.AsistenciaEntity;
import cl.mineduc.sidep.asistenciaapi.exceptions.SostenedorException;
import cl.mineduc.sidep.asistenciaapi.model.AsistenciaIndividualModel;
import cl.mineduc.sidep.asistenciaapi.model.AsistenciaModel;
import cl.mineduc.sidep.asistenciaapi.repositories.AsistenciaTableRepository;
import cl.mineduc.sidep.asistenciaapi.services.AsistenciaTableService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class AsistenciaTableServiceImpl implements AsistenciaTableService {

    private final AsistenciaTableRepository asistenciaTableRepository;

    @Override
    @Transactional
    public AsistenciaModel save(AsistenciaIndividualModel asistencia) {
        Long unidadEducativaId = checkFound(
                asistenciaTableRepository.findUnidadEducativaByRbd(asistencia.getRbd()),
                "No se encontró la Unidad Educativa para el RBD especificado."
        );

        Long nivelGradoId = checkFound(
                asistenciaTableRepository.findNivelGradoIdByNombre(asistencia.getGrado()),
                "No se encontró el nivel de grado para el grado especificado."
        );

        Long gradoId = checkFound(
                asistenciaTableRepository.findGradoByUnidadEducativaAndNivelGrado(unidadEducativaId, nivelGradoId),
                "No se encontró el grado para la unidad educativa y el nivel de grado."
        );

        Long grupoId = checkFound(
                asistenciaTableRepository.findGrupoByGradoLetra(gradoId, asistencia.getLetra()),
                "No se encontró el grupo para el grado y la letra especificados."
        );

        if (asistencia.getMes() == null || asistencia.getDia() == null) {
            throw new SostenedorException("Debe especificar mes y día para obtener el calendario del año vigente.");
        }

        Long calendarioId = checkFound(
                asistenciaTableRepository.findCalendarioByGrupoFecha(grupoId, String.valueOf(LocalDate.now().getYear()), asistencia.getMes(), asistencia.getDia()),
                "No se encontró el calendario para el grupo y la fecha especificados."
        );

        Long personaId = checkFound(
                asistenciaTableRepository.findPersonaByRut(asistencia.getRut()),
                "No se encontró la persona para el rut especificado."
        );

        Long parvuloId = checkFound(
                asistenciaTableRepository.findParvuloByPersona(personaId),
                "No se encontró el párvulo para la persona especificada."
        );

        Long matriculaUeId = checkFound(
                asistenciaTableRepository.findMatriculaUnidadEducativa(parvuloId, unidadEducativaId),
                "No se encontró la matrícula de la unidad educativa para el párvulo y la UE especificados."
        );

        Long matriculaGrupoId = checkFound(
                asistenciaTableRepository.findMatriculaGrupo(grupoId, matriculaUeId),
                "No se encontró la matrícula del grupo."
        );

        validarReglasAsistencia(matriculaGrupoId);

        AsistenciaEntity entity = toEntity(asistencia, calendarioId, matriculaGrupoId);

        asistenciaTableRepository.save(entity);

        return toModel(entity, asistencia);
    }


    @Override
    @Transactional
    public AsistenciaModel update(Long id, AsistenciaIndividualModel asistencia) {
        Long unidadEducativaId = checkFound(
                asistenciaTableRepository.findUnidadEducativaByRbd(asistencia.getRbd()),
                "No se encontró la Unidad Educativa para el RBD especificado."
        );

        Long nivelGradoId = checkFound(
                asistenciaTableRepository.findNivelGradoIdByNombre(asistencia.getGrado()),
                "No se encontró el nivel de grado para el grado especificado."
        );

        Long gradoId = checkFound(
                asistenciaTableRepository.findGradoByUnidadEducativaAndNivelGrado(unidadEducativaId, nivelGradoId),
                "No se encontró el grado para la unidad educativa y el nivel de grado."
        );

        Long grupoId = checkFound(
                asistenciaTableRepository.findGrupoByGradoLetra(gradoId, asistencia.getLetra()),
                "No se encontró el grupo para el grado y la letra especificados."
        );

        if (asistencia.getMes() == null || asistencia.getDia() == null) {
            throw new SostenedorException("Debe especificar mes y día para obtener el calendario del año vigente.");
        }

        Long calendarioId = checkFound(
                asistenciaTableRepository.findCalendarioByGrupoFecha(grupoId, String.valueOf(LocalDate.now().getYear()), asistencia.getMes(), asistencia.getDia()),
                "No se encontró el calendario para el grupo y la fecha especificados."
        );

        Long personaId = checkFound(
                asistenciaTableRepository.findPersonaByRut(asistencia.getRut()),
                "No se encontró la persona para el rut especificado."
        );

        Long parvuloId = checkFound(
                asistenciaTableRepository.findParvuloByPersona(personaId),
                "No se encontró el párvulo para la persona especificada."
        );

        Long matriculaUeId = checkFound(
                asistenciaTableRepository.findMatriculaUnidadEducativa(parvuloId, unidadEducativaId),
                "No se encontró la matrícula de la unidad educativa para el párvulo y la UE especificados."
        );

        Long matriculaGrupoId = checkFound(
                asistenciaTableRepository.findMatriculaGrupo(grupoId, matriculaUeId),
                "No se encontró la matrícula del grupo."
        );

        validarReglasAsistencia(matriculaGrupoId);
        asistencia.setId(id);

        AsistenciaEntity entity = toEntity(asistencia, calendarioId, matriculaGrupoId);

        asistenciaTableRepository.update(id, entity);

        return toModel(entity, asistencia);
    }

    /**
     * Método que ejecuta validaciones extra de negocio
     * (Grupo con docente, fecha calendario habilitada, etc.).
     */
    private void validarReglasAsistencia(Long matriculaGrupo) {
        Boolean tieneDocente = asistenciaTableRepository.validarGrupoTieneDocenteAsistente(matriculaGrupo);
        if (Boolean.FALSE.equals(tieneDocente)) {
            throw new SostenedorException("No se puede registrar asistencia: el grupo no tiene docente/asistente asignado.");
        }

    }

    /**
     * Verifica que el valor no sea null, si es null lanza excepción con el mensaje.
     */
    private Long checkFound(Long value, String errorMessage) {
        if (value == null) {
            throw new SostenedorException(errorMessage);
        }
        return value;
    }


    /**
     * Convierte el AsistenciaIndividualModel + IDs calculados en la entidad AsistenciaEntity.
     * (Ajustar según tu DB, si usas jsonAsistencia, etc.).
     */
    private AsistenciaEntity toEntity(AsistenciaIndividualModel model, Long calendarioId, Long matriculaGrupoId) {
        AsistenciaEntity e = new AsistenciaEntity();
        e.setId(model.getId());
        e.setCalendarioId(calendarioId);
        e.setMatriculaGrupoId(matriculaGrupoId);
        e.setPresente(model.getPresente());
        return e;
    }


}
