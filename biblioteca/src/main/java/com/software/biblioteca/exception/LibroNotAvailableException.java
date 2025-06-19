package com.software.biblioteca.exception;

public class LibroNotAvailableException extends RuntimeException{
    public LibroNotAvailableException(Long libroId) {
        super("El libro ya ha sido prestado");
    }
}
