package com.software.biblioteca.service;

import com.software.biblioteca.entity.Libro;
import com.software.biblioteca.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibroService {
    @Autowired
    private LibroRepository repo;

    public Libro crear(Libro libro) {
        return repo.save(libro);
    }

    public List<Libro> listar() {
        return repo.findAll();
    }

    public Libro obtenerPorId(Long id) {
        return repo.findById(id).orElseThrow();
    }

    public Libro editar(Long id, Libro libroActualizado) {
        Libro libroExistente = obtenerPorId(id);
        libroExistente.setTitulo(libroActualizado.getTitulo());
        libroExistente.setAutor(libroActualizado.getAutor());
        libroExistente.setDisponible(libroActualizado.isDisponible());
        return repo.save(libroExistente);
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }
}
