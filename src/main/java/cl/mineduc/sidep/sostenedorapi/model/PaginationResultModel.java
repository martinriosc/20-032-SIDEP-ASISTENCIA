package cl.mineduc.sidep.sostenedorapi.model;

import lombok.Data;

import java.util.List;

@Data
public class PaginationResultModel<T> {


    private Long totalElementos;
    private Integer totalPaginas;
    private List<T> resultados;


}
