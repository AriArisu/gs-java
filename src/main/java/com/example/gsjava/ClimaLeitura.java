package com.example.gsjava;

public class ClimaLeitura extends ComponenteTerritorial{
    public ClimaLeitura(int id, String nome, String regiao, String status) {
        super(nome, regiao, status);
    }

    @Override
    public String verificarStatus() {
        return "";
    }
}
