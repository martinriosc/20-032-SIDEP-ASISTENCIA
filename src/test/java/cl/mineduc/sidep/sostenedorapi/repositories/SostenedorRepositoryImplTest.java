package cl.mineduc.sidep.sostenedorapi.repositories;

import cl.mineduc.sidep.sostenedorapi.entities.SostenedorEntity;
import cl.mineduc.sidep.sostenedorapi.exceptions.SostenedorException;
import cl.mineduc.sidep.sostenedorapi.filter.SostenedorFilter;
import cl.mineduc.sidep.sostenedorapi.mappers.SostenedorMapper;
import cl.mineduc.sidep.sostenedorapi.model.SostenedorModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class SostenedorRepositoryImplTest {

    @InjectMocks
    private SostenedorRepositoryImpl sostenedorRepository;

    @Mock
    private SostenedorMapper sostenedorMapper;

    @Test
    public void findAll() {
        when(sostenedorMapper.findAll(any()))
                .thenReturn(Collections.singletonList(new SostenedorModel()));
        assertNotNull(this.sostenedorRepository.findAll(SostenedorFilter.builder().build()));
    }

    @Test(expected = SostenedorException.class)
    public void findAll_exeption() {
        when(sostenedorMapper.findAll(any())).thenThrow(MyBatisSystemException.class);
        this.sostenedorRepository.findAll(SostenedorFilter.builder().build());
    }

    @Test
    public void findById() {
        when(sostenedorMapper.findById(any())).thenReturn(new SostenedorModel());
        assertNotNull(this.sostenedorRepository.findById(1L));
    }

    @Test(expected = SostenedorException.class)
    public void findById_exception() {
        when(sostenedorMapper.findById(any())).thenThrow(MyBatisSystemException.class);
        this.sostenedorRepository.findById(1L);
    }

    @Test
    public void save() {
        when(sostenedorMapper.insert(any())).thenReturn(1L);
        this.sostenedorRepository.save(new SostenedorEntity());
        verify(sostenedorMapper, times(1)).insert(any());
    }

    @Test(expected = SostenedorException.class)
    public void save_exception() {
        when(sostenedorMapper.insert(any())).thenThrow(MyBatisSystemException.class);
        this.sostenedorRepository.save(new SostenedorEntity());
    }

    @Test
    public void existsByRut() {
        when(sostenedorMapper.existsByRut(anyInt(), anyString())).thenReturn(true);
        assertTrue(this.sostenedorRepository.existsByRut(1, "9"));
    }

    @Test(expected = SostenedorException.class)
    public void exisstsByRut_exception() {
        when(sostenedorMapper.existsByRut(anyInt(), anyString())).thenThrow(MyBatisSystemException.class);
        this.sostenedorRepository.existsByRut(1, "9");
    }

    @Test
    public void update() {
        doNothing().when(sostenedorMapper).updateSostenedor(any(), anyLong());
        this.sostenedorRepository.update(new SostenedorEntity(), 1L);
        verify(sostenedorMapper, times(1)).updateSostenedor(any(), any());
    }

    @Test(expected = SostenedorException.class)
    public void update_exception() {
        doThrow(MyBatisSystemException.class).when(sostenedorMapper).updateSostenedor(any(), any());
        this.sostenedorRepository.update(new SostenedorEntity(), 1L);
    }

    @Test
    public void hasUnidadEducativa() {
        when(sostenedorMapper.hasUnidadEducativa(anyLong())).thenReturn(true);
        assertTrue(this.sostenedorRepository.hasUnidadEducativa(1L));
    }

    @Test(expected = SostenedorException.class)
    public void hasUnidadEducativa_exception() {
        when(sostenedorMapper.hasUnidadEducativa(anyLong())).thenThrow(MyBatisSystemException.class);
        this.sostenedorRepository.hasUnidadEducativa(1L);
    }

    @Test
    public void delete() {
        doNothing().when(sostenedorMapper).delete(anyLong());
        this.sostenedorRepository.delete(1L);
        verify(sostenedorMapper, times(1)).delete(any());
    }

    @Test(expected = SostenedorException.class)
    public void delete_exception() {
        doThrow(MyBatisSystemException.class).when(sostenedorMapper).delete(any());
        this.sostenedorRepository.delete(1L);
    }

    @Test
    public void countTotal() {
        when(sostenedorMapper.countTotal(any())).thenReturn(1L);
        assertNotNull(this.sostenedorRepository.countTotal(SostenedorFilter.builder().build()));
    }

    @Test(expected = SostenedorException.class)
    public void countTotal_exception() {
        when(sostenedorMapper.countTotal(any())).thenThrow(MyBatisSystemException.class);
        this.sostenedorRepository.countTotal(SostenedorFilter.builder().build());
    }
}