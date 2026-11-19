package com.example.gsjava;

import excecao.FonteIndisponivelException;

/** Mede a intensidade da chuva em mm/h. */
public class SensorUmidadeSolo implements Sensor {


    @Override
    public double lerValor() {
        return sortearValor(0.0, 100.0);
    }

    @Override
    public boolean verificarFuncionamento() {
        return sortearFuncionamento(0.90);
    }

    @Override
    public String retornarTipo() {
        try {
            return sortearTipo("Resistivos", "Tensiometros", "Capacitivos");
        } catch (FonteIndisponivelException e) {
            return "Desconhecido";
        }
    }
}