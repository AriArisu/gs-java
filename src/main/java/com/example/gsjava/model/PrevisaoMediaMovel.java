package com.example.gsjava.model;


import java.util.List;

public class PrevisaoMediaMovel extends EstrategiaPrevisao {

    private int janela;

    public PrevisaoMediaMovel(int janela, int minimoLeituras) {
        super("Média móvel (últimas " + janela + " leituras)", minimoLeituras);
        this.janela = janela;
    }

    @Override
    public double prever(List<Double> historico) {
        super.prever(historico); // valida o histórico
        int n = Math.min(janela, historico.size());
        double soma = 0;
        for (int i = historico.size() - n; i < historico.size(); i++) {
            soma = soma + historico.get(i);
        }
        return soma / n;
    }
}
