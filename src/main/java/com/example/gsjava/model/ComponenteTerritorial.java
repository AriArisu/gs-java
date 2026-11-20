package com.example.gsjava.model;

import com.example.gsjava.excecao.DadosInvalidosException;

public abstract class ComponenteTerritorial {

    private String id;
    private String nome;
    private String regiao;
    private String status = "NAO VERIFICADO";

    public ComponenteTerritorial(String id, String nome, String regiao) {
        if (id == null || id.isBlank()) {
            throw new DadosInvalidosException("O componente precisa de um identificador.");
        }
        this.id = id;
        setNome(nome);
        setRegiao(regiao);
    }

    public abstract String verificarStatus();

    public String getId() { return id; }
    public String getNome() { return nome; }

    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new DadosInvalidosException("O nome do componente não pode ser vazio.");
        }
        this.nome = nome;
    }

    public String getRegiao() { return regiao; }

    public void setRegiao(String regiao) {
        if (regiao == null || regiao.isBlank()) {
            throw new DadosInvalidosException("A região do componente não pode ser vazia.");
        }
        this.regiao = regiao;
    }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
