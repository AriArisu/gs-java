package com.example.gsjava.model;

import com.example.gsjava.excecao.DadosInvalidosException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PrevisaoTest {

    @Test
    void mediaMovelCalculaAMediaDasUltimasNLeituras() {
        EstrategiaPrevisao estrategia = new PrevisaoMediaMovel(3, 5);
        List<Double> historico = List.of(10.0, 20.0, 30.0, 40.0, 50.0);

        double previsto = estrategia.prever(historico);

        assertEquals(40.0, previsto, 0.0001);
    }

    @Test
    void comparacaoSomaATendenciaAUltimaLeitura() {
        EstrategiaPrevisao estrategia = new PrevisaoComparacao(3, 5);
        List<Double> historico = List.of(10.0, 10.0, 10.0, 20.0, 20.0, 20.0);

        double previsto = estrategia.prever(historico);

        assertEquals(30.0, previsto, 0.0001);
    }

    @Test
    void historicoCurtoLancaExcecaoEmVezDeChutar() {
        EstrategiaPrevisao estrategia = new PrevisaoMediaMovel(3, 5);
        List<Double> historicoCurto = List.of(10.0, 20.0);

        assertThrows(DadosInvalidosException.class, () -> estrategia.prever(historicoCurto));
    }
}
