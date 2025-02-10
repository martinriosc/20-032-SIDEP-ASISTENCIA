package cl.mineduc.sidep.asistenciaapi.model;

import lombok.Data;

import java.util.Date;


@Data
public class GeneroModel {
    private Integer idGenero;          // pk_gene_id_genero
    private String nombre;             // gene_nombre
    private Date fechaCreacion;        // gene_fecha_creacion
    private Date fechaActualizacion;   // gene_fecha_actualizacion
}
