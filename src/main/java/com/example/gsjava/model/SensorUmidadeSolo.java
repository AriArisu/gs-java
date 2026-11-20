package com.example.gsjava.model;

import com.example.gsjava.excecao.FonteIndisponivelException;

public class SensorUmidadeSolo extends ComponenteTerritorial implements Sensor {

    public SensorUmidadeSolo(String id, String regiao) {
        super(id, "Umidade do solo", regiao);
    }

    @Override
    public String getUnidade() { return "%"; }

    @Override
    public double getValorMaximo() { return 100.0; }

    @Override
    public String retornarTipo() { return "UMIDADE_SOLO"; }

    @Override
    public boolean verificarFuncionamento() {
        return Math.random() < 0.9;
    }

    @Override
    public double lerValor() {
        if (!verificarFuncionamento()) {
            throw new FonteIndisponivelException("O sensor " + getId() + " (" + getNome() + ") não respondeu.");
        }
        return Math.random() * Math.random() * getValorMaximo();
    }

    @Override
    public String verificarStatus() {
        if (verificarFuncionamento()) {
            setStatus("OPERANTE");
        } else {
            setStatus("FORA DE OPERAÇÃO");
        }
        return getStatus();
    }
}
