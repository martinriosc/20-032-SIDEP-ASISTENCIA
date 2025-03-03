package cl.mineduc.sidep.asistenciaapi.services.impl;

import cl.mineduc.sidep.asistenciaapi.entities.AsistenciaEntity;
import cl.mineduc.sidep.asistenciaapi.entities.CalendarioEntity;
import cl.mineduc.sidep.asistenciaapi.exceptions.SidepException;
import cl.mineduc.sidep.asistenciaapi.filter.AsistenciaFilter;
import cl.mineduc.sidep.asistenciaapi.model.AsistenciaIndividualModel;
import cl.mineduc.sidep.asistenciaapi.model.AsistenciaModel;
import cl.mineduc.sidep.asistenciaapi.model.CalendarioModel;
import cl.mineduc.sidep.asistenciaapi.model.PaginationResultModel;
import cl.mineduc.sidep.asistenciaapi.repositories.AsistenciaRepository;
import cl.mineduc.sidep.asistenciaapi.repositories.CalendarioRepository;
import cl.mineduc.sidep.asistenciaapi.services.IAsistenciaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class AsistenciaServiceImpl implements IAsistenciaService {
    private static final int DEFAULT_PAGE_SIZE = 20;

    private final AsistenciaRepository asistenciaRepository;
    private final CalendarioRepository calendarioRepository;

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

            CalendarioModel calendario = calendarioRepository.findByDiaMes(dia, mes);

            if (calendario == null) {
                throw new SidepException("No existe un calendario para la fecha: día " + dia + ", mes " + mes, null);
            }

            if (Boolean.TRUE.equals(calendario.getTrabajado())) {
                throw new SidepException("No se puede actualizar asistencia: el calendario ya está trabajado.", null);
            }

            AsistenciaEntity entity = toEntityIndividual(asistenciaModel);

            AsistenciaEntity savedAsistencia = null;
            if (entity.getId() == null) {
               asistenciaRepository.save(entity);
            } else {
               asistenciaRepository.update(entity);
            }



            AsistenciaModel updated = asistenciaRepository.findById(entity.getId());
            return updated;

        } catch (MyBatisSystemException ex) {
            throw new SidepException("Error al consultar updateAsistenciaPupiloPorDia en AsistenciaRepository", ex);
        }
    }


    @Override
    @Transactional
    public List<AsistenciaModel> updateAsistenciaGrupalPupiloPorDia(List<AsistenciaIndividualModel> asistencias) {
        try {

            log.info("updateAsistenciaGrupalPupiloPorDia: {} registros", asistencias.size());


            List<AsistenciaModel> updatedList = new ArrayList<>();

            for (AsistenciaIndividualModel a : asistencias) {
                AsistenciaEntity entity = toEntityIndividual(a);
                if (entity.getId() == null) {
                    this.asistenciaRepository.save(entity);
                } else {
                    this.asistenciaRepository.update(entity);
                }
                updatedList.add(this.asistenciaRepository.findById(entity.getId()));
            }
            return updatedList;
        } catch (MyBatisSystemException ex) {
            throw new SidepException("Error al consultar updateAsistenciaGrupalPupiloPorDia en AsistenciaRepository", ex);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public PaginationResultModel<AsistenciaModel> findAsistencia(String rbd, String ensenanza, String grado, String letra, Long rut) {
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
                    .totalElementos((long) result.size())
                    .totalPaginas(total / result.size() + ((total % result.size() == 0) ? 0 : 1))
                    .build();

        } catch (MyBatisSystemException ex) {
            throw new SidepException("Error al consultar findAsistencia en AsistenciaRepository", ex);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public PaginationResultModel<AsistenciaModel> findAsistenciaPorMesAndDia(String rbd, String ensenanza, String grado, String letra, String mes, String dia, Long rut) {
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
                    .totalElementos((long) result.size())
                    .totalPaginas(total / result.size() + ((total % result.size() == 0) ? 0 : 1))
                    .build();
        } catch (MyBatisSystemException ex) {
            throw new SidepException("Error al consultar findAsistenciaPorMesAndDia en AsistenciaRepository", ex);
        }
    }

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

            // Manejo de paginación => asume que pageNumber=1 es la primera página
            if (pageNumber != null) {
                // Si no envían pageSize, usar un DEFAULT_PAGE_SIZE
                int size = (pageSize != null) ? pageSize : DEFAULT_PAGE_SIZE;

                // Forzamos un valor mínimo 1 para no tener offset negativo
                int safePageNumber = (pageNumber < 1) ? 1 : pageNumber;

                filter.setOrder("ASC");
                filter.setOrderBy("asis_fecha_creacion");
                filter.setLimit(size);
                filter.setOffset((safePageNumber - 1) * size);
            }

            List<AsistenciaModel> result = asistenciaRepository.findAll(filter);
            Long total = asistenciaRepository.countTotal(filter);

            if (result == null || result.isEmpty()) {
                return PaginationResultModel.nullResult();
            }

            // Cálculo de total de páginas: total / pageSize
            // (Si no usas la página, no pasa nada)
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
        e.setFechaActualizacion(LocalDateTime.now());

        return e;
    }

    private AsistenciaEntity toEntity(AsistenciaModel m) {
        AsistenciaEntity e = new AsistenciaEntity();

        if (m.getId() == null || m.getId() == 0) {
            e.setId(null);
        } else {
            e.setId(m.getId());
        }

        e.setCalendarioId(m.getCalendarioId());
        e.setFechaActualizacion(LocalDateTime.now());

        return e;
    }

    private AsistenciaModel toModel(AsistenciaEntity m) {
        AsistenciaModel e = new AsistenciaModel();

        if (m.getId() == null || m.getId() == 0) {
            e.setId(null);
        } else {
            e.setId(m.getId());
        }

        e.setCalendarioId(m.getCalendarioId());
        e.setFechaActualizacion(LocalDateTime.now());

        return e;
    }

    private CalendarioEntity toCalendarioEntity(CalendarioModel m) {
        CalendarioEntity e = new CalendarioEntity();
        e.setId(m.getId());
        e.setTrabajado(m.getTrabajado());
        e.setFechaActualizacion(m.getFechaActualizacion());
        e.setGrupoId(m.getGrupo() != null ? m.getGrupo().getId() : null);
        e.setFechaCreacion(m.getFechaCreacion());
        e.setFechaActualizacion(e.getFechaActualizacion());
        return e;
    }

    private CalendarioModel toCalendarioModel(CalendarioEntity e) {
        CalendarioModel m = new CalendarioModel();
        m.setId(e.getId());
        m.setTrabajado(e.getTrabajado());
        m.setFechaActualizacion(e.getFechaActualizacion());
        m.setFechaCreacion(e.getFechaCreacion());
        return m;
    }
}
