package cl.mineduc.sidep.asistenciaapi.repositories;

import cl.mineduc.sidep.asistenciaapi.entities.AsistenciaEntity;
import cl.mineduc.sidep.asistenciaapi.exceptions.SidepException;
import cl.mineduc.sidep.asistenciaapi.filter.AsistenciaFilter;
import cl.mineduc.sidep.asistenciaapi.mappers.AsistenciaMapper;
import cl.mineduc.sidep.asistenciaapi.model.AsistenciaModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class AsistenciaRepositoryTest {

    @Mock
    private AsistenciaMapper asistenciaMapper;

    @InjectMocks
    private AsistenciaRepository asistenciaRepository;

    @Test
    public void findAll_ok() {
        when(asistenciaMapper.findAll(any(AsistenciaFilter.class)))
                .thenReturn(Collections.singletonList(new AsistenciaModel()));
        assertNotNull(asistenciaRepository.findAll(AsistenciaFilter.builder().build()));
    }

    @Test(expected = SidepException.class)
    public void findAll_exception() {
        when(asistenciaMapper.findAll(any(AsistenciaFilter.class)))
                .thenThrow(MyBatisSystemException.class);
        asistenciaRepository.findAll(AsistenciaFilter.builder().build());
    }

    @Test
    public void countTotal_ok() {
        when(asistenciaMapper.countTotal(any(AsistenciaFilter.class)))
                .thenReturn(10L);
        assertEquals(Long.valueOf(10L), asistenciaRepository.countTotal(AsistenciaFilter.builder().build()));
    }

    @Test(expected = SidepException.class)
    public void countTotal_exception() {
        when(asistenciaMapper.countTotal(any(AsistenciaFilter.class)))
                .thenThrow(MyBatisSystemException.class);
        asistenciaRepository.countTotal(AsistenciaFilter.builder().build());
    }

    @Test
    public void findById_ok() {
        when(asistenciaMapper.findById(anyLong())).thenReturn(new AsistenciaModel());
        assertNotNull(asistenciaRepository.findById(1L));
    }

    @Test(expected = SidepException.class)
    public void findById_exception() {
        when(asistenciaMapper.findById(anyLong())).thenThrow(MyBatisSystemException.class);
        asistenciaRepository.findById(1L);
    }

    @Test
    public void save_ok() {
        when(asistenciaMapper.insert(any(AsistenciaEntity.class))).thenReturn(1L);
        AsistenciaEntity e = new AsistenciaEntity();
        asistenciaRepository.save(e);
        assertEquals(Long.valueOf(1L), e.getId());
        verify(asistenciaMapper, times(1)).insert(any());
    }

    @Test(expected = SidepException.class)
    public void save_exception() {
        when(asistenciaMapper.insert(any(AsistenciaEntity.class))).thenThrow(MyBatisSystemException.class);
        asistenciaRepository.save(new AsistenciaEntity());
    }

    @Test
    public void update_ok() {
        doNothing().when(asistenciaMapper).update(any(AsistenciaEntity.class));
        AsistenciaEntity e = new AsistenciaEntity();
        e.setId(10L);
        asistenciaRepository.update(e);
        verify(asistenciaMapper, times(1)).update(any(AsistenciaEntity.class));
    }

    @Test(expected = SidepException.class)
    public void update_exception() {
        doThrow(MyBatisSystemException.class).when(asistenciaMapper).update(any());
        AsistenciaEntity e = new AsistenciaEntity();
        e.setId(10L);
        asistenciaRepository.update(e);
    }

    @Test
    public void delete_ok() {
        doNothing().when(asistenciaMapper).delete(anyLong());
        asistenciaRepository.delete(5L);
        verify(asistenciaMapper, times(1)).delete(eq(5L));
    }

    @Test(expected = SidepException.class)
    public void delete_exception() {
        doThrow(MyBatisSystemException.class).when(asistenciaMapper).delete(anyLong());
        asistenciaRepository.delete(5L);
    }
}
