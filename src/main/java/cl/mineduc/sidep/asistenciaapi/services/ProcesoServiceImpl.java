package cl.mineduc.sidep.asistenciaapi.services;

import cl.mineduc.sidep.asistenciaapi.entities.ProcesoEntity;
import cl.mineduc.sidep.asistenciaapi.repositories.ProcesoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProcesoServiceImpl implements ProcesoService {

    private final ProcesoRepository repository;

    @Transactional
    @Override
    public void save(ProcesoEntity e) {
        this.repository.save(e);
    }
}
