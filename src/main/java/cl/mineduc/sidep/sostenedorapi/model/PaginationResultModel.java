package cl.mineduc.sidep.sostenedorapi.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PaginationResultModel<T> {


    private Long totalElementos;
    private Integer totalPaginas;
    private List<T> resultados;


}
