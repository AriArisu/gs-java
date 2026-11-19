package com.example.gsjava;

import excecao.DadosInvalidosException;

import java.util.List;

public class PrevisaoComparacao extends EstrategiaPrevisao {

    private final int janela;

    public PrevisaoComparacao(int janela, int minimoLeituras) throws DadosInvalidosException {
        super("Comparação com período anterior", Math.max(2, minimoLeituras));
        if (janela < 1) {
            throw new DadosInvalidosException("A janela da comparação deve ser de pelo menos 1 leitura.");
        }
        this.janela = janela;
    }

    @Override
    public double prever(List<Double> historico) throws DadosInvalidosException {
        double ultima = super.prever(historico); // valida e devolve a última leitura
        int n = Math.min(janela, historico.size() / 2);
        int fim = historico.size();
        double mediaRecente = media(historico, fim - n, fim);
        double mediaAnterior = media(historico, fim - 2 * n, fim - n);
        return Math.max(0, ultima + (mediaRecente - mediaAnterior));
    }

    private double media(List<Double> lista, int de, int ate) {
        double soma = 0;
        for (int i = de; i < ate; i++) {
            soma += lista.get(i);
        }
        return soma / (ate - de);
    }

    @Override
    public String descrever() {
        return "última leitura + (média das últimas " + janela + " - média das " + janela + " anteriores)";
    }
}
