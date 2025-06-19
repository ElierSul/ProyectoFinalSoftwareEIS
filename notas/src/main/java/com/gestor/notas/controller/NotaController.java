package com.gestor.notas.controller;

import com.gestor.notas.dto.NotaDTO;
import com.gestor.notas.dto.NotaResumenDTO;
import com.gestor.notas.entity.Nota;
import com.gestor.notas.service.NotaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notas")
public class NotaController {

    private final NotaService notaService;

    public NotaController(NotaService notaService) {
        this.notaService = notaService;
    }

    @PostMapping
    public Nota registrar(@RequestBody NotaDTO dto) {
        return notaService.registrarNota(dto);
    }

    @PutMapping("/{id}")
    public Nota actualizar(@PathVariable Long id, @RequestBody NotaDTO dto) {
        return notaService.actualizarNota(id, dto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        notaService.eliminarNota(id);
    }

    @GetMapping("/curso/{cursoId}")
    public List<NotaResumenDTO> listarResumenPorCurso(@PathVariable Long cursoId) {
        return notaService.obtenerResumenNotasPorCurso(cursoId);
    }
}
