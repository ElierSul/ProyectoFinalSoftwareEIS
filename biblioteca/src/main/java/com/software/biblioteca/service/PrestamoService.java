package com.software.biblioteca.service;

import com.software.biblioteca.dto.PrestamoDTO;
import com.software.biblioteca.entity.Libro;
import com.software.biblioteca.entity.Prestamo;
import com.software.biblioteca.entity.Usuario;
import com.software.biblioteca.exception.LibroNotAvailableException;
import com.software.biblioteca.exception.LibroNotFoundException;
import com.software.biblioteca.exception.PrestamoNotFoundException;
import com.software.biblioteca.exception.UsuarioNotFoundException;
import com.software.biblioteca.repository.LibroRepository;
import com.software.biblioteca.repository.PrestamoRepository;
import com.software.biblioteca.repository.UsuarioRepository;
import com.software.biblioteca.util.RolUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class PrestamoService {
    @Autowired
    private PrestamoRepository repo;

    @Autowired
    private UsuarioRepository usuarioRepo;

    @Autowired
    private LibroRepository libroRepo;

    public Prestamo prestar(PrestamoDTO dto) {
        Usuario usuario = usuarioRepo.findById(dto.getUsuarioId())
                .orElseThrow(() -> new UsuarioNotFoundException(dto.getUsuarioId()));
        Libro libro = libroRepo.findById(dto.getLibroId())
                .orElseThrow(() -> new LibroNotFoundException(dto.getLibroId()));

        if (!libro.isDisponible())
            throw new LibroNotAvailableException(libro.getId());

        if (dto.getFechaPrestamo().isAfter(LocalDate.now()))
            throw new IllegalArgumentException("La fecha de préstamo no puede estar en el futuro.");

        Prestamo prestamo = new Prestamo();
        prestamo.setUsuario(usuario);
        prestamo.setLibro(libro);
        prestamo.setFechaPrestamo(dto.getFechaPrestamo());
        prestamo.setFechaDevolucion(null);

        libro.setDisponible(false);
        libroRepo.save(libro);

        return repo.save(prestamo);
    }

    public Prestamo devolver(PrestamoDTO dto) {
        Prestamo prestamo = repo.findById(dto.getId())
                .orElseThrow(() -> new PrestamoNotFoundException(dto.getId()));

        LocalDate fechaPrestamo = prestamo.getFechaPrestamo();
        LocalDate fechaDevolucion = dto.getFechaDevolucion();

        if (fechaDevolucion.isAfter(LocalDate.now()))
            throw new IllegalArgumentException("La fecha de devolución no puede estar en el futuro.");

        if (fechaDevolucion.isBefore(fechaPrestamo))
            throw new IllegalArgumentException("La fecha de devolución no puede ser anterior a la fecha de préstamo.");

        prestamo.setFechaDevolucion(fechaDevolucion);

        long diasRetraso = ChronoUnit.DAYS.between(fechaPrestamo.plusDays(7), fechaDevolucion);

        long multa = 0;
        if (diasRetraso > 0) {
            RolUsuario rol = prestamo.getUsuario().getRol();
            multa = switch (rol) {
                case DOCENTE -> diasRetraso * 5000;
                case ESTUDIANTE -> diasRetraso * 1000;
                case EMPLEADO -> diasRetraso * 2500;
            };
        }

        prestamo.setMulta(multa);

        Libro libro = prestamo.getLibro();
        libro.setDisponible(true);
        libroRepo.save(libro);

        return repo.save(prestamo);
    }

    public List<Prestamo> listar() {
        return repo.findAll();
    }
}
