package com.example.gsjava;

import com.example.gsjava.ComponenteTerritorial;
import com.example.gsjava.Sensor;
import excecao.DadosInvalidosException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Ponto vulnerável do território (ex.: trecho do córrego que costuma transbordar) com seus sensores. */
public class PontoMonitorado extends ComponenteTerritorial {

    private final List<Sensor> sensores = new ArrayList<>();

    public PontoMonitorado(String id, String nome, String regiao) throws DadosInvalidosException {
        super(id, nome, regiao);
    }

    public void adicionarSensor(Sensor sensor) throws DadosInvalidosException {
        if (sensor == null) {
            throw new DadosInvalidosException("Não é possível adicionar um sensor nulo ao ponto.");
        }
        sensores.add(sensor);
    }

    public List<Sensor> getSensores() {
        return Collections.unmodifiableList(sensores);
    }

    @Override
    public String verificarStatus() {
        if (sensores.isEmpty()) {
            setStatus("SEM SENSORES");
        } else {
            long funcionando = sensores.stream().filter(Sensor::verificarFuncionamento).count();
            if (funcionando == sensores.size()) {
                setStatus("OPERANTE");
            } else if (funcionando == 0) {
                setStatus("FORA DE OPERAÇÃO");
            } else {
                setStatus("DEGRADADO (" + funcionando + "/" + sensores.size() + " sensores ok)");
            }
        }
        return getStatus();
    }
}
