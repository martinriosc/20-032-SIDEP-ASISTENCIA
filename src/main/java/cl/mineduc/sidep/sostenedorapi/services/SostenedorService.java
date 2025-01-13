package cl.mineduc.sidep.sostenedorapi.services;

import cl.mineduc.sidep.sostenedorapi.entities.SostenedorEntity;
import cl.mineduc.sidep.sostenedorapi.enums.Order;
import cl.mineduc.sidep.sostenedorapi.model.PaginationResultModel;
import cl.mineduc.sidep.sostenedorapi.model.SostenedorModel;

public interface SostenedorService {

    PaginationResultModel<SostenedorModel> findAll(
            String nombre,
            String rut,
            Long calidadJuridica,
            Long comuna,
            String orderBy,
            Order order,
            Integer page,
            Integer pageSize);

    SostenedorModel findById(Long id);

    SostenedorModel save(SostenedorModel model);

    default SostenedorEntity toEntity(SostenedorModel m ) {
        SostenedorEntity e = new SostenedorEntity();
        e.setDv(m.getDv());
        e.setNombre(m.getNombre());
        e.setComuna(m.getComuna() != null ? m.getComuna().getId() : null);
        e.setCalidadJuridica(m.getCalidadJuridica() != null ? m.getCalidadJuridica().getId(): null);
        e.setRun(m.getRut());
        e.setDireccion(m.getDireccion());
        e.setCodigoAreaTelefono(m.getCodigoAreaTelefono());
        e.setCelular(m.getCelular());
        e.setMail(m.getMail());
        return e;
    }

}
