package com.gestor.notas.dto;

import com.gestor.notas.util.NotaCualitativa;
import lombok.Data;

@Data
public class NotaDTO {
    private Long estudianteId;
    private Long cursoId;
    private Double notaCuantitativa;
    private NotaCualitativa notaCualitativa;
}
