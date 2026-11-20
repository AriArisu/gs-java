package com.example.gsjava.model;


import java.util.List;

public class PrevisaoComparacao extends EstrategiaPrevisao {

    private int janela;

    public PrevisaoComparacao(int janela, int minimoLeituras) {
        super("Comparação com período anterior (" + janela + " leituras cada)", minimoLeituras);
        this.janela = janela;
    }

    @Override
    public double prever(List<Double> historico) {
        double ultima = super.prever(historico); // valida o histórico e devolve a última leitura
        int total = historico.size();
        int n = Math.min(janela, total / 2);
        double mediaRecente = media(historico, total - n, total);
        double mediaAnterior = media(historico, total - 2 * n, total - n);
        // Chuva, nível e umidade nunca são negativos.
        return Math.max(0, ultima + (mediaRecente - mediaAnterior));
    }

    private double media(List<Double> lista, int inicio, int fim) {
        double soma = 0;
        for (int i = inicio; i < fim; i++) {
            soma = soma + lista.get(i);
        }
        return soma / (fim - inicio);
    }
}
