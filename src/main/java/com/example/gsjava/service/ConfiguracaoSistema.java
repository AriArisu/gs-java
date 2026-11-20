package com.example.gsjava.service;

import com.example.gsjava.model.DadosTerritorio;

public class ConfiguracaoSistema {

    private static ConfiguracaoSistema instancia;

    private int janela = 3;
    private int minimoLeituras = 5;
    private DadosTerritorio territorio;

    private ConfiguracaoSistema() {
        territorio = new DadosTerritorio("Bairro de exemplo - Zona Sul", "Zona Sul", 12000);
        // Limiares de exemplo (não oficiais).
        territorio.definirLimiares("PLUVIOMETRO", 25, 50, 80);      // mm/h
        territorio.definirLimiares("NIVEL_CORREGO", 1.5, 2.5, 3.5);  // metros
        territorio.definirLimiares("UMIDADE_SOLO", 60, 80, 95);      // %
    }

    public static ConfiguracaoSistema getInstance() {
        if (instancia == null) {
            instancia = new ConfiguracaoSistema();
        }
        return instancia;
    }

    public int getJanela() { return janela; }

    public int getMinimoLeituras() { return minimoLeituras; }

    public DadosTerritorio getTerritorio() { return territorio; }
}
