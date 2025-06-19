package com.software.biblioteca.controller;

import com.software.biblioteca.dto.PrestamoDTO;
import com.software.biblioteca.service.PrestamoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prestamos")
public class PrestamoController {
    @Autowired
    private PrestamoService service;

    @PostMapping("/prestar")
    public PrestamoDTO prestar(@RequestBody PrestamoDTO dto) {
        return PrestamoDTO.from(service.prestar(dto));
    }


    @PostMapping("/devolver")
    public PrestamoDTO devolver(@RequestBody PrestamoDTO dto) {
        return PrestamoDTO.from(service.devolver(dto));
    }

    @GetMapping
    public List<PrestamoDTO> listar() {
        return service.listar().stream()
                .map(PrestamoDTO::from)
                .toList();
    }
}
