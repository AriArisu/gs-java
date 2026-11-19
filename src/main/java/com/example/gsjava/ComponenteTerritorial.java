package com.example.gsjava;

import excecao.DadosInvalidosException;
import java.util.Random;

public abstract class ComponenteTerritorial {

    protected int id;
    protected String nome;
    protected String regiao;
    protected String status;

    protected static final Random RANDOM = new Random();

    private static final String[] NOMES_PADRAO = {
            "Estação Costeira", "Posto de Monitoramento", "Base de Observação",
            "Unidade Hidrológica", "Ponto de Controle"
    };

    private static final String[] REGIOES_PADRAO = {
            "Norte", "Sul", "Leste", "Oeste", "Centro-Oeste", "Litoral"
    };

    private static final String[] STATUS_PADRAO = {
            "OPERACIONAL", "ALERTA", "MANUTENCAO", "INATIVO"
    };

    public ComponenteTerritorial(String nome, String regiao, String status) {
        this.id = id;
        this.nome = nome;
        this.regiao = regiao;
        this.status = status;
    }

    public static int gerarIdAleatorio() {
        return RANDOM.nextInt(9000) + 1000; // Gera entre 1000 e 9999
    }

    public static String sortearNome() {
        return NOMES_PADRAO[RANDOM.nextInt(NOMES_PADRAO.length)];
    }

    public static String sortearRegiao() {
        return REGIOES_PADRAO[RANDOM.nextInt(REGIOES_PADRAO.length)];
    }

    public static String sortearStatus() {
        return STATUS_PADRAO[RANDOM.nextInt(STATUS_PADRAO.length)];
    }

    public void randomizarAtributos() throws DadosInvalidosException {
        this.id = gerarIdAleatorio();
        setNome(sortearNome() + " #" + RANDOM.nextInt(100));
        setRegiao(sortearRegiao());
        this.status = sortearStatus();
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getRegiao() { return regiao; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public abstract String verificarStatus();

    public void setNome(String nome) throws DadosInvalidosException {
        if (nome == null || nome.isBlank()) {
            throw new DadosInvalidosException("O nome do componente não pode ser vazio.");
        }
        this.nome = nome.trim();
    }

    public void setRegiao(String regiao) throws DadosInvalidosException {
        if (regiao == null || regiao.isBlank()) {
            throw new DadosInvalidosException("A região do componente não pode ser vazia.");
        }
        this.regiao = regiao.trim();
    }
}