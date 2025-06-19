package com.gestor.notas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NotaResumenDTO {
    private String nombreCurso;
    private String nombreEstudiante;
    private String nota;
}
