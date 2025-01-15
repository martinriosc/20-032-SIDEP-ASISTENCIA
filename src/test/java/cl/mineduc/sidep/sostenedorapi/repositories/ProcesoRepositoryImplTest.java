package cl.mineduc.sidep.sostenedorapi.repositories;

import cl.mineduc.sidep.sostenedorapi.entities.ProcesoEntity;
import cl.mineduc.sidep.sostenedorapi.exceptions.SidepException;
import cl.mineduc.sidep.sostenedorapi.mappers.ProcesoMapper;
import cl.mineduc.sidep.sostenedorapi.utils.ProcesoUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class ProcesoRepositoryImplTest {

    @InjectMocks
    private ProcesoRepositoryImpl procesoRepository;

    @Mock
    private ProcesoMapper procesoMapper;

    @Test
    public void save() {
        doNothing().when(procesoMapper).save(any());
        this.procesoRepository.save(ProcesoUtils.getProcesoEntity(200, "test", "test"));
        verify(procesoMapper).save(any());
    }

    @Test(expected = SidepException.class)
    public void save_exception() {
        doThrow(MyBatisSystemException.class).when(procesoMapper).save(any());
        this.procesoRepository.save(ProcesoUtils.getProcesoEntity(200, "test", "test"));
    }

}