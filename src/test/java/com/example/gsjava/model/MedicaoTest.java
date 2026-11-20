package com.example.gsjava.model;

import com.example.gsjava.excecao.DadosInvalidosException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MedicaoTest {

    @Test
    void medicaoNegativaLancaDadosInvalidosException() {
        double valorNegativo = -3.5;

        DadosInvalidosException erro = assertThrows(DadosInvalidosException.class,
                () -> new Medicao("PLV-01", "PLUVIOMETRO", valorNegativo, "mm/h"));

        assertTrue(erro.getMessage().contains("negativo"));
    }

    @Test
    void medicaoValidaGuardaOsDadosInformados() {
        double valor = 12.5;

        Medicao medicao = new Medicao("PLV-01", "PLUVIOMETRO", valor, "mm/h");

        assertEquals("PLV-01", medicao.getSensorId());
        assertEquals(12.5, medicao.getValor(), 0.0001);
    }

    @Test
    void medicaoSemSensorLancaDadosInvalidosException() {
        String sensorVazio = " ";

        assertThrows(DadosInvalidosException.class, () -> new Medicao(sensorVazio, "PLUVIOMETRO", 10, "mm/h"));
    }
}
