package com.example.gsjava;

import excecao.FonteIndisponivelException;

public class SensorPluviometro implements Sensor {

    @Override
    public double lerValor() {
        return sortearValor(0.0, 120.0); // ex.: mm de chuva entre 0 e 120
    }

    @Override
    public boolean verificarFuncionamento() {
        return sortearFuncionamento(0.90); // 90% de chance de estar operacional
    }

    @Override
    public String retornarTipo() {
        try {
            return sortearTipo("Pluviômetro Óptico", "Pluviômetro Báscula", "Sensor de Chuva");
        } catch (FonteIndisponivelException e) {
            return "Desconhecido";
        }
    }
}