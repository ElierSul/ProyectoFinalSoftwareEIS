package com.servicio.calculo.controller;

import com.servicio.calculo.service.MonteCarloService;
import com.servicio.calculo.service.MultiplicadorMatricesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/algoritmos")
public class AlgoritmosController {

    @Autowired
    private MonteCarloService montecarloServicio;

    @Autowired
    private MultiplicadorMatricesService multiplicadorMatricesServicio;

    @GetMapping("/montecarlo")
    public ResponseEntity<Double> ejecutarMontecarlo(
            @RequestParam int puntos,
            @RequestParam int hilos) throws InterruptedException {
        double resultado = montecarloServicio.estimarPi(puntos, hilos);
        return ResponseEntity.ok(resultado);
    }

    @PostMapping("/multiplicar")
    public ResponseEntity<int[][]> multiplicarMatrices(
            @RequestBody Map<String, int[][]> datos,
            @RequestParam int hilos) throws InterruptedException {
        int[][] matrizA = datos.get("A");
        int[][] matrizB = datos.get("B");
        int[][] resultado = multiplicadorMatricesServicio.multiplicar(matrizA, matrizB, hilos);
        return ResponseEntity.ok(resultado);
    }
}
