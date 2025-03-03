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
    public ResponseEntity<AsistenciaModel> updateAsistenciaGrupalPupiloPorDia(@Valid @RequestBody List<AsistenciaIndividualModel> asistenciaModel, HttpServletRequest request) {
        return ResponseEntity.ok(this.asistenciaService.updateAsistenciaGrupalPupiloPorDia(asistenciaModel));
    }

    @GetMapping("/asistencia/{rbd}/ensenanza/{ensenanza}/grado/{grado}/letra/{letra}/mes/{mes}/dia/{dia}/rut/{rut}")
    public ResponseEntity<PaginationResultModel<AsistenciaModel>> findAsistenciaPorMesAndDia(
            @PathVariable(name = "rbd", required = false) String rbd,
            @PathVariable(name = "ensenanza", required = false) String ensenanza,
            @PathVariable(name = "grado", required = false) String grado,
            @PathVariable(name = "letra", required = false) String letra,
            @PathVariable(name = "mes", required = false) String mes,
            @PathVariable(name = "dia", required = false) String dia,
            @PathVariable(name = "rut") Integer rut,
            HttpServletRequest httpServletRequest
    ) {
        return ResponseEntity.ok(this.asistenciaService.findAsistenciaPorMesAndDia(
                rbd, ensenanza, grado, letra, mes, dia, rut));
    }

    @GetMapping("/asistencia/{rbd}/ensenanza/{ensenanza}/grado/{grado}/letra/{letra}/rut/{rut}")
    public ResponseEntity<PaginationResultModel<AsistenciaModel>> findAsistencia(
            @PathVariable(name = "rbd", required = false) String rbd,
            @PathVariable(name = "ensenanza", required = false) String ensenanza,
            @PathVariable(name = "grado", required = false) String grado,
            @PathVariable(name = "letra", required = false) String letra,
            @PathVariable(name = "rut") Integer rut,
            HttpServletRequest httpServletRequest
    ) {
        return ResponseEntity.ok(this.asistenciaService.findAsistencia(
                rbd, ensenanza, grado, letra, rut));
    }

    @GetMapping("/asistencia")
    public ResponseEntity<PaginationResultModel<AsistenciaModel>> findAllAsistencia(
            @PathVariable(name = "periodoDesde", required = false) String periodoDesde,
            @PathVariable(name = "periodoHasta", required = false) String periodoHasta,
            @PathVariable(name = "establecimiento", required = false) String establecimiento,
            @PathVariable(name = "region", required = false) String region,
            @PathVariable(name = "provincia", required = false) String provincia,
            @PathVariable(name = "comuna", required = false) String comuna,
            @PathVariable(name = "pageSize", required = false) Integer pageSize,
            @PathVariable(name = "pageNumber", required = false) Integer pageNumber,
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
