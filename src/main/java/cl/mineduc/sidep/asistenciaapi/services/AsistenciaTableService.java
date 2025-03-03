package cl.mineduc.sidep.asistenciaapi.services;

import cl.mineduc.sidep.asistenciaapi.entities.AsistenciaEntity;
import cl.mineduc.sidep.asistenciaapi.model.AsistenciaIndividualModel;
import cl.mineduc.sidep.asistenciaapi.model.AsistenciaModel;

public interface AsistenciaTableService {

    AsistenciaModel save(AsistenciaIndividualModel asistencia);

    AsistenciaModel update(Long id, AsistenciaIndividualModel as);

    default AsistenciaEntity toEntity(AsistenciaIndividualModel as, Long matriculaGrupo) {
        AsistenciaEntity entity = new AsistenciaEntity();

        entity.setCalendarioId(as.getCalendarioId());
        entity.setMatriculaGrupoId(matriculaGrupo);

        //TODO: SETEAR JSON ASISTENCIA

        return entity;
    }

    default AsistenciaModel toModel(AsistenciaEntity entity, AsistenciaIndividualModel asistencia) {
        AsistenciaModel model = new AsistenciaModel();

        model.setId(entity.getId());
        model.setRbd(asistencia.getRbd().toString());
        model.setNivelGrado(String.valueOf(asistencia.getNivelGrado()));
        model.setLetra(asistencia.getLetra());
        model.setRut(asistencia.getRut());

        //TODO: SETEAR ASISTIO
        //result.setAsistio(asistencia.getPresente());

        model.setCalendarioId(entity.getCalendarioId());
        model.setMatriculaGrupoId(entity.getMatriculaGrupoId());
        model.setJsonAsistencia(entity.getJsonAsistencia());
        model.setJsonAsistencia2(entity.getJsonAsistencia2());
        model.setFechaRegistro(entity.getFechaCreacion());
        model.setFechaActualizacion(entity.getFechaActualizacion());

        return model;
    }

}
