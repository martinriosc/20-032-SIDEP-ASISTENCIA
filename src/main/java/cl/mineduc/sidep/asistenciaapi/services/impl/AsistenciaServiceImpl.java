package cl.mineduc.sidep.asistenciaapi.services.impl;

import cl.mineduc.sidep.asistenciaapi.entities.AsistenciaEntity;
import cl.mineduc.sidep.asistenciaapi.exceptions.SidepException;
import cl.mineduc.sidep.asistenciaapi.filter.AsistenciaFilter;
import cl.mineduc.sidep.asistenciaapi.model.AsistenciaIndividualModel;
import cl.mineduc.sidep.asistenciaapi.model.AsistenciaModel;
import cl.mineduc.sidep.asistenciaapi.model.PaginationResultModel;
import cl.mineduc.sidep.asistenciaapi.repositories.AsistenciaRepository;
import cl.mineduc.sidep.asistenciaapi.services.IAsistenciaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class AsistenciaServiceImpl implements IAsistenciaService {
    private static final int DEFAULT_PAGE_SIZE = 20;

    private final AsistenciaRepository asistenciaRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    @Transactional
    public AsistenciaModel updateAsistenciaPupiloPorDia(AsistenciaIndividualModel asistenciaModel) {
        try {

            log.info("updateAsistenciaPupiloPorDia: {}", asistenciaModel);

            AsistenciaEntity entity = toEntity(asistenciaModel);

            if (entity.getId() == null) {
                this.asistenciaRepository.save(entity);
            } else {
                this.asistenciaRepository.update(entity);
            }
            AsistenciaModel updated = this.asistenciaRepository.findById(entity.getId());
            return updated;
        } catch (MyBatisSystemException ex) {
            throw new SidepException("Error al consultar updateAsistenciaPupiloPorDia en AsistenciaRepository", ex);
        }
    }

    @Override
    @Transactional
    public AsistenciaModel updateAsistenciaGrupalPupiloPorDia(List<AsistenciaIndividualModel> asistencias) {
        try {

            log.info("updateAsistenciaGrupalPupiloPorDia: {} registros", asistencias.size());

            AsistenciaModel lastUpdated = null;
            for (AsistenciaIndividualModel a : asistencias) {
                AsistenciaEntity entity = toEntity(a);
                if (entity.getId() == null) {
                    this.asistenciaRepository.save(entity);
                } else {
                    this.asistenciaRepository.update(entity);
                }
                lastUpdated = this.asistenciaRepository.findById(entity.getId());
            }
            return lastUpdated;
        } catch (MyBatisSystemException ex) {
            throw new SidepException("Error al consultar updateAsistenciaGrupalPupiloPorDia en AsistenciaRepository", ex);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public PaginationResultModel<AsistenciaModel> findAsistencia(String rbd, String ensenanza, String grado, String letra, Integer rut) {
        try {
            log.info("findAsistencia: rbd={}, ensenanza={}, grado={}, letra={}, rut={}", rbd, ensenanza, grado, letra, rut);

            AsistenciaFilter filter = AsistenciaFilter.builder()
                    .rbd(rbd)
                    .ensenanza(ensenanza)
                    .grado(grado)
                    .letra(letra)
                    .rut(rut)
                    .build();

            List<AsistenciaModel> result = asistenciaRepository.findAll(filter);
            Long total = asistenciaRepository.countTotal(filter);

            if (result == null || result.isEmpty()) {
                return PaginationResultModel.nullResult();
            }
            return PaginationResultModel.<AsistenciaModel>builder()
                    .resultados(result)
                    .totalElementos(total)
                    .totalPaginas(total / result.size() + ((total % result.size() == 0) ? 0 : 1))
                    .build();

        } catch (MyBatisSystemException ex) {
            throw new SidepException("Error al consultar findAsistencia en AsistenciaRepository", ex);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public PaginationResultModel<AsistenciaModel> findAsistenciaPorMesAndDia(String rbd, String ensenanza, String grado, String letra, String mes, String dia, Integer rut) {
        try {
            log.info("findAsistenciaPorMesAndDia: rbd={}, ensenanza={}, grado={}, letra={}, mes={}, dia={}, rut={}",
                    rbd, ensenanza, grado, letra, mes, dia, rut);

            AsistenciaFilter filter = AsistenciaFilter.builder()
                    .rbd(rbd)
                    .ensenanza(ensenanza)
                    .grado(grado)
                    .letra(letra)
                    .mes(mes)
                    .dia(dia)
                    .rut(rut)
                    .build();

            List<AsistenciaModel> result = asistenciaRepository.findAll(filter);
            Long total = asistenciaRepository.countTotal(filter);

            if (result == null || result.isEmpty()) {
                return PaginationResultModel.nullResult();
            }
            return PaginationResultModel.<AsistenciaModel>builder()
                    .resultados(result)
                    .totalElementos(total)
                    .totalPaginas(total / result.size() + ((total % result.size() == 0) ? 0 : 1))
                    .build();
        } catch (MyBatisSystemException ex) {
            throw new SidepException("Error al consultar findAsistenciaPorMesAndDia en AsistenciaRepository", ex);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public PaginationResultModel<AsistenciaModel> findAllAsistencia(String periodoDesde, String periodoHasta, String establecimiento, String region, String provincia, String comuna, Integer pageSize, Integer pageNumber) {
        try {
            log.info("findAllAsistencia: periodoDesde={}, periodoHasta={}, establecimiento={}, region={}, provincia={}, comuna={}, pageSize={}, pageNumber={}",
                    periodoDesde, periodoHasta, establecimiento, region, provincia, comuna, pageSize, pageNumber);

            AsistenciaFilter filter = AsistenciaFilter.builder()
                    .periodoDesde(periodoDesde)
                    .periodoHasta(periodoHasta)
                    .establecimiento(establecimiento)
                    .region(region)
                    .provincia(provincia)
                    .comuna(comuna)
                    .build();

            if (pageNumber != null) {
                int size = (pageSize != null) ? pageSize : DEFAULT_PAGE_SIZE;
                filter.setOrder("ASC");
                filter.setOrderBy("fecha");
                filter.setLimit(size);
                filter.setOffset(pageNumber * size);
            }

            List<AsistenciaModel> result = asistenciaRepository.findAll(filter);
            Long total = asistenciaRepository.countTotal(filter);

            if (result == null || result.isEmpty()) {
                return PaginationResultModel.nullResult();
            }

            return PaginationResultModel.<AsistenciaModel>builder()
                    .resultados(result)
                    .totalElementos(total)
                    .totalPaginas(total / result.size() + ((total % result.size() == 0) ? 0 : 1))
                    .build();
        } catch (MyBatisSystemException ex) {
            throw new SidepException("Error al consultar findAllAsistencia en AsistenciaRepository", ex);
        }
    }

    private AsistenciaEntity toEntity(AsistenciaIndividualModel m) {
        AsistenciaEntity e = new AsistenciaEntity();
        e.setId(m.getId());

        try {
            String json = objectMapper.writeValueAsString(m);
            e.setJsonAsistencia(json);
        } catch (JsonProcessingException ex) {
            log.error("Error convirtiendo a JSON la asistencia: {}", m, ex);
            e.setJsonAsistencia("{}");
        }
        e.setJsonAsistencia2(null);
        return e;
    }
}
