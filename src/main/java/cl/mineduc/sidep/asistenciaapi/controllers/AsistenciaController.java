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

    @GetMapping("/asistencia/peridoDesde/{periodoDesde}/periodoHasta/{periodoHasta}/establecimiento/{establecimiento}/region/{region}/provincia/{provincia}/comuna/{comuna}/pageSize/{pageSize}/pageNumber/{pageNumber}")
    public ResponseEntity<PaginationResultModel<AsistenciaModel>> findAllAsistencia(
            @PathVariable("periodoDesde") String periodoDesde,
            @PathVariable("periodoHasta") String periodoHasta,
            @PathVariable("establecimiento") String establecimiento,
            @PathVariable("region") String region,
            @PathVariable("provincia") String provincia,
            @PathVariable("comuna") String comuna,
            @PathVariable("pageSize") Integer pageSize,
            @PathVariable("pageNumber") Integer pageNumber) {
        PaginationResultModel<AsistenciaModel> result = asistenciaService.findAllAsistencia(
                periodoDesde, periodoHasta, establecimiento, region, provincia, comuna, pageSize, pageNumber);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/asistencia")
    public ResponseEntity<AsistenciaModel> save(@Valid @RequestBody AsistenciaIndividualModel model) {
        AsistenciaModel result = asistenciaTableService.save(model);
        return ResponseEntity.ok(result);
    }

}
