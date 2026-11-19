package com.example.gsjava;

import com.example.gsjava.NivelAlerta;

import java.time.LocalDateTime;

/** Alerta emitido a partir de uma previsão que ultrapassou um limiar. */
public class Alerta {

    private final Long id;
    private final String sensorId;
    private final String tipo;
    private final NivelAlerta nivel;
    private final double valorPrevisto;
    private final double limite;
    private final String estrategia;
    private final String mensagem;
    private final LocalDateTime dataHora;

    public Alerta(Long id, String sensorId, String tipo, NivelAlerta nivel, double valorPrevisto,
                  double limite, String estrategia, String mensagem, LocalDateTime dataHora) {
        this.id = id;
        this.sensorId = sensorId;
        this.tipo = tipo;
        this.nivel = nivel;
        this.valorPrevisto = valorPrevisto;
        this.limite = limite;
        this.estrategia = estrategia;
        this.mensagem = mensagem;
        this.dataHora = dataHora;
    }

    public Long getId() { return id; }
    public String getSensorId() { return sensorId; }
    public String getTipo() { return tipo; }
    public NivelAlerta getNivel() { return nivel; }
    public double getValorPrevisto() { return valorPrevisto; }
    public double getLimite() { return limite; }
    public String getEstrategia() { return estrategia; }
    public String getMensagem() { return mensagem; }
    public LocalDateTime getDataHora() { return dataHora; }
}
