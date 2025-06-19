package com.software.biblioteca.repository;

import com.software.biblioteca.entity.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrestamoRepository extends JpaRepository<Prestamo,Long> {
}
