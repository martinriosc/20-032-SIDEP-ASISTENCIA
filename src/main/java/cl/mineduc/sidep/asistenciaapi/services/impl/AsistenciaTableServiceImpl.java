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

@Service
@RequiredArgsConstructor
public class AsistenciaTableServiceImpl implements AsistenciaTableService {

    private final AsistenciaTableRepository asistenciaTableRepository;

    @Override
    @Transactional
    public AsistenciaModel save(AsistenciaIndividualModel asistencia) {
        Long grupo = checkFound(
                this.asistenciaTableRepository.findGrupoByRbdLetraNivelJornada(
                        asistencia.getGrado(),
                        asistencia.getLetra(),
                        asistencia.getJornada().getId(),
                        asistencia.getRbd()
                ),
                "No se encontró el grupo para la jornada, grado, letra y rbd especificados."
        );

        Long calendario = checkFound(
                this.asistenciaTableRepository.findCalendarioByGrupo(grupo),
                "No se encontró el calendario para el grupo especificado."
        );

        Long unidadEducativa = checkFound(
                this.asistenciaTableRepository.findUnidadEducativaByRbd(asistencia.getRbd()),
                "No se encontró la unidad educativa para el RBD especificado."
        );

        Long parvulo = checkFound(
                this.asistenciaTableRepository.findByRut(asistencia.getRut()),
                "No se encontró el párvulo para el rut especificado."
        );

        Long matriculaUe = checkFound(
                this.asistenciaTableRepository.findMatriculaUnidadEducativa(parvulo, unidadEducativa),
                "No se encontró la matrícula de la unidad educativa para el párvulo y unidad educativa especificados."
        );

        Long matriculaGrupo = checkFound(
                this.asistenciaTableRepository.findMatriculaGrupo(grupo, matriculaUe),
                "No se encontró la matrícula del grupo para el grupo y matrícula de la unidad educativa especificados."
        );

        validarReglasAsistencia(calendario, matriculaGrupo);

        asistencia.setCalendarioId(calendario);

        AsistenciaEntity entity = this.toEntity(asistencia, matriculaGrupo);
        this.asistenciaTableRepository.save(entity);

        return this.toModel(entity, asistencia);
    }

    @Override
    @Transactional
    public AsistenciaModel update(Long id, AsistenciaIndividualModel as) {
        return null;
    }

    private void validarReglasAsistencia(Long calendario, Long matriculaGrupo) {
        /*Boolean cumpleAsistencia = asistenciaTableRepository.validarAsistencia(calendario, matriculaGrupo);
        if (Boolean.FALSE.equals(cumpleAsistencia)) {
            throw new SostenedorException("No se cumplen las reglas de asistencia para la matrícula.");
        }*/ //TODO: VALIDAR REGLA DE NEGOCIO

        Boolean tieneDocente = asistenciaTableRepository.validarGrupoTieneDocenteAsistente(matriculaGrupo);
        if (Boolean.FALSE.equals(tieneDocente)) {
            throw new SostenedorException("No se puede registrar asistencia: el grupo no tiene docente/asistente asignado.");
        }

        Boolean fechaHabil = asistenciaTableRepository.validarFechaCalendarioHabil(calendario);
        if (Boolean.FALSE.equals(fechaHabil)) {
            throw new SostenedorException("La fecha del calendario no está habilitada para asistencia.");
        }
    }

    /**
     * Metodo para verificar que el valor no sea null.
     * Si es null, lanza la excepcion con el mensaje enviado.
     */
    private Long checkFound(Long value, String errorMessage) {
        if (value == null) {
            throw new SostenedorException(errorMessage);
        }
        return value;
    }

}
