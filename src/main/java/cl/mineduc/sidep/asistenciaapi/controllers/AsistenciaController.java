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

    @GetMapping("/asistencia/rbd/{rbd}/grado/{grado}/letra/{letra}/mes/{mes}/dia/{dia}/rut/{rut}")
    public ResponseEntity<PaginationResultModel<AsistenciaModel>> findAsistenciaPorMesAndDia(
            @PathVariable("rbd") String rbd,
            @PathVariable("grado") String grado,
            @PathVariable("letra") String letra,
            @PathVariable("mes") String mes,
            @PathVariable("dia") String dia,
            @PathVariable("rut") Long rut) {
        PaginationResultModel<AsistenciaModel> result = asistenciaService.findAsistenciaPorMesAndDia(rbd, grado, letra, mes, dia, rut);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/asistencia/rbd/{rbd}/grado/{grado}/letra/{letra}/rut/{rut}")
    public ResponseEntity<PaginationResultModel<AsistenciaModel>> findAsistencia(
            @PathVariable("rbd") String rbd,
            @PathVariable("grado") String grado,
            @PathVariable("letra") String letra,
            @PathVariable("rut") Long rut) {
        PaginationResultModel<AsistenciaModel> result = asistenciaService.findAsistencia(rbd, grado, letra, rut);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/asistencia")
    public ResponseEntity<PaginationResultModel<AsistenciaModel>> findAllAsistencia(
            @RequestParam(name ="periodoDesde", required = false) String periodoDesde,
            @RequestParam(name ="periodoHasta", required = false) String periodoHasta,
            @RequestParam(name ="rbd", required = false) String rbd,
            @RequestParam(name ="region", required = false) String region,
            @RequestParam(name ="provincia", required = false) String provincia,
            @RequestParam(name ="comuna", required = false) String comuna,
            @RequestParam("pageSize") Integer pageSize,
            @RequestParam("pageNumber") Integer pageNumber) {
        PaginationResultModel<AsistenciaModel> result = asistenciaService.findAllAsistencia(
                periodoDesde, periodoHasta, rbd, region, provincia, comuna, pageSize, pageNumber);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/asistencia")
    public ResponseEntity<AsistenciaModel> save(@Valid @RequestBody AsistenciaIndividualModel model) {
        AsistenciaModel result = asistenciaTableService.save(model);
        return ResponseEntity.ok(result);
    }

}
