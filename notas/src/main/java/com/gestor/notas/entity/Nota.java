package com.gestor.notas.entity;

import com.gestor.notas.util.NotaCualitativa;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Nota {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Estudiante estudiante;

    @ManyToOne
    private Curso curso;

    private Double notaCuantitativa;

    @Enumerated(EnumType.STRING)
    private NotaCualitativa notaCualitativa;
}
