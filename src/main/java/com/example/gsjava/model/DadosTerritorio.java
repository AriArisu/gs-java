package com.example.gsjava.model;

import com.example.gsjava.excecao.DadosInvalidosException;

import java.util.HashMap;
import java.util.Map;

public class DadosTerritorio {

    private String nome;
    private String regiao;
    private int populacao;
    private Map<String, Limiares> limiares = new HashMap<>();

    public DadosTerritorio(String nome, String regiao, int populacao) {
        setNome(nome);
        setRegiao(regiao);
        setPopulacao(populacao);
    }

    public String getNome() { return nome; }

    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new DadosInvalidosException("O nome do território não pode ser vazio.");
        }
        this.nome = nome;
    }

    public String getRegiao() { return regiao; }

    public void setRegiao(String regiao) {
        if (regiao == null || regiao.isBlank()) {
            throw new DadosInvalidosException("A região do território não pode ser vazia.");
        }
        this.regiao = regiao;
    }

    public int getPopulacao() { return populacao; }

    public void setPopulacao(int populacao) {
        if (populacao < 0) {
            throw new DadosInvalidosException("População inválida (" + populacao + "): não pode ser negativa.");
        }
        this.populacao = populacao;
    }

    public void definirLimiares(String tipoSensor, double atencao, double alerta, double critico) {
        limiares.put(tipoSensor, new Limiares(atencao, alerta, critico));
    }

    public Limiares getLimiares(String tipoSensor) {
        Limiares encontrados = limiares.get(tipoSensor);
        if (encontrados == null) {
            throw new DadosInvalidosException("Não há limiares para o tipo de sensor '" + tipoSensor
                    + "'. Tipos disponíveis: " + limiares.keySet() + ".");
        }
        return encontrados;
    }

    public Map<String, Limiares> getTodosLimiares() { return limiares; }
}
