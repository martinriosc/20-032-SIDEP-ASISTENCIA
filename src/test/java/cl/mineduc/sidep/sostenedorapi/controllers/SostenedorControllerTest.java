package cl.mineduc.sidep.sostenedorapi.controllers;

import cl.mineduc.sidep.sostenedorapi.model.PaginationResultModel;
import cl.mineduc.sidep.sostenedorapi.model.SostenedorModel;
import cl.mineduc.sidep.sostenedorapi.services.ProcesoService;
import cl.mineduc.sidep.sostenedorapi.services.SostenedorService;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class SostenedorControllerTest {

    @Mock
    private SostenedorService sostenedorService;

    @Mock
    private ProcesoService procesoService;

    @InjectMocks
    private SostenedorController sostenedorController;

    private HttpServletRequest request;

    @Before
    public void beforeClass() throws Exception {
        this.request = mock(HttpServletRequest.class);
        when(request.getMethod()).thenReturn("POST");
        when(request.getRequestURI()).thenReturn("URI");
    }

    @Test
    public void findAll() {
        when(sostenedorService.findAll(any(), anyString(), anyLong(), anyLong(), anyString(), any(), anyInt(), anyInt()))
                .thenReturn(PaginationResultModel.<SostenedorModel>builder().build());
        assertNotNull(this.sostenedorController.findAll(null, null, null, null, null, null, 1, 10, request));
    }

    @Test
    public void findById() {
        when(sostenedorService.findById(anyLong())).thenReturn(new SostenedorModel());
        assertNotNull(this.sostenedorController.findById(1L));
    }

    @Test
    public void save() {
        when(sostenedorService.save(any())).thenReturn(new SostenedorModel());
        doNothing().when(procesoService).save(any());

        assertNotNull(this.sostenedorController.save(new SostenedorModel(), request));

    }

    @Test
    public void update() {
        when(sostenedorService.update(any(), anyLong())).thenReturn(new SostenedorModel());
        doNothing().when(procesoService).save(any());
        assertNotNull(this.sostenedorController.update(new SostenedorModel(), 1L, request));
    }

    @Test
    public void delete() {
        doNothing().when(sostenedorService).delete(anyLong());
        doNothing().when(procesoService).save(any());

        this.sostenedorController.delete(1L, request);

        verify(sostenedorService, times(1)).delete(anyLong());
        verify(procesoService, times(1)).save(any());

    }
}