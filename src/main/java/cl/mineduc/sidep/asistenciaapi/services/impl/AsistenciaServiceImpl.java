package cl.mineduc.sidep.asistenciaapi.services.impl;

import cl.mineduc.sidep.asistenciaapi.entities.AsistenciaEntity;
import cl.mineduc.sidep.asistenciaapi.exceptions.SidepException;
import cl.mineduc.sidep.asistenciaapi.filter.AsistenciaFilter;
import cl.mineduc.sidep.asistenciaapi.model.AsistenciaIndividualModel;
import cl.mineduc.sidep.asistenciaapi.model.AsistenciaModel;
import cl.mineduc.sidep.asistenciaapi.model.CalendarioModel;
import cl.mineduc.sidep.asistenciaapi.model.PaginationResultModel;
import cl.mineduc.sidep.asistenciaapi.repositories.AsistenciaRepository;
import cl.mineduc.sidep.asistenciaapi.repositories.AsistenciaTableRepository;
import cl.mineduc.sidep.asistenciaapi.repositories.CalendarioRepository;
import cl.mineduc.sidep.asistenciaapi.services.AsistenciaTableService;
import cl.mineduc.sidep.asistenciaapi.services.IAsistenciaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Log4j2
@RequiredArgsConstructor
public class AsistenciaServiceImpl implements IAsistenciaService {

    private static final int DEFAULT_PAGE_SIZE = 20;

    private final AsistenciaRepository asistenciaRepository;
    private final AsistenciaTableService asistenciaTableService;
    private final AsistenciaTableRepository asistenciaTableRepository;
    private final CalendarioRepository calendarioRepository;

    /**
     * Actualiza (o crea) la asistencia para un pupilo en un día específico.
     */
    @Override
    @Transactional
    public AsistenciaModel updateAsistenciaPupiloPorDia(AsistenciaIndividualModel asistenciaModel) {
        try {
            String diaStr = asistenciaModel.getDia();
            String mesStr = asistenciaModel.getMes();
            if (diaStr == null || mesStr == null) {
                throw new SidepException("Los campos día y mes son obligatorios.", null);
            }

            int dia = Integer.parseInt(diaStr);
            int mes = Integer.parseInt(mesStr);
            int anio = LocalDate.now().getYear();

            CalendarioModel calendario = calendarioRepository.findByDiaMesAnio(dia, mes, anio);
            if (calendario == null) {
                throw new SidepException(String.format(
                        "No existe un calendario para la fecha: día %d, mes %d, año %d",
                        dia, mes, anio
                ), null);
            }
            if (Boolean.FALSE.equals(calendario.getTrabajado())) {
                throw new SidepException("No se puede actualizar asistencia: la fecha no es trabajada.", null);
            }

            Long unidadEducativaId = asistenciaTableRepository.findUnidadEducativaByRbd(asistenciaModel.getRbd());
            Long nivelGradoId = asistenciaTableRepository.findNivelGradoIdByNombre(asistenciaModel.getGrado());
            Long gradoId = asistenciaTableRepository.findGradoByUnidadEducativaAndNivelGrado(unidadEducativaId, nivelGradoId);
            Long grupoId = asistenciaTableRepository.findGrupoByGradoLetra(gradoId, asistenciaModel.getLetra());
            Long personaId = asistenciaTableRepository.findPersonaByRut(asistenciaModel.getRut());
            Long parvuloId = asistenciaTableRepository.findParvuloByPersona(personaId);
            Long matriculaUeId = asistenciaTableRepository.findMatriculaUnidadEducativa(parvuloId, unidadEducativaId);
            Long matriculaGrupoId = asistenciaTableRepository.findMatriculaGrupo(grupoId, matriculaUeId);

            Map<String, Object> params = new HashMap<>();

            Long calendarioId = calendario.getId();
            params.put("calendarioId", calendario.getId());
            params.put("matriculaGrupoId", matriculaGrupoId);

            AsistenciaModel asistenciaExistente = asistenciaRepository.findByCalendarioAndMatriculaGrupo(calendarioId, matriculaGrupoId);


            asistenciaModel.setCalendarioId(calendario.getId());

            AsistenciaEntity entity = toEntityIndividual(asistenciaModel);
            entity.setPresente(asistenciaModel.getPresente());

            if (asistenciaExistente != null) {
                Long asistenciaId = asistenciaExistente.getId();
                AsistenciaModel updated = asistenciaTableService.update(asistenciaId, asistenciaModel);
                return updated;
            } else {
                AsistenciaModel created = asistenciaTableService.save(asistenciaModel);
                return created;
            }

        } catch (MyBatisSystemException ex) {
            throw new SidepException("Error al consultar updateAsistenciaPupiloPorDia en AsistenciaRepository", ex);
        }
    }

    /**
     * Actualiza (o crea) asistencia para un grupo de alumnos.
     */
    @Override
    @Transactional
    public List<AsistenciaModel> updateAsistenciaGrupalPupiloPorDia(List<AsistenciaIndividualModel> asistencias) {
        try {
            log.info("updateAsistenciaGrupalPupiloPorDia: {} registros", asistencias.size());
            List<AsistenciaModel> updatedList = new ArrayList<>();

            for (AsistenciaIndividualModel alumno : asistencias) {
                AsistenciaModel updated = this.updateAsistenciaPupiloPorDia(alumno);
                updatedList.add(updated);
            }
            return updatedList;

        } catch (MyBatisSystemException ex) {
            throw new SidepException("Error al consultar updateAsistenciaGrupalPupiloPorDia en AsistenciaRepository", ex);
        }
    }

    /**
     * Búsqueda de asistencia por algunos filtros (rbd, ensenanza, grado, letra, rut).
     */
    @Override
    @Transactional(readOnly = true)
    public PaginationResultModel<AsistenciaModel> findAsistencia(
            String rbd, String ensenanza, String grado, String letra, Long rut
    ) {
        try {
            log.info("findAsistencia: rbd={}, ensenanza={}, grado={}, letra={}, rut={}",
                    rbd, ensenanza, grado, letra, rut);

            AsistenciaFilter filter = AsistenciaFilter.builder()
                    .rbd(rbd)
                    .ensenanza(ensenanza)
                    .grado(grado)
                    .letra(letra)
                    .rut(rut)
                    .build();

            List<AsistenciaModel> result = asistenciaRepository.findAll(filter);
            Long total = asistenciaRepository.countTotal(filter);

            if (result.isEmpty()) {
                return PaginationResultModel.nullResult();
            }

            long totalElementos = total;
            long size = result.size();
            long totalPaginas = totalElementos / size;
            if (totalElementos % size != 0) {
                totalPaginas++;
            }

            return PaginationResultModel.<AsistenciaModel>builder()
                    .resultados(result)
                    .totalElementos(totalElementos)
                    .totalPaginas(totalPaginas)
                    .build();

        } catch (MyBatisSystemException ex) {
            throw new SidepException("Error al consultar findAsistencia en AsistenciaRepository", ex);
        }
    }

    /**
     * Búsqueda de asistencia filtrando por mes y día
     */
    @Override
    @Transactional(readOnly = true)
    public PaginationResultModel<AsistenciaModel> findAsistenciaPorMesAndDia(
            String rbd,
            String ensenanza,
            String grado,
            String letra,
            String mes,
            String dia,
            Long rut
    ) {
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

            if (result.isEmpty()) {
                return PaginationResultModel.nullResult();
            }

            long totalElementos = total;
            long size = result.size();
            long totalPaginas = totalElementos / size;
            if (totalElementos % size != 0) {
                totalPaginas++;
            }

            return PaginationResultModel.<AsistenciaModel>builder()
                    .resultados(result)
                    .totalElementos(totalElementos)
                    .totalPaginas(totalPaginas)
                    .build();

        } catch (MyBatisSystemException ex) {
            throw new SidepException("Error al consultar findAsistenciaPorMesAndDia en AsistenciaRepository", ex);
        }
    }

    /**
     * Búsqueda (paginada) de asistencias por rango de fechas (periodoDesde/Hasta),
     * establecimiento, región, provincia, comuna, etc.
     */
    @Override
    @Transactional(readOnly = true)
    public PaginationResultModel<AsistenciaModel> findAllAsistencia(
            String periodoDesde,
            String periodoHasta,
            String establecimiento,
            String region,
            String provincia,
            String comuna,
            Integer pageSize,
            Integer pageNumber
    ) {
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
                int safePageNumber = (pageNumber < 1) ? 1 : pageNumber;

                filter.setOrder("ASC");
                filter.setOrderBy("asis_fecha_creacion");
                filter.setLimit(size);
                filter.setOffset((safePageNumber - 1) * size);
            }

            List<AsistenciaModel> result = asistenciaRepository.findAll(filter);
            Long total = asistenciaRepository.countTotal(filter);

            if (result.isEmpty()) {
                return PaginationResultModel.nullResult();
            }
            long totalElementos = total;
            int sizeUsed = (filter.getLimit() != null) ? filter.getLimit() : result.size();
            long totalPaginas = totalElementos / sizeUsed;
            if (totalElementos % sizeUsed != 0) {
                totalPaginas++;
            }

            return PaginationResultModel.<AsistenciaModel>builder()
                    .resultados(result)
                    .totalElementos(totalElementos)
                    .totalPaginas(totalPaginas)
                    .build();

        } catch (MyBatisSystemException ex) {
            throw new SidepException("Error al consultar findAllAsistencia en AsistenciaRepository", ex);
        }
    }


    private AsistenciaEntity toEntityIndividual(AsistenciaIndividualModel m) {
        AsistenciaEntity e = new AsistenciaEntity();
        if (m.getId() == null || m.getId() == 0) {
            e.setId(null);
        } else {
            e.setId(m.getId());
        }
        e.setCalendarioId(m.getCalendarioId());
        // En este punto se debe tener ya calculado y seteado el calendarioId
        e.setFechaActualizacion(LocalDateTime.now());
        // Se setea el flag presente desde el modelo
        e.setPresente(m.getPresente());
        return e;
    }

    // Si se requiere convertir de entidad a modelo para el GET
    private AsistenciaModel toModel(AsistenciaEntity e, AsistenciaIndividualModel m) {
        AsistenciaModel model = new AsistenciaModel();
        model.setId(e.getId());
        model.setRbd(m.getRbd().toString());
        model.setGrado(m.getGrado().toString());
        model.setLetra(m.getLetra());
        model.setRut(Long.valueOf(m.getRut()));
        model.setPresente(e.getPresente());
        // Convertir la fecha (por ejemplo, a String) si es necesario:
        if (e.getFechaCreacion() != null) {
            model.setFechaRegistro(e.getFechaCreacion().toString());
        }
        return model;
    }

}
