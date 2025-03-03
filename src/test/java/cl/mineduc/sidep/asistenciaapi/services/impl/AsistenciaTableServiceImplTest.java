package cl.mineduc.sidep.asistenciaapi.services.impl;

import cl.mineduc.sidep.asistenciaapi.exceptions.SostenedorException;
import cl.mineduc.sidep.asistenciaapi.model.AsistenciaIndividualModel;
import cl.mineduc.sidep.asistenciaapi.repositories.AsistenciaTableRepository;
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
        when(asistenciaTableRepository.findGrupoByRbdLetraNivelJornada(
                input.getGrado(),
                input.getLetra(),
                input.getJornada().getId(),
                input.getRbd()))
                .thenReturn(10L);
        when(asistenciaTableRepository.findCalendarioByGrupo(10L))
                .thenReturn(20L);
        when(asistenciaTableRepository.findUnidadEducativaByRbd(input.getRbd()))
                .thenReturn(30L);
        when(asistenciaTableRepository.findByRut(input.getRut()))
                .thenReturn(40L);
        when(asistenciaTableRepository.findMatriculaUnidadEducativa(40L, 30L))
                .thenReturn(50L);
        when(asistenciaTableRepository.findMatriculaGrupo(10L, 50L))
                .thenReturn(60L);
        when(asistenciaTableRepository.validarGrupoTieneDocenteAsistente(anyLong()))
                .thenReturn(true);
        when(asistenciaTableRepository.validarFechaCalendarioHabil(20L))
                .thenReturn(true);

        doNothing().when(asistenciaTableRepository).save(any());

        service.save(input);

        verify(asistenciaTableRepository, times(1)).save(any());
    }

    @Test(expected = SostenedorException.class)
    public void save_GrupoNoEncontrado() {
        AsistenciaIndividualModel input = loadAsistencia();
        when(asistenciaTableRepository.findGrupoByRbdLetraNivelJornada(
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
        when(asistenciaTableRepository.findGrupoByRbdLetraNivelJornada(
                input.getGrado(),
                input.getLetra(),
                input.getJornada().getId(),
                input.getRbd()))
                .thenReturn(10L);
        when(asistenciaTableRepository.findCalendarioByGrupo(10L))
                .thenReturn(20L);
        when(asistenciaTableRepository.findUnidadEducativaByRbd(input.getRbd()))
                .thenReturn(30L);
        when(asistenciaTableRepository.findByRut(input.getRut()))
                .thenReturn(40L);
        when(asistenciaTableRepository.findMatriculaUnidadEducativa(40L, 30L))
                .thenReturn(50L);
        when(asistenciaTableRepository.findMatriculaGrupo(10L, 50L))
                .thenReturn(60L);
        when(asistenciaTableRepository.validarGrupoTieneDocenteAsistente(60L))
                .thenReturn(false);
        service.save(input);
    }

    @Test(expected = SostenedorException.class)
    public void save_FechaNoHabilitada() {
        AsistenciaIndividualModel input = loadAsistencia();
        when(asistenciaTableRepository.findGrupoByRbdLetraNivelJornada(
                input.getGrado(),
                input.getLetra(),
                input.getJornada().getId(),
                input.getRbd()))
                .thenReturn(10L);
        when(asistenciaTableRepository.findCalendarioByGrupo(10L))
                .thenReturn(20L);
        when(asistenciaTableRepository.findUnidadEducativaByRbd(input.getRbd()))
                .thenReturn(30L);
        when(asistenciaTableRepository.findByRut(input.getRut()))
                .thenReturn(40L);
        when(asistenciaTableRepository.findMatriculaUnidadEducativa(40L, 30L))
                .thenReturn(50L);
        when(asistenciaTableRepository.findMatriculaGrupo(10L, 50L))
                .thenReturn(60L);
        when(asistenciaTableRepository.validarGrupoTieneDocenteAsistente(60L))
                .thenReturn(true);
        when(asistenciaTableRepository.validarFechaCalendarioHabil(20L))
                .thenReturn(false);
        service.save(input);
    }

}