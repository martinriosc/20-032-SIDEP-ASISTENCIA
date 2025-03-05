package cl.mineduc.sidep.asistenciaapi.controllers;

import cl.mineduc.sidep.asistenciaapi.exceptions.SidepException;
import cl.mineduc.sidep.asistenciaapi.model.AsistenciaIndividualModel;
import cl.mineduc.sidep.asistenciaapi.model.AsistenciaModel;
import cl.mineduc.sidep.asistenciaapi.model.PaginationResultModel;
import cl.mineduc.sidep.asistenciaapi.services.AsistenciaTableService;
import cl.mineduc.sidep.asistenciaapi.services.IAsistenciaService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class AsistenciaControllerTest {

    @Mock
    private IAsistenciaService asistenciaService;

    @Mock
    private AsistenciaTableService asistenciaTableService;

    @InjectMocks
    private AsistenciaController asistenciaController;

    private HttpServletRequest request;

    @Before
    public void setUp() {
        this.request = mock(HttpServletRequest.class);
        when(request.getMethod()).thenReturn("PUT");
        when(request.getRequestURI()).thenReturn("URI");
    }

    @Test
    public void updateAsistenciaPupiloPorDia() {
        AsistenciaIndividualModel input = new AsistenciaIndividualModel();
        input.setRut(12345678);
        AsistenciaModel expected = new AsistenciaModel();
        expected.setRut(12345678L);

        when(asistenciaService.updateAsistenciaPupiloPorDia(any())).thenReturn(expected);

        ResponseEntity<AsistenciaModel> response = asistenciaController.updateAsistenciaPupiloPorDia(input, request);

        assertNotNull(response);
        assertNotNull(response.getBody());
        verify(asistenciaService, times(1)).updateAsistenciaPupiloPorDia(any());
    }

    @Test(expected = SidepException.class)
    public void updateAsistenciaPupiloPorDia_exception() {
        AsistenciaIndividualModel input = new AsistenciaIndividualModel();
        input.setRut(12345678);
        when(asistenciaService.updateAsistenciaPupiloPorDia(any()))
                .thenThrow(new SidepException("Error", null));
        asistenciaController.updateAsistenciaPupiloPorDia(input, request);
    }

    @Test
    public void updateAsistenciaGrupalPupiloPorDia() {
        AsistenciaIndividualModel a1 = new AsistenciaIndividualModel();
        a1.setRut(11111111);
        AsistenciaIndividualModel a2 = new AsistenciaIndividualModel();
        a2.setRut(22222222);

        AsistenciaModel expected = new AsistenciaModel();
        expected.setPresente(true);

        when(asistenciaService.updateAsistenciaGrupalPupiloPorDia(anyList()))
                .thenReturn(Arrays.asList(expected));

        ResponseEntity<List<AsistenciaModel>> response = asistenciaController.updateAsistenciaGrupalPupiloPorDia(
                Arrays.asList(a1, a2), request
        );

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
        verify(asistenciaService, times(1)).updateAsistenciaGrupalPupiloPorDia(anyList());
    }

    @Test(expected = SidepException.class)
    public void updateAsistenciaGrupalPupiloPorDia_exception() {
        when(asistenciaService.updateAsistenciaGrupalPupiloPorDia(anyList()))
                .thenThrow(new SidepException("Error", null));
        asistenciaController.updateAsistenciaGrupalPupiloPorDia(Collections.singletonList(new AsistenciaIndividualModel()), request);
    }

    @Test
    public void findAsistenciaPorMesAndDia() {
        when(asistenciaService.findAsistenciaPorMesAndDia(
                any(), any(), any(), any(), any(), anyLong()
        )).thenReturn(PaginationResultModel.<AsistenciaModel>builder().build());

        ResponseEntity<PaginationResultModel<AsistenciaModel>> response =
                asistenciaController.findAsistenciaPorMesAndDia(
                        null, null, null, null, null, 12345678L, request
                );

        assertNotNull(response);
        assertNotNull(response.getBody());
        verify(asistenciaService, times(1)).findAsistenciaPorMesAndDia(
                any(), any(), any(), any(), any(), anyLong()
        );
    }

    @Test(expected = SidepException.class)
    public void findAsistenciaPorMesAndDia_exception() {
        when(asistenciaService.findAsistenciaPorMesAndDia(any(), any(), any(), any(), any(), anyLong()))
                .thenThrow(new SidepException("Error", null));
        asistenciaController.findAsistenciaPorMesAndDia(null, null, null, null, null, 12345678L, request);
    }

    @Test
    public void findAsistencia() {
        when(asistenciaService.findAsistencia(
                any(), any(), any(), anyLong()
        )).thenReturn(PaginationResultModel.<AsistenciaModel>builder().build());

        ResponseEntity<PaginationResultModel<AsistenciaModel>> response =
                asistenciaController.findAsistencia(null, null, null, null, 12345678L, request);

        assertNotNull(response);
        assertNotNull(response.getBody());
        verify(asistenciaService, times(1)).findAsistencia(
                any(), any(), any(), anyLong()
        );
    }

    @Test(expected = SidepException.class)
    public void findAsistencia_exception() {
        when(asistenciaService.findAsistencia(any(), any(), any(), anyLong()))
                .thenThrow(new SidepException("Error", null));
        asistenciaController.findAsistencia(null, null, null, null, 12345678L, request);
    }

    @Test
    public void findAllAsistencia() {
        when(asistenciaService.findAllAsistencia(
                anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyInt(), anyInt()
        )).thenReturn(PaginationResultModel.<AsistenciaModel>builder()
                .resultados(Collections.singletonList(new AsistenciaModel()))
                .build());

        ResponseEntity<PaginationResultModel<AsistenciaModel>> response =
                asistenciaController.findAllAsistencia("2020-01-01", "2020-12-31",
                        "EstablecimientoX", "RegionX", "ProvinciaX", "ComunaX",
                        20, 0, request);

        assertNotNull(response);
        assertNotNull(response.getBody());
        verify(asistenciaService, times(1)).findAllAsistencia(
                anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyInt(), anyInt()
        );
    }

    @Test(expected = SidepException.class)
    public void findAllAsistencia_exception() {
        when(asistenciaService.findAllAsistencia(anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyInt(), anyInt()))
                .thenThrow(new SidepException("Error", null));
        asistenciaController.findAllAsistencia("2020-01-01", "2020-12-31",
                "EstablecimientoX", "RegionX", "ProvinciaX", "ComunaX",
                20, 0, request);
    }

    @Test
    public void save_ok() {
        AsistenciaIndividualModel input = new AsistenciaIndividualModel();
        input.setRut(12345678);
        AsistenciaModel expected = new AsistenciaModel();
        expected.setRut(12345678L);

        when(asistenciaTableService.save(any(AsistenciaIndividualModel.class)))
                .thenReturn(expected);

        ResponseEntity<AsistenciaModel> response = asistenciaController.save(input);

        assertNotNull(response);
        assertNotNull(response.getBody());
        verify(asistenciaTableService, times(1)).save(any(AsistenciaIndividualModel.class));
    }
}
