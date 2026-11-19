package com.example.gsjava;

import com.example.gsjava.NivelAlerta;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "alerta")
public class Alerta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sensor_id", nullable = false, length = 30)
    private String sensorId;

    @Column(name = "tipo", nullable = false, length = 30)
    private String tipo;

    @Enumerated(EnumType.STRING)
    @Column(name = "nivel", nullable = false, length = 10)
    private NivelAlerta nivel;

    @Column(name = "valor_previsto", nullable = false)
    private double valorPrevisto;

    @Column(name = "limite", nullable = false)
    private double limite;

    @Column(name = "estrategia", nullable = false, length = 60)
    private String estrategia;

    @Column(name = "mensagem", nullable = false, length = 800)
    private String mensagem;

    @Column(name = "data_hora", nullable = false)
    private LocalDateTime dataHora;

    protected Alerta() {
    }

    public Alerta(String sensorId, String tipo, NivelAlerta nivel, double valorPrevisto,
                  double limite, String estrategia, String mensagem) {
        this.sensorId = sensorId;
        this.tipo = tipo;
        this.nivel = nivel;
        this.valorPrevisto = valorPrevisto;
        this.limite = limite;
        this.estrategia = estrategia;
        this.mensagem = mensagem;
        this.dataHora = LocalDateTime.now();
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