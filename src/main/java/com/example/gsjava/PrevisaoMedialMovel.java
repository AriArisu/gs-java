package com.example.gsjava;

import excecao.DadosInvalidosException;

import java.util.List;

public class PrevisaoMedialMovel extends EstrategiaPrevisao {

    private final int janela;

    public PrevisaoMedialMovel(int janela, int minimoLeituras) throws DadosInvalidosException {
        super("Média móvel", Math.max(janela, minimoLeituras));
        if (janela < 1) {
            throw new DadosInvalidosException("A janela da média móvel deve ser de pelo menos 1 leitura.");
        }
        this.janela = janela;
    }

    @Override
    public double prever(List<Double> historico) throws DadosInvalidosException {
        super.prever(historico);
        int n = Math.min(janela, historico.size());
        double soma = 0;
        for (int i = historico.size() - n; i < historico.size(); i++) {
            soma += historico.get(i);
        }
        return soma / n;
    }

    @Override
    public String descrever() {
        return "média das últimas " + janela + " leituras";
    }
}
