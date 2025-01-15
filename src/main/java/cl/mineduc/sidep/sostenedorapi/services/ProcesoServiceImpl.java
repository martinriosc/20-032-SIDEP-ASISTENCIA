package cl.mineduc.sidep.sostenedorapi.services;

import cl.mineduc.sidep.sostenedorapi.entities.ProcesoEntity;
import cl.mineduc.sidep.sostenedorapi.repositories.ProcesoRepository;
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
