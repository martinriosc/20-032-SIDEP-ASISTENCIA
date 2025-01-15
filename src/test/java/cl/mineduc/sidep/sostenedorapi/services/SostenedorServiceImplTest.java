package cl.mineduc.sidep.sostenedorapi.services;

import cl.mineduc.sidep.sostenedorapi.entities.SostenedorEntity;
import cl.mineduc.sidep.sostenedorapi.enums.Order;
import cl.mineduc.sidep.sostenedorapi.exceptions.SostenedorException;
import cl.mineduc.sidep.sostenedorapi.filter.SostenedorFilter;
import cl.mineduc.sidep.sostenedorapi.model.CalidadJuridica;
import cl.mineduc.sidep.sostenedorapi.model.ComunaModel;
import cl.mineduc.sidep.sostenedorapi.model.SostenedorModel;
import cl.mineduc.sidep.sostenedorapi.repositories.SostenedorRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class SostenedorServiceImplTest {

    @InjectMocks
    private SostenedorServiceImpl sostenedorService;

    @Mock
    private SostenedorRepository sostenedorRepository;

    @Test
    public void delete() {
        doNothing()
                .when(sostenedorRepository)
                        .delete(anyLong());
        when(sostenedorRepository.hasUnidadEducativa(anyLong()))
                .thenReturn(false);
        this.sostenedorService.delete(1L);
        verify(sostenedorRepository, times(1)).delete(anyLong());

    }

    @Test(expected = SostenedorException.class)
    public void delete_hasUnidadIsTrue() {
        doNothing().when(sostenedorRepository).delete(anyLong());
        when(sostenedorRepository.hasUnidadEducativa(anyLong())).thenReturn(Boolean.TRUE);

        this.sostenedorService.delete(1L);

    }

    @Test
    public void delete_hasUnidadIsNull() {
        doNothing().when(sostenedorRepository).delete(anyLong());
        when(sostenedorRepository.hasUnidadEducativa(anyLong())).thenReturn(null);

        this.sostenedorService.delete(1L);
        verify(sostenedorRepository, times(1)).delete(anyLong());
    }

    @Test
    public void update() {
        when(this.sostenedorRepository.findById(anyLong()))
                .thenReturn(loadSostenedor());
        doNothing().when(sostenedorRepository).update(any(SostenedorEntity.class), anyLong());

        this.sostenedorService.update(loadSostenedor(), 1L);

        verify(this.sostenedorRepository, times(1)).update(any(SostenedorEntity.class), anyLong());

    }

    @Test(expected = SostenedorException.class)
    public void update_sostenedorNotFound() {
        when(this.sostenedorRepository.findById(anyLong()))
                .thenReturn(null);
        this.sostenedorService.update(loadSostenedor(), 1L);
    }

    @Test
    public void save() {
        SostenedorModel m = loadSostenedor();
        doNothing().when(sostenedorRepository).save(any(SostenedorEntity.class));
        when(sostenedorRepository.existsByRut(anyInt(), anyString()))
                .thenReturn(Boolean.FALSE);

        this.sostenedorService.save(m);

        verify(sostenedorRepository, times(1)).save(any(SostenedorEntity.class));

    }

    @Test(expected = SostenedorException.class)
    public void save_rutNotValid() {
        SostenedorModel s = loadSostenedor();
        s.setDv("8");

        this.sostenedorService.save(s);

    }

    @Test(expected = SostenedorException.class)
    public void save_dvIsBlank() {
        SostenedorModel s = loadSostenedor();
        s.setDv(null);

        this.sostenedorService.save(s);
    }

    @Test(expected = SostenedorException.class)
    public void save_existsBefore() {
        SostenedorModel m = loadSostenedor();
        when(this.sostenedorRepository.existsByRut(anyInt(), anyString()))
                .thenReturn(true);
        this.sostenedorService.save(m);
    }

    @Test
    public void findById() {
        when(this.sostenedorRepository.findById(anyLong()))
                .thenReturn(loadSostenedor());
        assertNotNull(this.sostenedorService.findById(1L));
    }

    @Test
    public void findAll() {
        when(sostenedorRepository.findAll(any(SostenedorFilter.class)))
                .thenReturn(Collections.singletonList(loadSostenedor()));
        when(sostenedorRepository.countTotal(any(SostenedorFilter.class)))
                .thenReturn(1L);

        assertNotNull(this.sostenedorService.findAll(
                "NOMBRE",
                "RUT",
                1L,
                1L,
                "id",
                Order.ASC,
                0,
                10
        ));
    }

    @Test
    public void findAll_orderByIsNull() {
        when(sostenedorRepository.findAll(any(SostenedorFilter.class)))
                .thenReturn(Collections.singletonList(loadSostenedor()));
        when(sostenedorRepository.countTotal(any(SostenedorFilter.class)))
                .thenReturn(1L);

        assertNotNull(this.sostenedorService.findAll(
                "NOMBRE",
                "RUT",
                1L,
                1L,
                null,
                Order.ASC,
                0,
                10
        ));
    }

    @Test
    public void findAll_rutIsNull() {
        when(sostenedorRepository.findAll(any(SostenedorFilter.class)))
                .thenReturn(Collections.singletonList(loadSostenedor()));
        when(sostenedorRepository.countTotal(any(SostenedorFilter.class)))
                .thenReturn(1L);

        assertNotNull(this.sostenedorService.findAll(
                "NOMBRE",
                null,
                1L,
                1L,
                "id",
                Order.ASC,
                0,
                10
        ));
    }

    @Test
    public void findAll_nombreIsNull() {
        when(sostenedorRepository.findAll(any(SostenedorFilter.class)))
                .thenReturn(Collections.singletonList(loadSostenedor()));
        when(sostenedorRepository.countTotal(any(SostenedorFilter.class)))
                .thenReturn(1L);

        assertNotNull(this.sostenedorService.findAll(
                null,
                "RUT",
                1L,
                1L,
                "id",
                Order.ASC,
                0,
                10
        ));
    }

    private static SostenedorModel loadSostenedor() {

        CalidadJuridica c = new CalidadJuridica();
        c.setId(1L);
        c.setNombre("Calidad Juridica Test");

        ComunaModel comuna = new ComunaModel();
        comuna.setId(1L);
        comuna.setNombre("Comuna Test");

        SostenedorModel m = new SostenedorModel();
        m.setId(1L);
        m.setNombre("nombre");
        m.setRut(1);
        m.setDv("9");
        m.setDireccion("Direccion");
        m.setTelefono("telefono");
        m.setCodigoAreaTelefono("codigoAreaTelefono");
        m.setCelular("celular");
        m.setCalidadJuridica(c);
        m.setComuna(comuna);
        m.setMail("test@test.cl");

        m.setFechaCreacion(LocalDateTime.now());

        return m;
    }



}