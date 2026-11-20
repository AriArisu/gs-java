package com.example.gsjava.model;

import com.example.gsjava.excecao.FonteIndisponivelException;

public class SensorNivelCorrego extends ComponenteTerritorial implements Sensor {

    public SensorNivelCorrego(String id, String regiao) {
        super(id, "Nível do córrego", regiao);
    }

    @Override
    public String getUnidade() { return "m"; }

    @Override
    public double getValorMaximo() { return 5.0; }

    @Override
    public String retornarTipo() { return "NIVEL_CORREGO"; }

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
