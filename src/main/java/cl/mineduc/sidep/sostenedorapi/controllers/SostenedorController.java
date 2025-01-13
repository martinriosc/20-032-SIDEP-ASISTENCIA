package cl.mineduc.sidep.sostenedorapi.controllers;

import cl.mineduc.sidep.sostenedorapi.enums.Order;
import cl.mineduc.sidep.sostenedorapi.model.PaginationResultModel;
import cl.mineduc.sidep.sostenedorapi.model.SostenedorModel;
import cl.mineduc.sidep.sostenedorapi.services.SostenedorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(name = "Sostenedor Api", path = "/sostenedor")
@RequiredArgsConstructor
@Slf4j
public class SostenedorController {

    private final SostenedorService sostenedorService;

    @GetMapping("")
    public ResponseEntity<PaginationResultModel<SostenedorModel>> findAll(
            @RequestParam(name = "nombre", required = false) String nombre,
            @RequestParam(name = "rut", required = false) String rut,
            @RequestParam(name = "calidadJuridica", required = false) Long calidadJuridica,
            @RequestParam(name = "comuna", required = false) Long comuna,
            @RequestParam(name = "orderBy", required = false) String orderBy,
            @RequestParam(name = "order", required = false) Order order,
            @RequestParam(name = "page") Integer page,
            @RequestParam(name = "pageSize") Integer pageSize
    ) {
        return ResponseEntity.ok(this.sostenedorService.findAll(
                nombre, rut, calidadJuridica, comuna, orderBy, order, page, pageSize));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SostenedorModel> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.sostenedorService.findById(id));
    }

    @PostMapping("")
    public ResponseEntity<SostenedorModel> save(@Valid @RequestBody SostenedorModel sostenedorModel) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.sostenedorService.save(sostenedorModel));
    }


}
