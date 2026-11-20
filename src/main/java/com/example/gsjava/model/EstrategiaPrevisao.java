package com.example.gsjava.model;

import com.example.gsjava.excecao.DadosInvalidosException;

import java.util.List;


public abstract class EstrategiaPrevisao {

    private String nome;
    private int minimoLeituras;

    public EstrategiaPrevisao(String nome, int minimoLeituras) {
        this.nome = nome;
        this.minimoLeituras = minimoLeituras;
    }

    public double prever(List<Double> historico) {
        if (historico.size() < minimoLeituras) {
            throw new DadosInvalidosException("Histórico insuficiente: há " + historico.size()
                    + " leitura(s) e são necessárias pelo menos " + minimoLeituras + ". Com tão pouco histórico, prever seria chutar.");
        }
        return historico.get(historico.size() - 1);
    }

    public String getNome() { return nome; }
}
