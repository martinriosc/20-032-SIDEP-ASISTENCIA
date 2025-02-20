package cl.mineduc.sidep.asistenciaapi.repositories;

import cl.mineduc.sidep.asistenciaapi.entities.ProcesoEntity;
import cl.mineduc.sidep.asistenciaapi.exceptions.SidepException;
import cl.mineduc.sidep.asistenciaapi.mappers.ProcesoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ProcesoRepository {

    private final ProcesoMapper procesoMapper;

    public void save(ProcesoEntity e) {
        try {
            this.procesoMapper.save(e);
        } catch (MyBatisSystemException ex) {
            log.error(ex.getMessage());
            throw new SidepException("Error al save proceso", ex);
        }
    }
}
