package cl.mineduc.sidep.sostenedorapi.services;

import cl.mineduc.sidep.sostenedorapi.entities.SostenedorEntity;
import cl.mineduc.sidep.sostenedorapi.enums.Order;
import cl.mineduc.sidep.sostenedorapi.exceptions.SostenedorException;
import cl.mineduc.sidep.sostenedorapi.filter.SostenedorFilter;
import cl.mineduc.sidep.sostenedorapi.model.PaginationResultModel;
import cl.mineduc.sidep.sostenedorapi.model.SostenedorModel;
import cl.mineduc.sidep.sostenedorapi.repositories.SostenedorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


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

        List<SostenedorModel> result = this.sostenedorRepository.findAll(f);
        Long total = this.sostenedorRepository.countTotal(f);

        return PaginationResultModel
                .<SostenedorModel>builder()
                .resultados(result)
                .totalElementos(total)
                .totalPaginas(total / result.size() + ((total % result.size() == 0) ? 0 : 1))
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public SostenedorModel findById(Long id) {
        return this.sostenedorRepository.findById(id);
    }

    @Override
    public SostenedorModel save(SostenedorModel m) {

        Integer rut = m.getRut();
        String dv = m.getDv();

        if (!this.validaRut(String.valueOf(rut), dv)) {
            log.error("RUN inválido {} - {}", m.getRut(), m.getDv());
            throw new SostenedorException(String.format("Error al guardar sostenedor, RUN inválido %s-%s", rut, dv));
            // ToDo: send logs to database
        }

        if (this.sostenedorRepository.existsByRut(rut, dv)) {
            log.error("RUN {} - {} existente", rut, dv);
            throw new SostenedorException(String.format("Error al guardar sostenedor, RUN %s-%s ya existe", rut, dv));
            // ToDo: send logs to database
        }

        SostenedorEntity e = this.toEntity(m);
        this.sostenedorRepository.save(e);

        m.setId(e.getId());

        return m;
    }

    @Override
    public SostenedorModel update(SostenedorModel m, Long id) {

        if (this.sostenedorRepository.findById(id) == null) {
            log.error("No es posible actualizar, Sostenedor de ID {} no encontrado", id);
            throw new SostenedorException("No es posible actualizar, Sostenedor de ID " + id);
            // ToDo: send logs to database
        }

        SostenedorEntity e = this.toEntity(m);
        this.sostenedorRepository.update(e, id);

        return m;
    }

    @Override
    public void delete(Long id) {

        Boolean hasUnidad = this.sostenedorRepository.hasUnidadEducativa(id);

        if (hasUnidad != null && hasUnidad) {
            log.error("No es posible eliminar sostenedor de {}, está asociado a una unidad educativa", id);
            throw new SostenedorException(String.format("No es posible eliminar Sostenedor id: %s. Está asociado a una unidad educativa", id));
            // ToDo: send logs to database
        }

        this.sostenedorRepository.delete(id);
    }

}
