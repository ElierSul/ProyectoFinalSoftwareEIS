package com.servicio.calculo.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
public class MultiplicadorMatricesService {

    public int[][] multiplicar(int[][] matrizA, int[][] matrizB, int numeroHilos) throws InterruptedException {
        int filasA = matrizA.length;
        int columnasB = matrizB[0].length;
        int columnasA = matrizA[0].length;

        int[][] resultado = new int[filasA][columnasB];
        ExecutorService ejecutor = Executors.newFixedThreadPool(numeroHilos);
        List<Runnable> tareas = new ArrayList<>();

        for (int fila = 0; fila < filasA; fila++) {
            final int filaActual = fila;
            tareas.add(() -> {
                for (int columna = 0; columna < columnasB; columna++) {
                    for (int k = 0; k < columnasA; k++) {
                        resultado[filaActual][columna] += matrizA[filaActual][k] * matrizB[k][columna];
                    }
                }
            });
        }

        for (Runnable tarea : tareas) {
            ejecutor.submit(tarea);
        }

        ejecutor.shutdown();
        ejecutor.awaitTermination(1, TimeUnit.MINUTES);

        return resultado;
    }
}
