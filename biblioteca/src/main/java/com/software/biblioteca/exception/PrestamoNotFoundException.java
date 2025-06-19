package com.software.biblioteca.exception;

public class PrestamoNotFoundException extends  RuntimeException{
    public PrestamoNotFoundException(Long id) {
        super("Prestamo no encontrado");
    }
}
