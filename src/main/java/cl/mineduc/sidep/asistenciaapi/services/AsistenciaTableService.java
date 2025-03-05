package cl.mineduc.sidep.asistenciaapi.services;

import cl.mineduc.sidep.asistenciaapi.entities.AsistenciaEntity;
import cl.mineduc.sidep.asistenciaapi.model.AsistenciaIndividualModel;
import cl.mineduc.sidep.asistenciaapi.model.AsistenciaModel;

public interface AsistenciaTableService {

    AsistenciaModel save(AsistenciaIndividualModel asistencia);

    AsistenciaModel update(Long id, AsistenciaIndividualModel as);


    default AsistenciaModel toModel(AsistenciaEntity entity, AsistenciaIndividualModel asistencia) {
        AsistenciaModel model = new AsistenciaModel();

        model.setId(entity.getId());
        model.setRbd(asistencia.getRbd().toString());
        model.setGrado(asistencia.getGrado().toString());
        model.setLetra(asistencia.getLetra());
        model.setRut(Long.valueOf(asistencia.getRut()));
        model.setPresente(asistencia.getPresente());

        return model;
    }

}
