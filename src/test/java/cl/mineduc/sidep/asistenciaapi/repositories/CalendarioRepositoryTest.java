package cl.mineduc.sidep.asistenciaapi.repositories;

import cl.mineduc.sidep.asistenciaapi.entities.CalendarioEntity;
import cl.mineduc.sidep.asistenciaapi.exceptions.SidepException;
import cl.mineduc.sidep.asistenciaapi.filter.CalendarioFilter;
import cl.mineduc.sidep.asistenciaapi.mappers.CalendarioMapper;
import cl.mineduc.sidep.asistenciaapi.model.CalendarioModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class CalendarioRepositoryTest {

    @Mock
    private CalendarioMapper calendarioMapper;

    @InjectMocks
    private CalendarioRepository calendarioRepository;

    @Test
    public void findAll_ok() {
        CalendarioFilter filter = CalendarioFilter.builder().build();
        List<CalendarioModel> list = Collections.singletonList(new CalendarioModel());
        when(calendarioMapper.findAll(any(CalendarioFilter.class))).thenReturn(list);
        List<CalendarioModel> result = calendarioRepository.findAll(filter);
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test(expected = SidepException.class)
    public void findAll_exception() {
        CalendarioFilter filter = CalendarioFilter.builder().build();

        when(calendarioMapper.findAll(any(CalendarioFilter.class))).thenThrow(MyBatisSystemException.class);
        calendarioRepository.findAll(filter);
    }

    @Test
    public void countTotal_ok() {
        CalendarioFilter filter = CalendarioFilter.builder().build();

        when(calendarioMapper.countTotal(any(CalendarioFilter.class))).thenReturn(5L);
        Long total = calendarioRepository.countTotal(filter);
        assertEquals(Long.valueOf(5L), total);
    }

    @Test(expected = SidepException.class)
    public void countTotal_exception() {
        CalendarioFilter filter = CalendarioFilter.builder().build();

        when(calendarioMapper.countTotal(any(CalendarioFilter.class))).thenThrow(MyBatisSystemException.class);
        calendarioRepository.countTotal(filter);
    }

    @Test
    public void findById_ok() {
        CalendarioModel calendario = new CalendarioModel();
        when(calendarioMapper.findById(anyLong())).thenReturn(calendario);
        CalendarioModel result = calendarioRepository.findById(1L);
        assertNotNull(result);
    }

    @Test(expected = SidepException.class)
    public void findById_exception() {
        when(calendarioMapper.findById(anyLong())).thenThrow(MyBatisSystemException.class);
        calendarioRepository.findById(1L);
    }

    @Test
    public void save_ok() {
        CalendarioEntity entity = new CalendarioEntity();
        when(calendarioMapper.insert(any(CalendarioEntity.class))).thenReturn(10L);
        calendarioRepository.save(entity);
        assertEquals(Long.valueOf(10L), entity.getId());
        verify(calendarioMapper, times(1)).insert(any(CalendarioEntity.class));
    }

    @Test(expected = SidepException.class)
    public void save_exception() {
        when(calendarioMapper.insert(any(CalendarioEntity.class))).thenThrow(MyBatisSystemException.class);
        calendarioRepository.save(new CalendarioEntity());
    }

    @Test
    public void update_ok() {
        CalendarioEntity entity = new CalendarioEntity();
        entity.setId(15L);
        doNothing().when(calendarioMapper).update(any(), anyLong());
        calendarioRepository.update(entity);
        verify(calendarioMapper, times(1)).update(any(CalendarioEntity.class), eq(15L));
    }

    @Test(expected = SidepException.class)
    public void update_exception() {
        CalendarioEntity entity = new CalendarioEntity();
        entity.setId(15L);
        doThrow(MyBatisSystemException.class).when(calendarioMapper).update(any(), anyLong());
        calendarioRepository.update(entity);
    }

    @Test
    public void delete_ok() {
        doNothing().when(calendarioMapper).delete(anyLong());
        calendarioRepository.delete(20L);
        verify(calendarioMapper, times(1)).delete(eq(20L));
    }

    @Test(expected = SidepException.class)
    public void delete_exception() {
        doThrow(MyBatisSystemException.class).when(calendarioMapper).delete(anyLong());
        calendarioRepository.delete(20L);
    }
}
