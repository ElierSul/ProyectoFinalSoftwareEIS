package com.software.cliente.calculocientifico.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class AlgoritmosClientService {

    private final String apiUrl;
    private final RestTemplate restTemplate;

    @Autowired
    public AlgoritmosClientService(@Value("${algoritmos.api.url}") String apiUrl,
                                   RestTemplateBuilder restTemplateBuilder) {
        this.apiUrl = apiUrl;
        this.restTemplate = restTemplateBuilder.build();
    }

    public double calcularMontecarlo(int puntos, int hilos) {
        String url = apiUrl + "/montecarlo?puntos={puntos}&hilos={hilos}";
        return restTemplate.getForObject(url, Double.class, puntos, hilos);
    }

    public int[][] multiplicarMatrices(int[][] matrizA, int[][] matrizB, int hilos) {
        String url = apiUrl + "/multiplicar?hilos={hilos}";

        Map<String, int[][]> requestBody = new HashMap<>();
        requestBody.put("A", matrizA);
        requestBody.put("B", matrizB);

        return restTemplate.postForObject(url, requestBody, int[][].class, hilos);
    }
}