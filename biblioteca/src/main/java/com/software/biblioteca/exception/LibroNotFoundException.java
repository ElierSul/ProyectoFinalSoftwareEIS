package com.software.biblioteca.exception;

public class LibroNotFoundException extends RuntimeException{
    public LibroNotFoundException(Long libroId) {
        super("Libro no encontrado");
    }
}
