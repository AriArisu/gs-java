package com.example.gsjava;

import excecao.DadosInvalidosException;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "medicao")
public class Medicao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sensor_id", nullable = false, length = 30)
    private String sensorId;

    @Column(name = "tipo", nullable = false, length = 30)
    private String tipo;

    @Column(name = "valor", nullable = false)
    private double valor;

    @Column(name = "unidade", nullable = false, length = 10)
    private String unidade;

    @Column(name = "data_hora", nullable = false)
    private LocalDateTime dataHora;

    protected Medicao() {
    }

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
        this.sensorId = sensorId;
        this.tipo = tipo;
        this.valor = valor;
        this.unidade = unidade;
        this.dataHora = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public String getSensorId() { return sensorId; }
    public String getTipo() { return tipo; }
    public double getValor() { return valor; }
    public String getUnidade() { return unidade; }
    public LocalDateTime getDataHora() { return dataHora; }
}