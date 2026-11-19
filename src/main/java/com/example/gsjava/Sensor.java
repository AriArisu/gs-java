package com.example.gsjava;

import excecao.FonteIndisponivelException;
import java.util.concurrent.ThreadLocalRandom;

public interface Sensor {
    double lerValor();
    boolean verificarFuncionamento();
    String retornarTipo();

    // Sorteia um valor decimal dentro de [min, max)
    default double sortearValor(double min, double max) {
        return ThreadLocalRandom.current().nextDouble(min, max);
    }

    // Sorteia se o sensor está funcionando com base numa chance (ex.: 0.85 = 85%)
    default boolean sortearFuncionamento(double chanceOperacional) {
        return ThreadLocalRandom.current().nextDouble() < chanceOperacional;
    }

    // Sorteia um tipo a partir de um conjunto de opções
    default String sortearTipo(String... opcoes) throws FonteIndisponivelException {
        if (opcoes == null || opcoes.length == 0) {
            throw new FonteIndisponivelException("Nenhum tipo de sensor disponível para sorteio.");
        }
        int indice = ThreadLocalRandom.current().nextInt(opcoes.length);
        return opcoes[indice];
    }
}