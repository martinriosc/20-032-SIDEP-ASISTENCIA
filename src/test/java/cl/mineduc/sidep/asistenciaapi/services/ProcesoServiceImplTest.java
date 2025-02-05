package cl.mineduc.sidep.asistenciaapi.services;

import cl.mineduc.sidep.asistenciaapi.entities.ProcesoEntity;
import cl.mineduc.sidep.asistenciaapi.repositories.ProcesoRepository;
import cl.mineduc.sidep.asistenciaapi.utils.ProcesoUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class ProcesoServiceImplTest {

    @InjectMocks
    private ProcesoServiceImpl procesoService;

    @Mock
    private ProcesoRepository procesoRepository;


    @Test
    public void save() {

        ProcesoEntity procesoEntity = ProcesoUtils
                .getProcesoEntity(200, ProcesoUtils.getOperacion("POST", "/ENDPOINT"), "mensaje");
        doNothing().when(procesoRepository).save(any());
        this.procesoService.save(procesoEntity);

        verify(procesoRepository, times(1)).save(any());
    }
}