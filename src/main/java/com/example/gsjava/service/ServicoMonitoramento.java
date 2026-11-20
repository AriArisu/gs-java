package com.example.gsjava.service;

import com.example.gsjava.excecao.DadosInvalidosException;
import com.example.gsjava.model.Alerta;
import com.example.gsjava.model.EstrategiaPrevisao;
import com.example.gsjava.model.Limiares;
import com.example.gsjava.model.Medicao;
import com.example.gsjava.model.NivelAlerta;
import com.example.gsjava.model.PrevisaoComparacao;
import com.example.gsjava.model.PrevisaoMediaMovel;
import com.example.gsjava.model.Sensor;
import com.example.gsjava.model.SensorNivelCorrego;
import com.example.gsjava.model.SensorPluviometro;
import com.example.gsjava.model.SensorUmidadeSolo;
import com.example.gsjava.repository.AlertaRepository;
import com.example.gsjava.repository.MedicaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServicoMonitoramento {

    @Autowired
    private MedicaoRepository medicaoRepository;

    @Autowired
    private AlertaRepository alertaRepository;

    private List<Sensor> sensores = new ArrayList<>();

    public ServicoMonitoramento() {
        sensores.add(new SensorPluviometro("PLV-01", "Zona Sul"));
        sensores.add(new SensorNivelCorrego("NIV-01", "Zona Sul"));
        sensores.add(new SensorUmidadeSolo("UMI-01", "Zona Sul"));
    }

    public List<Sensor> listarSensores() {
        return sensores;
    }

    public Sensor buscarSensor(String idSensor) {
        for (Sensor sensor : sensores) {
            if (sensor.getId().equalsIgnoreCase(idSensor.trim())) {
                return sensor;
            }
        }
        throw new DadosInvalidosException("Sensor não encontrado: '" + idSensor + "'.");
    }

    public Medicao registrarLeitura(Sensor sensor, double valor) {
        if (valor < 0 || valor > sensor.getValorMaximo()) {
            throw new DadosInvalidosException("Leitura de " + sensor.getId() + " fora da faixa (" + valor + " "
                    + sensor.getUnidade() + "): este sensor mede de 0 a " + sensor.getValorMaximo() + " "
                    + sensor.getUnidade() + ".");
        }
        Medicao medicao = new Medicao(sensor.getId(), sensor.retornarTipo(), valor, sensor.getUnidade());
        medicaoRepository.save(medicao);
        return medicao;
    }

    public List<Medicao> historicoPorSensor(String idSensor) {
        return medicaoRepository.findBySensorIdOrderByIdAsc(idSensor);
    }

    public List<Alerta> listarAlertas() {
        return alertaRepository.findAll();
    }

    public List<String> preverEAlertar(Sensor sensor) {
        ConfiguracaoSistema configuracao = ConfiguracaoSistema.getInstance();
        Limiares limiares = configuracao.getTerritorio().getLimiares(sensor.retornarTipo());

        List<Double> historico = new ArrayList<>();
        for (Medicao medicao : historicoPorSensor(sensor.getId())) {
            historico.add(medicao.getValor());
        }

        List<EstrategiaPrevisao> estrategias = List.of(
                new PrevisaoMediaMovel(configuracao.getJanela(), configuracao.getMinimoLeituras()),
                new PrevisaoComparacao(configuracao.getJanela(), configuracao.getMinimoLeituras()));

        List<String> resultados = new ArrayList<>();
        for (EstrategiaPrevisao estrategia : estrategias) {
            resultados.add(avaliar(sensor, estrategia, historico, limiares));
        }
        return resultados;
    }

    private String avaliar(Sensor sensor, EstrategiaPrevisao estrategia, List<Double> historico, Limiares limiares) {
        double previsto;
        try {
            previsto = estrategia.prever(historico);
        } catch (DadosInvalidosException e) {
            return estrategia.getNome() + ": sem previsão. " + e.getMessage();
        }

        String resumo = estrategia.getNome() + ": previsto " + arredondar(previsto) + " " + sensor.getUnidade() + ".";

        NivelAlerta nivel = limiares.classificar(previsto);
        if (nivel == null) {
            return resumo + " Abaixo do primeiro limite (ATENÇÃO = " + arredondar(limiares.getAtencao()) + " "
                    + sensor.getUnidade() + "): nenhum alerta.";
        }

        double limite = limiares.limitePara(nivel);
        String mensagem = "[" + nivel + "] Sensor " + sensor.getId() + " (" + sensor.getNome() + "): "
                + resumo + " Isso atinge o limite de " + nivel + " (" + arredondar(limite) + " "
                + sensor.getUnidade() + "). A decisão final é da equipe: confirme em campo antes de agir.";
        alertaRepository.save(new Alerta(sensor.getId(), nivel.name(), mensagem));
        return mensagem;
    }

    private double arredondar(double valor) {
        return Math.round(valor * 100) / 100.0;
    }
}
