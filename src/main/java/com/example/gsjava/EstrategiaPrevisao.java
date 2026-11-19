package com.example.gsjava;

import excecao.DadosInvalidosException;

import java.util.List;

public abstract class EstrategiaPrevisao {

    private final String nome;
    private final int minimoLeituras;

    protected EstrategiaPrevisao(String nome, int minimoLeituras) {
        this.nome = nome;
        this.minimoLeituras = minimoLeituras;
    }

    public double prever(List<Double> historico) throws DadosInvalidosException {
        if (historico == null || historico.size() < minimoLeituras) {
            int qtd = historico == null ? 0 : historico.size();
            throw new DadosInvalidosException("Histórico insuficiente para a estratégia '" + nome + "': há "
                    + qtd + " leitura(s) e são necessárias pelo menos " + minimoLeituras
                    + ". O sistema prefere não prever a chutar.");
        }
        return historico.get(historico.size() - 1);
    }

    /** Explica em uma frase como a estratégia calcula. */
    public abstract String descrever();

    public String getNome() {
        return nome;
    }

    public int getMinimoLeituras() {
        return minimoLeituras;
    }
}