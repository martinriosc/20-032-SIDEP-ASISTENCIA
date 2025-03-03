package cl.mineduc.sidep.asistenciaapi.repositories;

import cl.mineduc.sidep.asistenciaapi.entities.AsistenciaEntity;
import cl.mineduc.sidep.asistenciaapi.exceptions.SostenedorException;
import cl.mineduc.sidep.asistenciaapi.mappers.AsistenciaTableMapper;
import org.apache.ibatis.exceptions.PersistenceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mybatis.spring.MyBatisSystemException;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AsistenciaTableRepositoryImplTest {

    @Mock
    private AsistenciaTableMapper mapper;

    @InjectMocks
    private AsistenciaTableRepositoryImpl repository;

    @Test
    public void save_ok() {
        AsistenciaEntity entity = new AsistenciaEntity();
        doNothing().when(mapper).insert(entity);
        repository.save(entity);
        verify(mapper).insert(entity);
    }

    @Test(expected = SostenedorException.class)
    public void save_error() {
        AsistenciaEntity entity = new AsistenciaEntity();
        doThrow(new MyBatisSystemException(new PersistenceException("error")))
                .when(mapper).insert(any(AsistenciaEntity.class));
        repository.save(entity);
    }

    @Test
    public void update_ok() {
        AsistenciaEntity entity = new AsistenciaEntity();
        Long id = 1L;
        doNothing().when(mapper).update(id, entity);
        repository.update(id, entity);
        verify(mapper, times(1)).update(id, entity);
    }

    @Test(expected = SostenedorException.class)
    public void update_error() {
        AsistenciaEntity entity = new AsistenciaEntity();
        Long id = 1L;
        doThrow(new MyBatisSystemException(new PersistenceException())).when(mapper).update(id, entity);
        repository.update(id, entity);
    }

    @Test
    public void validarAsistencia_ok() {
        Long calendario = 20L, matriculaGrupo = 30L;
        when(mapper.validarAsistencia(calendario, matriculaGrupo)).thenReturn(true);
        Boolean result = repository.validarAsistencia(calendario, matriculaGrupo);
        assertTrue(result);
    }

    @Test(expected = SostenedorException.class)
    public void validarAsistencia_error() {
        Long calendario = 20L, matriculaGrupo = 30L;
        when(mapper.validarAsistencia(calendario, matriculaGrupo))
                .thenThrow(new MyBatisSystemException(new PersistenceException()));
        repository.validarAsistencia(calendario, matriculaGrupo);
    }

    @Test
    public void validarGrupoTieneDocenteAsistente_ok() {
        Long matriculaGrupo = 60L;
        when(mapper.validarGrupoTieneDocenteAsistente(matriculaGrupo)).thenReturn(true);
        Boolean result = repository.validarGrupoTieneDocenteAsistente(matriculaGrupo);
        assertTrue(result);
    }

    @Test(expected = SostenedorException.class)
    public void validarGrupoTieneDocenteAsistente_error() {
        Long matriculaGrupo = 60L;
        when(mapper.validarGrupoTieneDocenteAsistente(matriculaGrupo))
                .thenThrow(new MyBatisSystemException(new PersistenceException()));
        repository.validarGrupoTieneDocenteAsistente(matriculaGrupo);
    }

    @Test
    public void validarFechaCalendarioHabil_ok() {
        Long calendario = 20L;
        when(mapper.validarFechaCalendarioHabil(calendario)).thenReturn(true);
        Boolean result = repository.validarFechaCalendarioHabil(calendario);
        assertTrue(result);
    }

}