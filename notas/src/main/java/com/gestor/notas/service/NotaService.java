package com.gestor.notas.service;

import com.gestor.notas.dto.NotaDTO;
import com.gestor.notas.dto.NotaResumenDTO;
import com.gestor.notas.entity.Nota;
import com.gestor.notas.entity.Curso;
import com.gestor.notas.entity.Estudiante;
import com.gestor.notas.exception.NotFoundException;
import com.gestor.notas.repository.NotaRepository;
import com.gestor.notas.repository.CursoRepository;
import com.gestor.notas.repository.EstudianteRepository;
import com.gestor.notas.util.TipoNota;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotaService {

    private final NotaRepository notaRepository;
    private final EstudianteRepository estudianteRepository;
    private final CursoRepository cursoRepository;

    public NotaService(NotaRepository notaRepository,
                       EstudianteRepository estudianteRepository,
                       CursoRepository cursoRepository) {
        this.notaRepository = notaRepository;
        this.estudianteRepository = estudianteRepository;
        this.cursoRepository = cursoRepository;
    }

    public Nota registrarNota(NotaDTO dto) {
        Estudiante estudiante = estudianteRepository.findById(dto.getEstudianteId())
                .orElseThrow(() -> new NotFoundException("Estudiante no encontrado"));
        Curso curso = cursoRepository.findById(dto.getCursoId())
                .orElseThrow(() -> new NotFoundException("Curso no encontrado"));

        Nota nota = new Nota();
        nota.setEstudiante(estudiante);
        nota.setCurso(curso);

        if (curso.getTipoNota() == TipoNota.CUANTITATIVA) {
            if (dto.getNotaCuantitativa() == null || dto.getNotaCuantitativa() < 0 || dto.getNotaCuantitativa() > 5) {
                throw new IllegalArgumentException("Nota cuantitativa fuera de rango (0-5)");
            }
            nota.setNotaCuantitativa(dto.getNotaCuantitativa());
        } else {
            if (dto.getNotaCualitativa() == null) {
                throw new IllegalArgumentException("Nota cualitativa requerida");
            }
            nota.setNotaCualitativa(dto.getNotaCualitativa());
        }

        return notaRepository.save(nota);
    }

    public Nota actualizarNota(Long id, NotaDTO dto) {
        Nota nota = notaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Nota no encontrada"));

        Curso curso = cursoRepository.findById(dto.getCursoId())
                .orElseThrow(() -> new NotFoundException("Curso no encontrado"));

        if (curso.getTipoNota() == TipoNota.CUANTITATIVA) {
            if (dto.getNotaCuantitativa() == null || dto.getNotaCuantitativa() < 0 || dto.getNotaCuantitativa() > 5) {
                throw new IllegalArgumentException("Nota cuantitativa fuera de rango (0-5)");
            }
            nota.setNotaCuantitativa(dto.getNotaCuantitativa());
            nota.setNotaCualitativa(null);
        } else {
            if (dto.getNotaCualitativa() == null) {
                throw new IllegalArgumentException("Nota cualitativa requerida");
            }
            nota.setNotaCualitativa(dto.getNotaCualitativa());
            nota.setNotaCuantitativa(null);
        }

        return notaRepository.save(nota);
    }

    public void eliminarNota(Long id) {
        if (!notaRepository.existsById(id)) {
            throw new NotFoundException("Nota no encontrada");
        }
        notaRepository.deleteById(id);
    }

    public List<NotaResumenDTO> obtenerResumenNotasPorCurso(Long cursoId) {
        Curso curso = cursoRepository.findById(cursoId)
                .orElseThrow(() -> new NotFoundException("Curso no encontrado"));

        List<Nota> notas = notaRepository.findByCursoId(cursoId);

        return notas.stream().map(nota -> {
            String notaValor;
            if (curso.getTipoNota() == TipoNota.CUANTITATIVA) {
                notaValor = String.valueOf(nota.getNotaCuantitativa());
            } else {
                notaValor = String.valueOf(nota.getNotaCualitativa());
            }

            return new NotaResumenDTO(
                    curso.getNombre(),
                    nota.getEstudiante().getNombre(),
                    notaValor
            );
        }).toList();
    }
}
