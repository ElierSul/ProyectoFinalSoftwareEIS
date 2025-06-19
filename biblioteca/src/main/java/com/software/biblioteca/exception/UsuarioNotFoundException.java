package com.software.biblioteca.exception;

public class UsuarioNotFoundException extends RuntimeException{
    public UsuarioNotFoundException(Long usuarioId) {
        super("Usuario no encontrado");
    }
}
