package com.example.gsjava.model;

import com.example.gsjava.excecao.FonteIndisponivelException;

public interface Sensor {

    String getId();

    String getNome();

    String getUnidade();

    double getValorMaximo();

    double lerValor();

    boolean verificarFuncionamento();

    String verificarStatus();

    String retornarTipo();
}
