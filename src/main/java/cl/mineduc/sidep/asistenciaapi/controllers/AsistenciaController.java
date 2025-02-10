package cl.mineduc.sidep.asistenciaapi.controllers;

import cl.mineduc.sidep.asistenciaapi.model.AsistenciaIndividualModel;
import cl.mineduc.sidep.asistenciaapi.model.AsistenciaModel;
import cl.mineduc.sidep.asistenciaapi.model.PaginationResultModel;
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
            @RequestParam(name = "rbd", required = false) String rbd,
            @RequestParam(name = "ensenanza", required = false) String ensenanza,
            @RequestParam(name = "grado", required = false) String grado,
            @RequestParam(name = "letra", required = false) String letra,
            @RequestParam(name = "mes", required = false) String mes,
            @RequestParam(name = "dia", required = false) String dia,
            @RequestParam(name = "rut") Integer rut,
            HttpServletRequest httpServletRequest
    ) {
        return ResponseEntity.ok(this.asistenciaService.findAsistenciaPorMesAndDia(
                rbd, ensenanza, grado, letra, mes, dia, rut));
    }

    @GetMapping("/asistencia/{rbd}/ensenanza/{ensenanza}/grado/{grado}/letra/{letra}/rut/{rut}")
    public ResponseEntity<PaginationResultModel<AsistenciaModel>> findAsistencia(
            @RequestParam(name = "rbd", required = false) String rbd,
            @RequestParam(name = "ensenanza", required = false) String ensenanza,
            @RequestParam(name = "grado", required = false) String grado,
            @RequestParam(name = "letra", required = false) String letra,
            @RequestParam(name = "rut") Integer rut,
            HttpServletRequest httpServletRequest
    ) {
        return ResponseEntity.ok(this.asistenciaService.findAsistencia(
                rbd, ensenanza, grado, letra, rut));
    }

    @GetMapping("/asistencia")
    public ResponseEntity<PaginationResultModel<AsistenciaModel>> findAllAsistencia(
            @RequestParam(name = "periodoDesde", required = false) String periodoDesde,
            @RequestParam(name = "periodoHasta", required = false) String periodoHasta,
            @RequestParam(name = "establecimiento", required = false) String establecimiento,
            @RequestParam(name = "region", required = false) String region,
            @RequestParam(name = "provincia", required = false) String provincia,
            @RequestParam(name = "comuna", required = false) String comuna,
            @RequestParam(name = "pageSize") Integer pageSize,
            @RequestParam(name = "pageNumber") Integer pageNumber,
            HttpServletRequest httpServletRequest
    ) {
        return ResponseEntity.ok(this.asistenciaService.findAllAsistencia(
                periodoDesde, periodoHasta, establecimiento, region, provincia, comuna, pageSize, pageNumber));
    }


}
