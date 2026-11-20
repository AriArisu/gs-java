package com.example.gsjava.service;

import com.example.gsjava.excecao.DadosInvalidosException;
import com.example.gsjava.model.Alerta;
import com.example.gsjava.model.Medicao;
import com.example.gsjava.model.SensorPluviometro;
import com.example.gsjava.repository.AlertaRepository;
import com.example.gsjava.repository.MedicaoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ServicoMonitoramentoTest {

    @Mock
    private MedicaoRepository medicaoRepository;

    @Mock
    private AlertaRepository alertaRepository;

    @InjectMocks
    private ServicoMonitoramento servico;

    private SensorPluviometro pluviometro = new SensorPluviometro("PLV-01", "Zona Sul");

    private void historicoNoBanco(List<Double> valores) {
        List<Medicao> medicoes = new ArrayList<>();
        for (double valor : valores) {
            medicoes.add(new Medicao("PLV-01", "PLUVIOMETRO", valor, "mm/h"));
        }
        when(medicaoRepository.findBySensorIdOrderByIdAsc("PLV-01")).thenReturn(medicoes);
    }

    @Test
    void leituraValidaEGravadaNoRepositorio() {
        double valor = 42.0;

        Medicao gravada = servico.registrarLeitura(pluviometro, valor);

        verify(medicaoRepository).save(any(Medicao.class));
        assertEquals(42.0, gravada.getValor(), 0.0001);
    }

    @Test
    void leituraForaDaFaixaLancaExcecaoENaoGrava() {
        double valorImpossivel = 500.0;

        DadosInvalidosException erro = assertThrows(DadosInvalidosException.class,
                () -> servico.registrarLeitura(pluviometro, valorImpossivel));

        assertTrue(erro.getMessage().contains("fora da faixa"));
        verify(medicaoRepository, never()).save(any(Medicao.class));
    }

    @Test
    void previsaoQueAtingeLimiteGravaUmAlertaPorEstrategia() {
        historicoNoBanco(List.of(10.0, 10.0, 60.0, 60.0, 60.0));

        List<String> resultados = servico.preverEAlertar(pluviometro);

        verify(alertaRepository, times(2)).save(any(Alerta.class));
        assertEquals(2, resultados.size());
        assertTrue(resultados.get(0).contains("[ALERTA]"));
        assertTrue(resultados.get(0).contains("PLV-01"));
        assertTrue(resultados.get(1).contains("[CRITICO]"));
    }

    @Test
    void previsaoAbaixoDoPrimeiroLimiteNaoGeraAlerta() {
        historicoNoBanco(List.of(5.0, 5.0, 5.0, 5.0, 5.0));

        List<String> resultados = servico.preverEAlertar(pluviometro);

        verify(alertaRepository, never()).save(any(Alerta.class));
        assertTrue(resultados.get(0).contains("nenhum alerta"));
    }

    @Test
    void historicoCurtoFazOSistemaSeCalarSemGerarAlerta() {
        historicoNoBanco(List.of(90.0, 95.0));

        List<String> resultados = servico.preverEAlertar(pluviometro);

        verify(alertaRepository, never()).save(any(Alerta.class));
        assertTrue(resultados.get(0).contains("sem previsão"));
    }
}
