package cl.mineduc.sidep.sostenedorapi.services;

import cl.mineduc.sidep.sostenedorapi.entities.SostenedorEntity;
import cl.mineduc.sidep.sostenedorapi.enums.Order;
import cl.mineduc.sidep.sostenedorapi.model.SostenedorModel;

import java.util.List;

public interface SostenedorService {

    List<SostenedorModel> findAll(String nombre, String rut, Long calidadJuridica, Long comunda, String orderBy, Order order, Integer page, Integer pageSize);

    SostenedorModel findById(Long id);

    SostenedorModel save(SostenedorEntity e);

}
