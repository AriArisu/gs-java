package com.example.gsjava;

import excecao.FonteIndisponivelException;

/** Mede o nível da água do córrego em centímetros. */
public class SensorNivelCorrego implements Sensor{

    @Override
    public double lerValor() {
        return sortearValor(0.0, 5.0);
    }

    @Override
    public boolean verificarFuncionamento() {
        return sortearFuncionamento(0.90);
    }

    @Override
    public String retornarTipo() {
        try {
            return sortearTipo("Regua Limetrica aluminio", "Regua Limetrica Inox", "Regua Limetrica Ceramica");
        } catch (FonteIndisponivelException e) {
            return "Desconhecido";
        }
    }
}
