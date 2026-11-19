package com.example.gsjava;

import excecao.DadosInvalidosException;

import java.time.LocalDateTime;

/** Uma leitura de sensor num instante. Só existe se for válida. */
public class Medicao {

    private final Long id;
    private final String sensorId;
    private final String tipo;
    private final double valor;
    private final String unidade;
    private final LocalDateTime dataHora;

    /** Construtor usado ao ler um sensor: valida os dados. */
    public Medicao(String sensorId, String tipo, double valor, String unidade) throws DadosInvalidosException {
        if (sensorId == null || sensorId.isBlank()) {
            throw new DadosInvalidosException("A medição precisa informar o sensor de origem.");
        }
        if (Double.isNaN(valor) || Double.isInfinite(valor)) {
            throw new DadosInvalidosException("Medição de " + tipo + " inválida: o valor não é um número.");
        }
        if (valor < 0) {
            throw new DadosInvalidosException("Medição de " + tipo + " inválida: valor negativo (" + valor + ").");
        }
        this.id = null;
        this.sensorId = sensorId;
        this.tipo = tipo;
        this.valor = valor;
        this.unidade = unidade;
        this.dataHora = LocalDateTime.now();
    }

    /** Construtor usado pelo DAO ao reconstruir uma medição que já estava no banco. */
    public Medicao(Long id, String sensorId, String tipo, double valor, String unidade, LocalDateTime dataHora) {
        this.id = id;
        this.sensorId = sensorId;
        this.tipo = tipo;
        this.valor = valor;
        this.unidade = unidade;
        this.dataHora = dataHora;
    }

    public Long getId() { return id; }
    public String getSensorId() { return sensorId; }
    public String getTipo() { return tipo; }
    public double getValor() { return valor; }
    public String getUnidade() { return unidade; }
    public LocalDateTime getDataHora() { return dataHora; }
}
