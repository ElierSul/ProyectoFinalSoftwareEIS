package com.servicio.calculo.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class MonteCarloService {

    public double estimarPi(int totalPuntos, int numeroHilos) throws InterruptedException {
        ExecutorService ejecutor = Executors.newFixedThreadPool(numeroHilos);
        AtomicInteger dentroDelCirculo = new AtomicInteger();

        int puntosPorHilo = totalPuntos / numeroHilos;
        List<Callable<Void>> tareas = new ArrayList<>();

        for (int i = 0; i < numeroHilos; i++) {
            tareas.add(() -> {
                int dentroLocal = 0;
                Random aleatorio = new Random();
                for (int j = 0; j < puntosPorHilo; j++) {
                    double x = aleatorio.nextDouble();
                    double y = aleatorio.nextDouble();
                    if (x * x + y * y <= 1) {
                        dentroLocal++;
                    }
                }
                dentroDelCirculo.addAndGet(dentroLocal);
                return null;
            });
        }

        ejecutor.invokeAll(tareas);
        ejecutor.shutdown();

        return 4.0 * dentroDelCirculo.get() / totalPuntos;
    }
}
