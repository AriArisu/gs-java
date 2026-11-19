package com.example.gsjava;

import com.example.gsjava.Medicao;
import excecao.DadosInvalidosException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MedicaoTest {

    @Test
    void medicaoNegativaLancaDadosInvalidosException() {
        // Arrange
        double valorNegativo = -3.5;

        // Act + Assert
        DadosInvalidosException ex = assertThrows(DadosInvalidosException.class,
                () -> new Medicao("PLV-01", "PLUVIOMETRO", valorNegativo, "mm/h"));
        assertTrue(ex.getMessage().contains("negativo"));
    }

    @Test
    void medicaoValidaGuardaOsDadosInformados() throws Exception, DadosInvalidosException {
        // Arrange + Act
        Medicao m = new Medicao("PLV-01", "PLUVIOMETRO", 12.5, "mm/h");

        // Assert
        assertEquals("PLV-01", m.getSensorId());
        assertEquals(12.5, m.getValor(), 0.0001);
        assertTrue(m.getDataHora() != null);
    }

    @Test
    void medicaoSemSensorLancaDadosInvalidosException() {
        assertThrows(DadosInvalidosException.class, () -> new Medicao(" ", "PLUVIOMETRO", 10, "mm/h"));
    }
}
