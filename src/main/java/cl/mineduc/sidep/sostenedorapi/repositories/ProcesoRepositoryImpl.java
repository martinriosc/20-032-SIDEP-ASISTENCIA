package cl.mineduc.sidep.sostenedorapi.repositories;

import cl.mineduc.sidep.sostenedorapi.entities.ProcesoEntity;
import cl.mineduc.sidep.sostenedorapi.exceptions.SidepException;
import cl.mineduc.sidep.sostenedorapi.mappers.ProcesoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ProcesoRepositoryImpl implements ProcesoRepository {

    private final ProcesoMapper procesoMapper;

    @Override
    public void save(ProcesoEntity e) {
        try {
            this.procesoMapper.save(e);
        } catch (MyBatisSystemException ex) {
            log.error(ex.getMessage());
            throw new SidepException("Error al save proceso", ex);
        }
    }
}
