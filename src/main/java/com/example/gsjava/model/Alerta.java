package com.example.gsjava.model;

import jakarta.persistence.*;

@Entity
@Table(name = "alertas")
public class Alerta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sensorId;
    private String nivel;

    @Column(length = 600)
    private String mensagem;

    public Alerta() { }

    public Alerta(String sensorId, String nivel, String mensagem) {
        this.sensorId = sensorId;
        this.nivel = nivel;
        this.mensagem = mensagem;
    }

    public Long getId() { return id; }
    public String getSensorId() { return sensorId; }
    public String getNivel() { return nivel; }
    public String getMensagem() { return mensagem; }
}
