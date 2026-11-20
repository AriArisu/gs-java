package com.example.gsjava.model;

import com.example.gsjava.excecao.FonteIndisponivelException;

public class SensorPluviometro extends ComponenteTerritorial implements Sensor {

    public SensorPluviometro(String id, String regiao) {
        super(id, "Pluviômetro", regiao);
    }

    @Override
    public String getUnidade() { return "mm/h"; }

    @Override
    public double getValorMaximo() { return 120.0; }

    @Override
    public String retornarTipo() { return "PLUVIOMETRO"; }

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
