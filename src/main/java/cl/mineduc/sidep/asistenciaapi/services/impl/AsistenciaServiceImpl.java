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

/**
 * Implementación completa del servicio de Asistencia
 */
@Service
@Log4j2
@RequiredArgsConstructor
public class AsistenciaServiceImpl implements IAsistenciaService {
    private static final int DEFAULT_PAGE_SIZE = 20;

    private final AsistenciaRepository asistenciaRepository;
    /**
     * Para convertir la información a un JSON en asis_json_asistencia,
     * si deseas almacenar varios campos en un solo string.
     */
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Actualiza o inserta la asistencia de un pupilo en un día específico.
     * 1) Convierte el modelo a Entity
     * 2) Según si hay ID o no, hace update o insert
     * 3) Retorna el registro final desde la BD
     */
    @Override
    @Transactional
    public AsistenciaModel updateAsistenciaPupiloPorDia(AsistenciaIndividualModel asistenciaModel) {
        try {

            log.info("updateAsistenciaPupiloPorDia: {}", asistenciaModel);

            // 1) Convertimos AsistenciaIndividualModel -> AsistenciaEntity
            AsistenciaEntity entity = toEntity(asistenciaModel);

            // 2) Lógica de "upsert": si 'id' es null => insert, si no => update
            if (entity.getId() == null) {
                // INSERT
                this.asistenciaRepository.save(entity);
            } else {
                // UPDATE
                this.asistenciaRepository.update(entity);
            }

            // 3) Consultamos la BD para devolver el registro actualizado
            AsistenciaModel updated = this.asistenciaRepository.findById(entity.getId());
            return updated;
        } catch (MyBatisSystemException ex) {
            throw new SidepException("Error al consultar updateAsistenciaPupiloPorDia en AsistenciaRepository", ex);
        }
    }

    /**
     * Actualiza de forma grupal la asistencia de varios pupilos.
     * Se realiza la misma lógica (upsert) para cada pupilo de la lista.
     */
    @Override
    @Transactional
    public AsistenciaModel updateAsistenciaGrupalPupiloPorDia(List<AsistenciaIndividualModel> asistencias) {
        try {

            log.info("updateAsistenciaGrupalPupiloPorDia: {} registros", asistencias.size());

            AsistenciaModel lastUpdated = null;
            // Iteramos cada asistencia individual
            for (AsistenciaIndividualModel a : asistencias) {
                AsistenciaEntity entity = toEntity(a);
                if (entity.getId() == null) {
                    this.asistenciaRepository.save(entity);
                } else {
                    this.asistenciaRepository.update(entity);
                }
                // Obtenemos el registro final desde la BD
                lastUpdated = this.asistenciaRepository.findById(entity.getId());
            }
            // Retornamos el último registro actualizado (o uno genérico).
            // Si deseas retornar la lista completa, deberías cambiar el método en la interfaz
            // para devolver List<AsistenciaModel>.
            return lastUpdated;
        } catch (MyBatisSystemException ex) {
            throw new SidepException("Error al consultar updateAsistenciaGrupalPupiloPorDia en AsistenciaRepository", ex);
        }
    }

    /**
     * Busca asistencias filtrando por rbd, enseñanza, grado, letra, rut.
     * Retorna un PaginationResultModel con la lista y totales.
     */
    @Override
    @Transactional(readOnly = true)
    public PaginationResultModel<AsistenciaModel> findAsistencia(String rbd, String ensenanza, String grado, String letra, Integer rut) {
        try {
            log.info("findAsistencia: rbd={}, ensenanza={}, grado={}, letra={}, rut={}", rbd, ensenanza, grado, letra, rut);

            // 1) Construir el filtro
            AsistenciaFilter filter = AsistenciaFilter.builder()
                    .rbd(rbd)
                    .ensenanza(ensenanza)
                    .grado(grado)
                    .letra(letra)
                    .rut(rut)
                    .build();

            // 2) Consultar en el repositorio
            List<AsistenciaModel> result = asistenciaRepository.findAll(filter);
            Long total = asistenciaRepository.countTotal(filter);

            // 3) Retornar paginado
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

    /**
     * Busca asistencias filtrando además por mes y día,
     * además de rbd, enseñanza, grado, letra, y rut.
     */
    @Override
    @Transactional(readOnly = true)
    public PaginationResultModel<AsistenciaModel> findAsistenciaPorMesAndDia(String rbd, String ensenanza, String grado, String letra, String mes, String dia, Integer rut) {
        try {
            log.info("findAsistenciaPorMesAndDia: rbd={}, ensenanza={}, grado={}, letra={}, mes={}, dia={}, rut={}",
                    rbd, ensenanza, grado, letra, mes, dia, rut);

            // 1) Filtro
            AsistenciaFilter filter = AsistenciaFilter.builder()
                    .rbd(rbd)
                    .ensenanza(ensenanza)
                    .grado(grado)
                    .letra(letra)
                    .mes(mes)
                    .dia(dia)
                    .rut(rut)
                    .build();

            // 2) Consulta
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

    /**
     * Busca todas las asistencias dentro de un periodo y con filtros de establecimiento, region, provincia, comuna, etc.
     * Maneja paginación (pageSize/pageNumber).
     */
    @Override
    @Transactional(readOnly = true)
    public PaginationResultModel<AsistenciaModel> findAllAsistencia(String periodoDesde, String periodoHasta, String establecimiento, String region, String provincia, String comuna, Integer pageSize, Integer pageNumber) {
        try {
            log.info("findAllAsistencia: periodoDesde={}, periodoHasta={}, establecimiento={}, region={}, provincia={}, comuna={}, pageSize={}, pageNumber={}",
                    periodoDesde, periodoHasta, establecimiento, region, provincia, comuna, pageSize, pageNumber);

            // 1) Filtro
            AsistenciaFilter filter = AsistenciaFilter.builder()
                    .periodoDesde(periodoDesde)
                    .periodoHasta(periodoHasta)
                    .establecimiento(establecimiento)
                    .region(region)
                    .provincia(provincia)
                    .comuna(comuna)
                    .build();

            // 2) Paginación
            if (pageNumber != null) {
                int size = (pageSize != null) ? pageSize : DEFAULT_PAGE_SIZE;
                filter.setOrder("ASC");
                filter.setOrderBy("fecha");
                filter.setLimit(size);
                filter.setOffset(pageNumber * size);
            }

            // 3) Consulta a la BD
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

    /**
     * Método privado para convertir AsistenciaIndividualModel a AsistenciaEntity.
     * Aquí se ejemplifica guardar rbd, grado, letra, etc. en un JSON.
     */
    private AsistenciaEntity toEntity(AsistenciaIndividualModel m) {
        AsistenciaEntity e = new AsistenciaEntity();
        e.setId(m.getId());

        // Generamos JSON con la información que no está en columnas directas
        try {
            // Podrías armar un map o un objeto intermedio
            // A modo de ejemplo, guardamos la misma AsistenciaIndividualModel como JSON:
            String json = objectMapper.writeValueAsString(m);
            e.setJsonAsistencia(json);
        } catch (JsonProcessingException ex) {
            log.error("Error convirtiendo a JSON la asistencia: {}", m, ex);
            e.setJsonAsistencia("{}");
        }

        // asis_json_asistencia_2 lo dejamos vacío o con otro contenido
        e.setJsonAsistencia2(null);


        return e;
    }
}
