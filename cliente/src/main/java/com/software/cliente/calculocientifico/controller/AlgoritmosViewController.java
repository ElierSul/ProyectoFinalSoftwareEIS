package com.software.cliente.calculocientifico.controller;

import com.software.cliente.calculocientifico.dto.MatricesForm;
import com.software.cliente.calculocientifico.dto.MontecarloForm;
import com.software.cliente.calculocientifico.service.AlgoritmosClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/algoritmos")
public class AlgoritmosViewController {

    private final AlgoritmosClientService algoritmosClientService;

    @Autowired
    public AlgoritmosViewController(AlgoritmosClientService algoritmosClientService) {
        this.algoritmosClientService = algoritmosClientService;
    }

    @GetMapping("/montecarlo")
    public String mostrarFormularioMontecarlo(Model model) {
        model.addAttribute("montecarloForm", new MontecarloForm());
        return "montecarlo";
    }

    @PostMapping("/montecarlo")
    public String calcularMontecarlo(@ModelAttribute MontecarloForm form, Model model) {
        double resultado = algoritmosClientService.calcularMontecarlo(form.getPuntos(), form.getHilos());
        model.addAttribute("resultado", resultado);
        model.addAttribute("montecarloForm", form);
        return "montecarlo";
    }

    @GetMapping("/matrices")
    public String mostrarFormularioMatrices(Model model) {
        model.addAttribute("matricesForm", new MatricesForm());
        return "matrices";
    }

    @PostMapping("/matrices")
    public String calcularMatrices(@ModelAttribute MatricesForm form, Model model) {
        int[][] matrizA = convertirStringAMatriz(form.getMatrizA());
        int[][] matrizB = convertirStringAMatriz(form.getMatrizB());

        int[][] resultado = algoritmosClientService.multiplicarMatrices(matrizA, matrizB, form.getHilos());

        model.addAttribute("resultado", convertirMatrizAString(resultado));
        model.addAttribute("matricesForm", form);
        return "matrices";
    }

    private int[][] convertirStringAMatriz(String matrizStr) {
        String[] filas = matrizStr.split(";");
        int[][] matriz = new int[filas.length][];

        for (int i = 0; i < filas.length; i++) {
            String[] valores = filas[i].trim().split("\\s+");
            matriz[i] = new int[valores.length];
            for (int j = 0; j < valores.length; j++) {
                matriz[i][j] = Integer.parseInt(valores[j]);
            }
        }
        return matriz;
    }

    private String convertirMatrizAString(int[][] matriz) {
        StringBuilder sb = new StringBuilder();
        for (int[] fila : matriz) {
            for (int valor : fila) {
                sb.append(valor).append(" ");
            }
            sb.append(";");
        }
        return sb.toString();
    }
}