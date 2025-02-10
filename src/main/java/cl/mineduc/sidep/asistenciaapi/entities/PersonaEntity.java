package cl.mineduc.sidep.asistenciaapi.entities;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class PersonaEntity {

    private Long id;               // pk_pers_id_persona
    private Long etniaId;          // fk_pers_id_etnia
    private Long nacionalidadId;   // fk_pers_id_nacionalidad
    private Long estadoCivilId;    // fk_pers_id_estado_civil
    private Long sexoId;           // fk_pers_id_sexo
    private Integer rut;           // pers_rut
    private String dv;             // pers_dv
    private String nombre;         // pers_nombre
    private String primerApellido; // pers_primer_apellido
    private String segundoApellido; // pers_segundo_apellido
    private LocalDate fechaNacimiento;  // pers_fecha_nacimiento
    private LocalDate fechaDefuncion;   // pers_fecha_defuncion (puede ser null)
    private Boolean normalizado;        // pers_normalizado
    private Boolean esRut;              // pers_es_rut
    private LocalDate fechaNormalizacion; // pers_fecha_normalizacion
    private LocalDateTime fechaCreacion;      // pers_fecha_creacion
    private LocalDateTime fechaActualizacion; // pers_fecha_actualizacion
}
