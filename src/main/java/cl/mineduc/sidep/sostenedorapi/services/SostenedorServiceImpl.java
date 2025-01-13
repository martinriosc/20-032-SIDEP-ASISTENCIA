package cl.mineduc.sidep.sostenedorapi.services;

import cl.mineduc.sidep.sostenedorapi.entities.SostenedorEntity;
import cl.mineduc.sidep.sostenedorapi.enums.Order;
import cl.mineduc.sidep.sostenedorapi.filter.SostenedorFilter;
import cl.mineduc.sidep.sostenedorapi.model.SostenedorModel;
import cl.mineduc.sidep.sostenedorapi.repositories.SostenedorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class SostenedorServiceImpl implements SostenedorService {

    private static final int PAGE_SIZE = 20;

    private final SostenedorRepository sostenedorRepository;

    @Override
    public List<SostenedorModel> findAll(String nombre, String rut, Long calidadJuridica, Long comunda, String orderBy, Order order, Integer page, Integer pageSize) {

        SostenedorFilter f = SostenedorFilter
                .builder()
                .nombre(nombre)
                .rut(rut)
                .calidadJuridiad(calidadJuridica)
                .comuna(comunda)
                .build();

        if (page != null) {
            int size = pageSize != null ? pageSize : PAGE_SIZE;
            f.setOrder(order != null ? order.name() : Order.ASC.name());
            f.setOrderBy(orderBy);
            f.setLimit(size);
            f.setOffset(page * size);
        }
        return this.sostenedorRepository.findAll(f);
    }

    @Override
    public SostenedorModel findById(Long id) {
        return null;
    }

    @Override
    public SostenedorModel save(SostenedorEntity e) {
        return null;
    }
}
