package cl.mineduc.sidep.asistenciaapi.controllers;

import cl.mineduc.sidep.asistenciaapi.model.AsistenciaIndividualModel;
import cl.mineduc.sidep.asistenciaapi.model.AsistenciaModel;
import cl.mineduc.sidep.asistenciaapi.model.PaginationResultModel;
import cl.mineduc.sidep.asistenciaapi.services.AsistenciaTableService;
import cl.mineduc.sidep.asistenciaapi.services.IAsistenciaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(name = "Asistencia Api", path = "/api-asistencia-parv")
@RequiredArgsConstructor
@Slf4j
public class AsistenciaController {

    private final IAsistenciaService asistenciaService;
    private final AsistenciaTableService asistenciaTableService;

    @PutMapping("/asistencia")
    public ResponseEntity<AsistenciaModel> updateAsistenciaPupiloPorDia(@Valid @RequestBody AsistenciaIndividualModel asistenciaModel, HttpServletRequest request) {
        return ResponseEntity.ok(this.asistenciaService.updateAsistenciaPupiloPorDia(asistenciaModel));
    }

    @PutMapping("/asistencias")
    public ResponseEntity<List<AsistenciaModel>> updateAsistenciaGrupalPupiloPorDia(@Valid @RequestBody List<AsistenciaIndividualModel> asistenciaModel, HttpServletRequest request) {
        return ResponseEntity.ok(this.asistenciaService.updateAsistenciaGrupalPupiloPorDia(asistenciaModel));
    }

    @GetMapping("/asistencia/get-dia-mes")
    public ResponseEntity<PaginationResultModel<AsistenciaModel>> findAsistenciaPorMesAndDia(
            @RequestParam(name = "rbd", required = false) String rbd,
            @RequestParam(name = "grado", required = false) String grado,
            @RequestParam(name = "letra", required = false) String letra,
            @RequestParam(name = "mes", required = false) String mes,
            @RequestParam(name = "dia", required = false) String dia,
            @RequestParam(name = "rut", required = false) Long rut,
            HttpServletRequest httpServletRequest
    ) {
        return ResponseEntity.ok(this.asistenciaService.findAsistenciaPorMesAndDia(
                rbd,  grado, letra, mes, dia, rut));
    }

    @GetMapping("/asistencia/get")
    public ResponseEntity<PaginationResultModel<AsistenciaModel>> findAsistencia(
            @RequestParam(name = "rbd", required = false) String rbd,
            @RequestParam(name = "grado", required = false) String grado,
            @RequestParam(name = "letra", required = false) String letra,
            @RequestParam(name = "rut", required = false) Long rut,
            HttpServletRequest httpServletRequest
    ) {
        return ResponseEntity.ok(this.asistenciaService.findAsistencia(
                rbd, grado, letra, rut));
    }

    @GetMapping("/asistencia/list")
    public ResponseEntity<PaginationResultModel<AsistenciaModel>> findAllAsistencia(
            @RequestParam(name = "periodoDesde", required = false) String periodoDesde,
            @RequestParam(name = "periodoHasta", required = false) String periodoHasta,
            @RequestParam(name = "establecimiento", required = false) String establecimiento,
            @RequestParam(name = "region", required = false) String region,
            @RequestParam(name = "provincia", required = false) String provincia,
            @RequestParam(name = "comuna", required = false) String comuna,
            @RequestParam(name = "pageSize", required = false) Integer pageSize,
            @RequestParam(name = "pageNumber", required = false) Integer pageNumber,
            HttpServletRequest httpServletRequest
    ) {
        return ResponseEntity.ok(this.asistenciaService.findAllAsistencia(
                periodoDesde, periodoHasta, establecimiento, region, provincia, comuna, pageSize, pageNumber));
    }

    @PostMapping("")
    public ResponseEntity<AsistenciaModel> save(@Valid @RequestBody AsistenciaIndividualModel model) {
        return ResponseEntity.ok(this.asistenciaTableService.save(model));
    }

}
