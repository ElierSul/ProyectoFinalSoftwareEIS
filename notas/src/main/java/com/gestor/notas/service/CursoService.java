package com.gestor.notas.service;

import com.gestor.notas.entity.Curso;
import com.gestor.notas.exception.NotFoundException;
import com.gestor.notas.repository.CursoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CursoService {
    private final CursoRepository cursoRepository;

    public CursoService(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    public List<Curso> listarCursos() {
        return cursoRepository.findAll();
    }

    public Curso crearCurso(Curso curso) {
        return cursoRepository.save(curso);
    }

    public Curso actualizarCurso(Long id, Curso cursoActualizado) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Curso no encontrado"));
        curso.setNombre(cursoActualizado.getNombre());
        curso.setTipoNota(cursoActualizado.getTipoNota());
        return cursoRepository.save(curso);
    }

    public void eliminar(Long id) {
        if (!cursoRepository.existsById(id)) {
            throw new NotFoundException("Curso no encontrado");
        }
        cursoRepository.deleteById(id);
    }
}
