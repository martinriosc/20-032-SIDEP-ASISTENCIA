package cl.mineduc.sidep.asistenciaapi.services;

import cl.mineduc.sidep.asistenciaapi.entities.AsistenciaEntity;
import cl.mineduc.sidep.asistenciaapi.exceptions.SidepException;
import cl.mineduc.sidep.asistenciaapi.filter.AsistenciaFilter;
import cl.mineduc.sidep.asistenciaapi.model.AsistenciaIndividualModel;
import cl.mineduc.sidep.asistenciaapi.model.AsistenciaModel;
import cl.mineduc.sidep.asistenciaapi.model.CalendarioModel;
import cl.mineduc.sidep.asistenciaapi.model.PaginationResultModel;
import cl.mineduc.sidep.asistenciaapi.repositories.AsistenciaRepository;
import cl.mineduc.sidep.asistenciaapi.repositories.CalendarioRepository;
import cl.mineduc.sidep.asistenciaapi.services.impl.AsistenciaServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.MyBatisSystemException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class AsistenciaServiceImplTest {

    @Mock
    private AsistenciaRepository asistenciaRepository;

    @Mock
    private CalendarioRepository calendarioRepository;

    @InjectMocks
    private AsistenciaServiceImpl asistenciaService;

    @Before
    public void setUp() {
    }

    @Test
    public void updateAsistenciaPupiloPorDia_insertCase() {
        AsistenciaIndividualModel input = new AsistenciaIndividualModel();
        input.setRut(12345678);
        input.setPresente(true);
        input.setDia("15");
        input.setMes("03");

        CalendarioModel calendario = new CalendarioModel();
        calendario.setTrabajado(false);
        when(calendarioRepository.findByDiaMes(15, 3)).thenReturn(calendario);

        // Caso sin calendario (o calendario no seteado)
        doAnswer(invocation -> {
            AsistenciaEntity e = invocation.getArgument(0);
            e.setId(99L);
            return null;
        }).when(asistenciaRepository).save(any(AsistenciaEntity.class));

        AsistenciaModel mockedDbRecord = new AsistenciaModel();
        mockedDbRecord.setId(99L);
        mockedDbRecord.setRut(12345678);
        mockedDbRecord.setAsistio(true);

        when(asistenciaRepository.findById(eq(99L))).thenReturn(mockedDbRecord);

        AsistenciaModel result = asistenciaService.updateAsistenciaPupiloPorDia(input);

        assertNotNull(result);
        assertEquals(Long.valueOf(99L), result.getId());
        assertTrue(result.getAsistio());
        verify(asistenciaRepository, times(1)).save(any(AsistenciaEntity.class));
        verify(asistenciaRepository, never()).update(any(AsistenciaEntity.class));
        verify(asistenciaRepository, times(1)).findById(99L);
    }

    @Test
    public void updateAsistenciaPupiloPorDia_updateCase() {
        AsistenciaIndividualModel input = new AsistenciaIndividualModel();
        input.setId(10L);
        input.setRut(12345678);
        input.setPresente(false);
        input.setDia("15");
        input.setMes("03");
        input.setCalendarioId(10L);

        CalendarioModel calendario = new CalendarioModel();
        calendario.setTrabajado(false);
        when(calendarioRepository.findByDiaMes(15, 3)).thenReturn(calendario);

        when(asistenciaRepository.update(any(AsistenciaEntity.class))).thenReturn(null);

        AsistenciaModel mockedDbRecord = new AsistenciaModel();
        mockedDbRecord.setId(10L);
        mockedDbRecord.setRut(12345678);
        mockedDbRecord.setAsistio(false);

        when(asistenciaRepository.findById(eq(10L))).thenReturn(mockedDbRecord);

        AsistenciaModel result = asistenciaService.updateAsistenciaPupiloPorDia(input);

        assertNotNull(result);
        assertEquals(Long.valueOf(10L), result.getId());
        assertFalse(result.getAsistio());
        verify(asistenciaRepository, times(1)).update(any(AsistenciaEntity.class));
        verify(asistenciaRepository, never()).save(any(AsistenciaEntity.class));
        verify(asistenciaRepository, times(1)).findById(10L);
    }

    @Test(expected = SidepException.class)
    public void updateAsistenciaPupiloPorDia_saveException() {
        AsistenciaIndividualModel input = new AsistenciaIndividualModel();
        input.setRut(12345678);
        input.setPresente(true);

        doThrow(new MyBatisSystemException(new Exception()))
                .when(asistenciaRepository).save(any());

        asistenciaService.updateAsistenciaPupiloPorDia(input);
    }


    @Test
    public void updateAsistenciaPupiloPorDia_calendarioNoTrabajado() {
        AsistenciaIndividualModel input = new AsistenciaIndividualModel();
        input.setRut(12345678);
        input.setCalendarioId(100L);
        input.setDia("15");
        input.setMes("03");

        CalendarioModel calendario = new CalendarioModel();
        calendario.setTrabajado(false);
        when(calendarioRepository.findByDiaMes(15, 3)).thenReturn(calendario);

        doAnswer(invocation -> {
            AsistenciaEntity e = invocation.getArgument(0);
            e.setId(101L);
            return null;
        }).when(asistenciaRepository).save(any(AsistenciaEntity.class));

        AsistenciaModel mockedDbRecord = new AsistenciaModel();
        mockedDbRecord.setId(101L);
        mockedDbRecord.setRut(12345678);
        when(asistenciaRepository.findById(eq(101L))).thenReturn(mockedDbRecord);

        AsistenciaModel result = asistenciaService.updateAsistenciaPupiloPorDia(input);

        assertNotNull(result);
        assertEquals(Long.valueOf(101L), result.getId());
    }

    @Test(expected = SidepException.class)
    public void updateAsistenciaPupiloPorDia_calendarioTrabajado() {
        AsistenciaIndividualModel input = new AsistenciaIndividualModel();
        input.setRut(12345678);
        input.setCalendarioId(100L);

        CalendarioModel calendario = new CalendarioModel();
        calendario.setTrabajado(true);
        when(calendarioRepository.findById(100L)).thenReturn(calendario);

        asistenciaService.updateAsistenciaPupiloPorDia(input);
    }

    @Test
    public void updateAsistenciaGrupalPupiloPorDia() {
        AsistenciaIndividualModel a1 = new AsistenciaIndividualModel();
        AsistenciaIndividualModel a2 = new AsistenciaIndividualModel();
        a1.setRut(11111111);
        a2.setRut(22222222);

        doAnswer(inv -> {
            AsistenciaEntity e = inv.getArgument(0);
            e.setId(1L);
            return null;
        }).when(asistenciaRepository).save(any(AsistenciaEntity.class));

        when(asistenciaRepository.update(any(AsistenciaEntity.class))).thenReturn(null);

        AsistenciaModel mockedDbRecord = new AsistenciaModel();
        mockedDbRecord.setId(1L);
        mockedDbRecord.setAsistio(true);

        when(asistenciaRepository.findById(anyLong())).thenReturn(mockedDbRecord);

        List<AsistenciaIndividualModel> asistencias = Arrays.asList(a1, a2);

        List<AsistenciaModel> result = asistenciaService.updateAsistenciaGrupalPupiloPorDia(asistencias);
        assertNotNull(result);
        for (AsistenciaModel model : result) {
            assertTrue(model.getAsistio());
        }
        verify(asistenciaRepository, times(2)).findById(anyLong());
    }

    @Test(expected = SidepException.class)
    public void updateAsistenciaGrupalPupiloPorDia_exception() {
        AsistenciaIndividualModel a1 = new AsistenciaIndividualModel();
        a1.setRut(11111111);
        List<AsistenciaIndividualModel> asistencias = Collections.singletonList(a1);

        doThrow(new MyBatisSystemException(new Exception()))
                .when(asistenciaRepository).save(any());
        asistenciaService.updateAsistenciaGrupalPupiloPorDia(asistencias);
    }

    @Test
    public void findAsistencia() {
        when(asistenciaRepository.findAll(any(AsistenciaFilter.class)))
                .thenReturn(Collections.singletonList(new AsistenciaModel()));
        when(asistenciaRepository.countTotal(any(AsistenciaFilter.class)))
                .thenReturn(1L);

        PaginationResultModel<AsistenciaModel> result =
                asistenciaService.findAsistencia("123", "ENSE", "1", "A", 11111111L);

        assertNotNull(result);
        assertEquals(Long.valueOf(1L), result.getTotalElementos());
        assertEquals(Long.valueOf(1L), result.getTotalPaginas());
    }

    @Test(expected = SidepException.class)
    public void findAsistencia_exception() {
        doThrow(new MyBatisSystemException(new Exception()))
                .when(asistenciaRepository).findAll(any());
        asistenciaService.findAsistencia("123", "ENSE", "1", "A", 11111111L);
    }

    @Test
    public void findAsistenciaPorMesAndDia() {
        when(asistenciaRepository.findAll(any(AsistenciaFilter.class)))
                .thenReturn(Collections.emptyList());
        when(asistenciaRepository.countTotal(any(AsistenciaFilter.class)))
                .thenReturn(0L);

        PaginationResultModel<AsistenciaModel> result =
                asistenciaService.findAsistenciaPorMesAndDia("123", "ENSE", "1", "A", "01", "15", 11111111L);

        assertNotNull(result);
        assertEquals(Long.valueOf(0L), result.getTotalElementos());
        assertEquals(Long.valueOf(0L), result.getTotalPaginas());
    }

    @Test(expected = SidepException.class)
    public void findAsistenciaPorMesAndDia_exception() {
        doThrow(new MyBatisSystemException(new Exception()))
                .when(asistenciaRepository).findAll(any());
        asistenciaService.findAsistenciaPorMesAndDia("123", "ENSE", "1", "A", "01", "15", 11111111L);
    }

    @Test
    public void findAllAsistencia() {
        when(asistenciaRepository.findAll(any(AsistenciaFilter.class)))
                .thenReturn(Arrays.asList(new AsistenciaModel(), new AsistenciaModel()));
        when(asistenciaRepository.countTotal(any(AsistenciaFilter.class)))
                .thenReturn(2L);

        PaginationResultModel<AsistenciaModel> result = asistenciaService.findAllAsistencia(
                "2020-01-01", "2020-12-31", "EstX", "RegX", "ProvX", "ComX", 10, 0
        );

        assertNotNull(result);
        assertEquals(Long.valueOf(2L), result.getTotalElementos());
        // Se calcula totalPaginas según la cantidad de elementos en la lista
        assertEquals(Long.valueOf(1L), result.getTotalPaginas());
    }

    @Test(expected = SidepException.class)
    public void findAllAsistencia_exception() {
        doThrow(new MyBatisSystemException(new Exception()))
                .when(asistenciaRepository).findAll(any());
        asistenciaService.findAllAsistencia("2020-01-01", "2020-12-31", "EstX", "RegX", "ProvX", "ComX", 10, 0);
    }
}
