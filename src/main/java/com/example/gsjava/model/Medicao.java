package com.example.gsjava.model;

import com.example.gsjava.excecao.DadosInvalidosException;
import jakarta.persistence.*;

@Entity
@Table(name = "medicoes")
public class Medicao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sensorId;
    private String tipo;
    private double valor;
    private String unidade;

    public Medicao() { }

    public Medicao(String sensorId, String tipo, double valor, String unidade) {
        if (sensorId == null || sensorId.isBlank()) {
            throw new DadosInvalidosException("A medição precisa informar o sensor de origem.");
        }
        this.sensorId = sensorId;
        this.tipo = tipo;
        this.unidade = unidade;
        setValor(valor);
    }

    public Long getId() { return id; }
    public String getSensorId() { return sensorId; }
    public String getTipo() { return tipo; }
    public String getUnidade() { return unidade; }
    public double getValor() { return valor; }

    public void setValor(double valor) {
        if (valor < 0) {
            throw new DadosInvalidosException("Medição de " + tipo + " inválida: valor negativo (" + valor + ").");
        }
        this.valor = valor;
    }
}
