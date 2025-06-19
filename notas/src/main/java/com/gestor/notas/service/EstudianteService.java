package com.gestor.notas.service;

import com.gestor.notas.entity.Estudiante;
import com.gestor.notas.exception.NotFoundException;
import com.gestor.notas.repository.EstudianteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstudianteService {

    private final EstudianteRepository estudianteRepository;

    public EstudianteService(EstudianteRepository estudianteRepository) {
        this.estudianteRepository = estudianteRepository;
    }

    public List<Estudiante> listarEstudiantes() {
        return estudianteRepository.findAll();
    }

    public Estudiante crearEstudiante(Estudiante estudiante) {
        return estudianteRepository.save(estudiante);
    }

    public Estudiante actualizarEstudiante(Long id, Estudiante estudianteActualizado) {
        Estudiante estudiante = estudianteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Estudiante no encontrado"));
        estudiante.setNombre(estudianteActualizado.getNombre());
        return estudianteRepository.save(estudiante);
    }

    public void eliminar(Long id) {
        if (!estudianteRepository.existsById(id)) {
            throw new NotFoundException("Estudiante no encontrado");
        }
        estudianteRepository.deleteById(id);
    }
}
