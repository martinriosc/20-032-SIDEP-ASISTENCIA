package cl.mineduc.sidep.sostenedorapi.model;

import lombok.Builder;
import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data
@Builder
public class PaginationResultModel<T> {


    private Long totalElementos;
    private Long totalPaginas;
    private List<T> resultados;


    public static <T> PaginationResultModel<T> nullResult() {
        return PaginationResultModel.<T>builder()
                .totalPaginas(0L)
                .totalElementos(0L)
                .resultados(Collections.emptyList())
                .build();
    }
}
