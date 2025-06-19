package com.gestor.notas.repository;

import com.gestor.notas.entity.Nota;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotaRepository extends JpaRepository<Nota, Long> {
    List<Nota> findByCursoId(Long idCurso);
}
