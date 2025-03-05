package cl.mineduc.sidep.asistenciaapi.services;

import cl.mineduc.sidep.asistenciaapi.entities.AsistenciaEntity;
import cl.mineduc.sidep.asistenciaapi.exceptions.SidepException;
import cl.mineduc.sidep.asistenciaapi.filter.AsistenciaFilter;
import cl.mineduc.sidep.asistenciaapi.model.AsistenciaIndividualModel;
import cl.mineduc.sidep.asistenciaapi.model.AsistenciaModel;
import cl.mineduc.sidep.asistenciaapi.model.CalendarioModel;
import cl.mineduc.sidep.asistenciaapi.model.PaginationResultModel;
import cl.mineduc.sidep.asistenciaapi.repositories.AsistenciaRepository;
import cl.mineduc.sidep.asistenciaapi.repositories.AsistenciaTableRepository;
import cl.mineduc.sidep.asistenciaapi.repositories.CalendarioRepository;
import cl.mineduc.sidep.asistenciaapi.services.impl.AsistenciaServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.MyBatisSystemException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
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
    private AsistenciaTableRepository asistenciaTableRepository;

    @Mock
    private cl.mineduc.sidep.asistenciaapi.services.AsistenciaTableService asistenciaTableService;

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
        input.setGrado(1L);
        input.setLetra("A");

        when(asistenciaRepository.findAll(any(AsistenciaFilter.class)))
                .thenReturn(Collections.emptyList());

        int currentYear = LocalDate.now().getYear();
        CalendarioModel calendario = new CalendarioModel();
        calendario.setTrabajado(true);
        calendario.setId(200L);
        when(calendarioRepository.findByDiaMesAnio(eq(15), eq(3), eq(currentYear), anyLong()))
                .thenReturn(calendario);

        when(asistenciaTableRepository.findUnidadEducativaByRbd(anyInt())).thenReturn(30L);
        when(asistenciaTableRepository.findNivelGradoIdByNombre(anyLong())).thenReturn(40L);
        when(asistenciaTableRepository.findGradoByUnidadEducativaAndNivelGrado(anyLong(), anyLong())).thenReturn(50L);
        when(asistenciaTableRepository.findGrupoByGradoLetra(anyLong(), anyString())).thenReturn(60L);
        when(asistenciaTableRepository.findPersonaByRut(anyInt())).thenReturn(70L);
        when(asistenciaTableRepository.findParvuloByPersona(anyLong())).thenReturn(80L);
        when(asistenciaTableRepository.findMatriculaUnidadEducativa(anyLong(), anyLong())).thenReturn(90L);
        when(asistenciaTableRepository.findMatriculaGrupo(anyLong(), anyLong())).thenReturn(100L);

        when(asistenciaRepository.findByCalendarioAndMatriculaGrupo(eq(calendario.getId()), eq(100L)))
                .thenReturn(null);

        when(asistenciaTableService.save(any(AsistenciaIndividualModel.class)))
                .thenAnswer(invocation -> {
                    AsistenciaIndividualModel model = invocation.getArgument(0);
                    AsistenciaModel m = new AsistenciaModel();
                    m.setId(99L);
                    m.setRut(Long.valueOf(model.getRut()));
                    m.setPresente(model.getPresente());
                    return m;
                });

        AsistenciaModel result = asistenciaService.updateAsistenciaPupiloPorDia(input);

        assertNotNull(result);
        assertEquals(Long.valueOf(99L), result.getId());
        assertTrue(result.getPresente());
        verify(asistenciaTableService, times(1)).save(any(AsistenciaIndividualModel.class));
        verify(asistenciaRepository, never()).update(anyLong(), any(AsistenciaEntity.class));
    }


//    @Test
//    public void updateAsistenciaPupiloPorDia_updateCase() {
//        AsistenciaIndividualModel input = new AsistenciaIndividualModel();
//        input.setId(10L);
//        input.setRut(12345678);
//        input.setPresente(true);
//        input.setDia("15");
//        input.setMes("03");
//        input.setGrado(1L);
//        input.setLetra("A");
//
//        // Simular que ya existe una asistencia previa
//        when(asistenciaRepository.findAll(any(AsistenciaFilter.class)))
//                .thenReturn(Collections.singletonList(new AsistenciaModel() {{
//                    setId(10L);
//                }}));
//
//        // Stubear el cálculo de IDs necesarios
//        when(asistenciaTableRepository.findUnidadEducativaByRbd(eq(12345678))).thenReturn(30L);
//        when(asistenciaTableRepository.findNivelGradoIdByNombre(eq(1L))).thenReturn(40L);
//        when(asistenciaTableRepository.findGradoByUnidadEducativaAndNivelGrado(30L, 40L)).thenReturn(50L);
//        when(asistenciaTableRepository.findGrupoByGradoLetra(eq(50L), eq("A"))).thenReturn(60L);
//        when(asistenciaTableRepository.findPersonaByRut(eq(12345678))).thenReturn(70L);
//        when(asistenciaTableRepository.findParvuloByPersona(70L)).thenReturn(80L);
//        when(asistenciaTableRepository.findMatriculaUnidadEducativa(80L, 30L)).thenReturn(90L);
//        when(asistenciaTableRepository.findMatriculaGrupo(60L, 90L)).thenReturn(100L);
//
//        int currentYear = LocalDate.now().getYear();
//        CalendarioModel calendario = new CalendarioModel();
//        calendario.setTrabajado(true);
//        calendario.setId(200L);
//        calendario.setFecha(LocalDate.of(currentYear, 3, 15));
//
//        when(calendarioRepository.findByDiaMesAnio(eq(15), eq(3), eq(currentYear), eq(60L)))
//                .thenReturn(calendario);
//
//        when(asistenciaRepository.findByCalendarioAndMatriculaGrupo(eq(calendario.getId()), eq(100L)))
//                .thenReturn(new AsistenciaModel() {{
//                    setId(10L);
//                }});
//
//        when(asistenciaTableService.update(eq(10L), any(AsistenciaIndividualModel.class)))
//                .thenAnswer(invocation -> {
//                    AsistenciaModel m = new AsistenciaModel();
//                    m.setId(10L);
//                    m.setRut(12345678L);
//                    m.setPresente(false); // Simula que se cambia el flag a false
//                    return m;
//                });
//
//        when(asistenciaRepository.findById(eq(10L)))
//                .thenReturn(new AsistenciaModel() {{
//                    setId(10L);
//                    setRut(12345678L);
//                    setPresente(false);
//                }});
//
//        AsistenciaModel result = asistenciaService.updateAsistenciaPupiloPorDia(input);
//
//        assertNotNull(result);
//        assertEquals(Long.valueOf(10L), result.getId());
//        assertFalse(result.getPresente());
//        verify(asistenciaTableService, times(1)).update(eq(10L), any(AsistenciaIndividualModel.class));
//        verify(asistenciaTableService, never()).save(any(AsistenciaIndividualModel.class));
//        verify(asistenciaRepository, times(1)).findById(10L);
//    }




    @Test(expected = SidepException.class)
    public void updateAsistenciaPupiloPorDia_saveException() {
        AsistenciaIndividualModel input = new AsistenciaIndividualModel();
        input.setRut(12345678);
        input.setPresente(true);
        input.setDia("15");
        input.setMes("03");

        when(calendarioRepository.findByDiaMesAnio(eq(15), eq(3), eq(LocalDate.now().getYear()), anyLong()))
                .thenReturn(new CalendarioModel() {{ setTrabajado(true); }});

        when(asistenciaRepository.findAll(any(AsistenciaFilter.class)))
                .thenReturn(Collections.emptyList());

        doThrow(new MyBatisSystemException(new Exception()))
                .when(asistenciaTableService).save(any(AsistenciaIndividualModel.class));

        asistenciaService.updateAsistenciaPupiloPorDia(input);
    }

    @Test(expected = SidepException.class)
    public void updateAsistenciaPupiloPorDia_calendarioNoTrabajado() {
        AsistenciaIndividualModel input = new AsistenciaIndividualModel();
        input.setRut(12345678);
        input.setCalendarioId(100L);
        asistenciaService.updateAsistenciaPupiloPorDia(input);
    }

    @Test(expected = SidepException.class)
    public void updateAsistenciaPupiloPorDia_calendarioTrabajado_exception() {
        AsistenciaIndividualModel input = new AsistenciaIndividualModel();
        input.setRut(12345678);
        input.setDia("15");
        input.setMes("03");
        input.setCalendarioId(100L);

        CalendarioModel calendario = new CalendarioModel();
        calendario.setTrabajado(false);
        when(calendarioRepository.findByDiaMesAnio(eq(15), eq(3), eq(LocalDate.now().getYear()), anyLong()))
                .thenReturn(calendario);

        asistenciaService.updateAsistenciaPupiloPorDia(input);
    }


    @Test
    public void updateAsistenciaGrupalPupiloPorDia() {
        AsistenciaIndividualModel a1 = new AsistenciaIndividualModel();
        AsistenciaIndividualModel a2 = new AsistenciaIndividualModel();
        a1.setRut(11111111);
        a2.setRut(22222222);
        a1.setDia("15");
        a1.setMes("03");
        a1.setRbd(1111);
        a1.setGrado(1L);
        a1.setLetra("A");
        a1.setPresente(true);
        a1.setJornada(cl.mineduc.sidep.asistenciaapi.enums.TipoJornada.MANANA);
        a2.setDia("15");
        a2.setMes("03");
        a2.setRbd(1111);
        a2.setGrado(1L);
        a2.setLetra("A");
        a2.setPresente(true);
        a2.setJornada(cl.mineduc.sidep.asistenciaapi.enums.TipoJornada.MANANA);

        CalendarioModel calendario = new CalendarioModel();
        calendario.setTrabajado(true);
        calendario.setId(200L);
        when(calendarioRepository.findByDiaMesAnio(anyInt(), anyInt(), anyInt(), anyLong()))
                .thenReturn(calendario);

        when(asistenciaRepository.findAll(any(AsistenciaFilter.class)))
                .thenReturn(Collections.emptyList());

        doAnswer(invocation -> {
            AsistenciaIndividualModel model = invocation.getArgument(0);
            AsistenciaModel m = new AsistenciaModel();
            m.setId(1L);
            m.setPresente(model.getPresente());
            m.setRut(Long.valueOf(model.getRut()));
            m.setGrado(model.getGrado().toString());
            m.setRbd(model.getRbd().toString());
            m.setLetra(model.getLetra());
            m.setFechaRegistro("2025-03-15");
            return m;
        }).when(asistenciaTableService).save(any(AsistenciaIndividualModel.class));

        List<AsistenciaIndividualModel> asistencias = Arrays.asList(a1, a2);
        List<AsistenciaModel> result = asistenciaService.updateAsistenciaGrupalPupiloPorDia(asistencias);

        assertNotNull(result);
        for (AsistenciaModel model : result) {
            assertTrue(model.getPresente());
        }
        verify(asistenciaTableService, times(2)).save(any(AsistenciaIndividualModel.class));
    }



    @Test(expected = SidepException.class)
    public void updateAsistenciaGrupalPupiloPorDia_exception() {
        AsistenciaIndividualModel a1 = new AsistenciaIndividualModel();
        a1.setRut(11111111);
        List<AsistenciaIndividualModel> asistencias = Collections.singletonList(a1);
        doThrow(new MyBatisSystemException(new Exception()))
                .when(asistenciaTableService).save(any(AsistenciaIndividualModel.class));
        asistenciaService.updateAsistenciaGrupalPupiloPorDia(asistencias);
    }

    @Test
    public void findAsistencia() {
        when(asistenciaRepository.findAll(any(AsistenciaFilter.class)))
                .thenReturn(Collections.singletonList(new AsistenciaModel()));
        when(asistenciaRepository.countTotal(any(AsistenciaFilter.class)))
                .thenReturn(1L);

        PaginationResultModel<AsistenciaModel> result =
                asistenciaService.findAsistencia("123",  "1", "A", 11111111L);

        assertNotNull(result);
        assertEquals(Long.valueOf(1L), result.getTotalElementos());
        assertEquals(Long.valueOf(1L), result.getTotalPaginas());
    }

    @Test(expected = SidepException.class)
    public void findAsistencia_exception() {
        doThrow(new MyBatisSystemException(new Exception()))
                .when(asistenciaRepository).findAll(any(AsistenciaFilter.class));
        asistenciaService.findAsistencia("123", "1", "A", 11111111L);
    }

    @Test
    public void findAsistenciaPorMesAndDia() {
        when(asistenciaRepository.findAll(any(AsistenciaFilter.class)))
                .thenReturn(Collections.emptyList());
        when(asistenciaRepository.countTotal(any(AsistenciaFilter.class)))
                .thenReturn(0L);

        PaginationResultModel<AsistenciaModel> result =
                asistenciaService.findAsistenciaPorMesAndDia("123", "1", "A", "01", "15", 11111111L);

        assertNotNull(result);
        assertEquals(Long.valueOf(0L), result.getTotalElementos());
        assertEquals(Long.valueOf(0L), result.getTotalPaginas());
    }

    @Test(expected = SidepException.class)
    public void findAsistenciaPorMesAndDia_exception() {
        doThrow(new MyBatisSystemException(new Exception()))
                .when(asistenciaRepository).findAll(any(AsistenciaFilter.class));
        asistenciaService.findAsistenciaPorMesAndDia("123", "1", "A", "01", "15", 11111111L);
    }

    @Test
    public void findAllAsistencia() {
        when(asistenciaRepository.findAll(any(AsistenciaFilter.class)))
                .thenReturn(Arrays.asList(new AsistenciaModel(), new AsistenciaModel()));
        when(asistenciaRepository.countTotal(any(AsistenciaFilter.class)))
                .thenReturn(2L);

        PaginationResultModel<AsistenciaModel> result = asistenciaService.findAllAsistencia(
                "2020-01-01", "2020-12-31", "EstX", "RegX", "ProvX", "ComX", 10, 1
        );

        assertNotNull(result);
        assertEquals(Long.valueOf(2L), result.getTotalElementos());
        assertEquals(Long.valueOf(1L), result.getTotalPaginas());
    }

    @Test(expected = SidepException.class)
    public void findAllAsistencia_exception() {
        doThrow(new MyBatisSystemException(new Exception()))
                .when(asistenciaRepository).findAll(any(AsistenciaFilter.class));
        asistenciaService.findAllAsistencia("2020-01-01", "2020-12-31", "EstX", "RegX", "ProvX", "ComX", 10, 1);
    }
}
