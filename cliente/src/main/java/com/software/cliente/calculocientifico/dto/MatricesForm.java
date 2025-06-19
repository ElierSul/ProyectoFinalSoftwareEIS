package com.software.cliente.calculocientifico.dto;

import lombok.Data;

@Data
public class MatricesForm {
    private String matrizA = "1 2;3 4";
    private String matrizB = "5 6;7 8";
    private int hilos = 2;
}
