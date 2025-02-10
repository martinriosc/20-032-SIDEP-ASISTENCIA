package cl.mineduc.sidep.asistenciaapi.model;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Representa la información de asistencia que se retorna al cliente.
 */
@Data
public class AsistenciaModel {
    private Long id;               // ID de asistencia o ID lógico que quieras mostrar
    private String rbd;            // RBD del establecimiento (si aplica en tu dominio)
    private String ensenanza;      // Tipo de enseñanza
    private String grado;          // Grado
    private String letra;          // Letra del curso
    private Integer rut;           // RUT del párvulo/alumno

    private LocalDate fecha;       // Fecha de la asistencia (si aplica)
    private Boolean asistio;       // Indica si asistió (true/false)

    private LocalDateTime fechaRegistro;  // Fecha de creación/registro
    private LocalDateTime fechaActualizacion; // Fecha de última actualización
}
