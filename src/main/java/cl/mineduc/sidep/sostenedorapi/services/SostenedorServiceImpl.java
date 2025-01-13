package cl.mineduc.sidep.sostenedorapi.services;

import cl.mineduc.sidep.sostenedorapi.entities.SostenedorEntity;
import cl.mineduc.sidep.sostenedorapi.enums.Order;
import cl.mineduc.sidep.sostenedorapi.filter.SostenedorFilter;
import cl.mineduc.sidep.sostenedorapi.model.PaginationResultModel;
import cl.mineduc.sidep.sostenedorapi.model.SostenedorModel;
import cl.mineduc.sidep.sostenedorapi.repositories.SostenedorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Slf4j
public class SostenedorServiceImpl implements SostenedorService {

    private static final int PAGE_SIZE = 20;

    private final SostenedorRepository sostenedorRepository;

    @Transactional(readOnly = true)
    @Override
    public PaginationResultModel<SostenedorModel> findAll(String nombre, String rut, Long calidadJuridica, Long comuna, String orderBy, Order order, Integer page, Integer pageSize) {

        if (StringUtils.isBlank(orderBy)) {
            orderBy = null;
        }

        if (StringUtils.isBlank(rut)) {
            rut = null;
        }

        if (StringUtils.isBlank(nombre)) {
            nombre = null;
        }

        SostenedorFilter f = SostenedorFilter
                .builder()
                .nombre(nombre)
                .rut(rut)
                .calidadJuridica(calidadJuridica)
                .comuna(comuna)
                .build();

        if (page != null) {
            int size = pageSize != null ? pageSize : PAGE_SIZE;
            f.setOrder(order != null ? order.name() : Order.ASC.name());
            f.setOrderBy(orderBy);
            f.setLimit(size);
            f.setOffset(page * size);
        }

        return PaginationResultModel
                .<SostenedorModel>builder()
                .resultados(this.sostenedorRepository.findAll(f))
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public SostenedorModel findById(Long id) {
        return this.sostenedorRepository.findById(id);
    }

    @Transactional
    @Override
    public SostenedorModel save(SostenedorModel m) {
        SostenedorEntity e = this.toEntity(m);
        this.sostenedorRepository.save(e);

        m.setId(e.getId());

        return m;
    }
}
