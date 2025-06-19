package com.software.biblioteca.service;

import com.software.biblioteca.entity.Usuario;
import com.software.biblioteca.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository repo;

    public Usuario crear(Usuario usuario) {
        return repo.save(usuario);
    }

    public List<Usuario> listar() {
        return repo.findAll();
    }

    public Usuario obtenerPorId(Long id) {
        return repo.findById(id).orElseThrow();
    }

    public Usuario editar(Long id, Usuario usuarioActualizado) {
        Usuario existente = obtenerPorId(id);
        existente.setNombre(usuarioActualizado.getNombre());
        existente.setRol(usuarioActualizado.getRol());
        return repo.save(existente);
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }
}
