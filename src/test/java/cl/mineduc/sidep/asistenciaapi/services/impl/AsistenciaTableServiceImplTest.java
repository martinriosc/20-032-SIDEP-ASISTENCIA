package cl.mineduc.sidep.asistenciaapi.services.impl;

import cl.mineduc.sidep.asistenciaapi.entities.AsistenciaEntity;
import cl.mineduc.sidep.asistenciaapi.exceptions.SostenedorException;
import cl.mineduc.sidep.asistenciaapi.model.AsistenciaIndividualModel;
import cl.mineduc.sidep.asistenciaapi.model.AsistenciaModel;
import cl.mineduc.sidep.asistenciaapi.model.CalendarioModel;
import cl.mineduc.sidep.asistenciaapi.repositories.AsistenciaRepository;
import cl.mineduc.sidep.asistenciaapi.repositories.AsistenciaTableRepository;
import cl.mineduc.sidep.asistenciaapi.repositories.CalendarioRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AsistenciaTableServiceImplTest {

    @Mock
    private AsistenciaTableRepository asistenciaTableRepository;

    @Mock
    private AsistenciaRepository asistenciaRepository;

    @Mock
    private CalendarioRepository calendarioRepository;

    @InjectMocks
    private AsistenciaTableServiceImpl service;

    private static AsistenciaIndividualModel loadAsistencia() {
        AsistenciaIndividualModel asistencia = new AsistenciaIndividualModel();
        asistencia.setId(1L);
        asistencia.setRbd(1111);
        asistencia.setGrado(1L);
        asistencia.setLetra("A");
        asistencia.setMes("03");
        asistencia.setDia("15");
        asistencia.setRut(12345678);
        asistencia.setPresente(true);
        asistencia.setCalendarioId(10L);
        asistencia.setJornada(cl.mineduc.sidep.asistenciaapi.enums.TipoJornada.MANANA);
        return asistencia;
    }
    @Test
    public void save_ok() {
        AsistenciaIndividualModel input = loadAsistencia();

        lenient().when(calendarioRepository.findByDiaMesAnio(28, 2, 2025, 2L))
                .thenReturn(new CalendarioModel() {{
                    setTrabajado(true);
                }});

        lenient().when(asistenciaTableRepository.findGrupoByRbdLetraNivelJornada(
                input.getGrado(),
                input.getLetra(),
                input.getJornada().getId(),
                input.getRbd()))
                .thenReturn(10L);
        lenient().when(asistenciaTableRepository.findCalendarioByGrupo(10L))
                .thenReturn(20L);
        lenient().when(asistenciaTableRepository.findUnidadEducativaByRbd(input.getRbd()))
                .thenReturn(30L);
        lenient().when(asistenciaTableRepository.findByRut(input.getRut()))
                .thenReturn(40L);
        lenient().when(asistenciaTableRepository.findMatriculaUnidadEducativa(40L, 30L))
                .thenReturn(50L);
        lenient().when(asistenciaTableRepository.findMatriculaGrupo(10L, 50L))
                .thenReturn(60L);
        lenient().when(asistenciaTableRepository.validarGrupoTieneDocenteAsistente(anyLong()))
                .thenReturn(true);
        lenient().when(asistenciaTableRepository.validarFechaCalendarioHabil(20L))
                .thenReturn(true);

        doAnswer(invocation -> {
            AsistenciaEntity e = invocation.getArgument(0);
            e.setId(99L);
            return null;
        }).when(asistenciaTableRepository).save(any(AsistenciaEntity.class));

        AsistenciaModel mockedDbRecord = new AsistenciaModel();
        mockedDbRecord.setId(99L);
        mockedDbRecord.setRut(12345678L);
        mockedDbRecord.setPresente(true);
        lenient().when(asistenciaRepository.findById(eq(99L))).thenReturn(mockedDbRecord);

        service.save(input);

        verify(asistenciaTableRepository, times(1)).save(any(AsistenciaEntity.class));
    }




    @Test(expected = SostenedorException.class)
    public void save_GrupoNoEncontrado() {
        AsistenciaIndividualModel input = loadAsistencia();
        lenient().when(asistenciaTableRepository.findGrupoByRbdLetraNivelJornada(
                input.getGrado(),
                input.getLetra(),
                input.getJornada().getId(),
                input.getRbd()))
                .thenReturn(null);
        service.save(input);
    }

    @Test(expected = SostenedorException.class)
    public void save_GrupoSinDocente() {
        AsistenciaIndividualModel input = loadAsistencia();
        lenient().when(asistenciaTableRepository.findGrupoByRbdLetraNivelJornada(
                input.getGrado(),
                input.getLetra(),
                input.getJornada().getId(),
                input.getRbd()))
                .thenReturn(10L);
        lenient().when(asistenciaTableRepository.findCalendarioByGrupo(10L))
                .thenReturn(20L);
        lenient().when(asistenciaTableRepository.findUnidadEducativaByRbd(input.getRbd()))
                .thenReturn(30L);
        lenient().when(asistenciaTableRepository.findByRut(input.getRut()))
                .thenReturn(40L);
        lenient().when(asistenciaTableRepository.findMatriculaUnidadEducativa(40L, 30L))
                .thenReturn(50L);
        lenient().when(asistenciaTableRepository.findMatriculaGrupo(10L, 50L))
                .thenReturn(60L);
        lenient().when(asistenciaTableRepository.validarGrupoTieneDocenteAsistente(60L))
                .thenReturn(false);
        service.save(input);
    }

    @Test(expected = SostenedorException.class)
    public void save_FechaNoHabilitada() {
        AsistenciaIndividualModel input = loadAsistencia();
        lenient().when(asistenciaTableRepository.findGrupoByRbdLetraNivelJornada(
                input.getGrado(),
                input.getLetra(),
                input.getJornada().getId(),
                input.getRbd()))
                .thenReturn(10L);
        lenient().when(asistenciaTableRepository.findCalendarioByGrupo(10L))
                .thenReturn(20L);
        lenient().when(asistenciaTableRepository.findUnidadEducativaByRbd(input.getRbd()))
                .thenReturn(30L);
        lenient().when(asistenciaTableRepository.findByRut(input.getRut()))
                .thenReturn(40L);
        lenient().when(asistenciaTableRepository.findMatriculaUnidadEducativa(40L, 30L))
                .thenReturn(50L);
        lenient().when(asistenciaTableRepository.findMatriculaGrupo(10L, 50L))
                .thenReturn(60L);
        lenient().when(asistenciaTableRepository.validarGrupoTieneDocenteAsistente(60L))
                .thenReturn(true);
        lenient().when(asistenciaTableRepository.validarFechaCalendarioHabil(20L))
                .thenReturn(false);
        service.save(input);
    }

}