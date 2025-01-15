package cl.mineduc.sidep.sostenedorapi.services;

import cl.mineduc.sidep.sostenedorapi.entities.SostenedorEntity;
import cl.mineduc.sidep.sostenedorapi.enums.Order;
import cl.mineduc.sidep.sostenedorapi.model.PaginationResultModel;
import cl.mineduc.sidep.sostenedorapi.model.SostenedorModel;
import org.apache.commons.lang3.StringUtils;

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

    SostenedorModel update(SostenedorModel model, Long id);

    void delete(Long id);

    default SostenedorEntity toEntity(SostenedorModel m) {
        SostenedorEntity e = new SostenedorEntity();
        e.setId(m.getId());
        e.setDv(m.getDv());
        e.setNombre(m.getNombre());
        e.setComuna(m.getComuna() != null ? m.getComuna().getId() : null);
        e.setCalidadJuridica(m.getCalidadJuridica() != null ? m.getCalidadJuridica().getId() : null);
        e.setRun(m.getRut());
        e.setDireccion(m.getDireccion());
        e.setCodigoAreaTelefono(m.getCodigoAreaTelefono());
        e.setCelular(m.getCelular());
        e.setMail(m.getMail());
        return e;
    }

    default boolean validaRut(String rut, String dv) {
        int intRut = Integer.parseInt(rut.replace("\\.", ""));

        if (StringUtils.isBlank(dv)) {
            return false;
        }

        char dvChar = dv.charAt(0);
        int m = 0;
        int s = 1;
        for (; intRut != 0; intRut /= 10) {
            s = (s + intRut % 10 * (9 - m++ % 6)) % 11;
        }

        return (dvChar == (char) (s != 0 ? s + 47 : 75));
    }

}
