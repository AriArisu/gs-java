package com.example.gsjava;

import com.example.gsjava.excecao.DadosInvalidosException;
import com.example.gsjava.excecao.FonteIndisponivelException;
import com.example.gsjava.model.Alerta;
import com.example.gsjava.model.DadosTerritorio;
import com.example.gsjava.model.Limiares;
import com.example.gsjava.model.Medicao;
import com.example.gsjava.model.Sensor;
import com.example.gsjava.service.ConfiguracaoSistema;
import com.example.gsjava.service.ServicoMonitoramento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class SistemaMonitoramento implements CommandLineRunner {

    @Autowired
    private ServicoMonitoramento servico;

    private Scanner entrada;

    @Override
    public void run(String... args) {
        entrada = new Scanner(System.in);
        boolean rodando = true;

        System.out.println("=== Monitoramento Territorial: chuva intensa e risco de alagamento ===");

        while (rodando) {
            System.out.println();
            System.out.println("--- MENU ---");
            System.out.println("1) Verificar sensores");
            System.out.println("2) Rodar previsões");
            System.out.println("3) Ver histórico");
            System.out.println("4) Gerenciar dados do território");
            System.out.println("5) Status completo");
            System.out.println("0) Encerrar");
            String opcao = pedir("Escolha: ");

            try {
                if (opcao.equals("1")) {
                    verificarSensores();
                } else if (opcao.equals("2")) {
                    rodarPrevisoes();
                } else if (opcao.equals("3")) {
                    verHistorico();
                } else if (opcao.equals("4")) {
                    gerenciarTerritorio();
                } else if (opcao.equals("5")) {
                    statusCompleto();
                } else if (opcao.equals("0")) {
                    rodando = false;
                } else {
                    System.out.println("Opção inválida: '" + opcao + "'. Digite um número do menu.");
                }
            } catch (DadosInvalidosException e) {
                System.out.println("ERRO - Dado inválido: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("ERRO - Digite um número válido (use ponto nos decimais, ex.: 2.5).");
            }
        }

        System.out.println("Sistema encerrado. Os dados continuam salvos no banco.");
    }


    private void verificarSensores() {
        System.out.println();
        System.out.println("1) Ler todos os sensores agora (grava no banco)");
        System.out.println("2) Registrar uma leitura manual (para testar a validação)");
        String opcao = pedir("Escolha: ");

        if (opcao.equals("1")) {
            for (Sensor sensor : servico.listarSensores()) {
                try {
                    Medicao m = servico.registrarLeitura(sensor, sensor.lerValor());
                    System.out.println("  OK  " + sensor.getId() + " (" + sensor.getNome() + "): "
                            + arredondar(m.getValor()) + " " + m.getUnidade() + " - gravada");
                } catch (FonteIndisponivelException e) {
                    System.out.println("  ERRO " + sensor.getId() + ": " + e.getMessage() + " Nada foi gravado.");
                }
            }
        } else if (opcao.equals("2")) {
            listarIdsDosSensores();
            Sensor sensor = servico.buscarSensor(pedir("Id do sensor: "));
            double valor = Double.parseDouble(pedir("Valor lido (" + sensor.getUnidade() + "): "));
            Medicao m = servico.registrarLeitura(sensor, valor);
            System.out.println("  OK  Leitura gravada: " + sensor.getId() + " = " + arredondar(m.getValor()) + " " + m.getUnidade());
        } else {
            System.out.println("Opção inválida.");
        }
    }


    private void rodarPrevisoes() {
        System.out.println();
        System.out.println("--- Previsões (sobre o histórico gravado no banco) ---");
        for (Sensor sensor : servico.listarSensores()) {
            System.out.println();
            System.out.println("[" + sensor.getId() + "] " + sensor.getNome());
            for (String resultado : servico.preverEAlertar(sensor)) {
                System.out.println("  - " + resultado);
            }
        }
    }


    private void verHistorico() {
        System.out.println();
        System.out.println("1) Medições de um sensor");
        System.out.println("2) Alertas já emitidos");
        String opcao = pedir("Escolha: ");

        if (opcao.equals("1")) {
            listarIdsDosSensores();
            Sensor sensor = servico.buscarSensor(pedir("Id do sensor: "));
            List<Medicao> medicoes = servico.historicoPorSensor(sensor.getId());
            if (medicoes.isEmpty()) {
                System.out.println("Nenhuma medição gravada para " + sensor.getId() + " ainda. Use a opção 1 do menu.");
            }
            for (Medicao m : medicoes) {
                System.out.println("  #" + m.getId() + "  " + arredondar(m.getValor()) + " " + m.getUnidade());
            }
        } else if (opcao.equals("2")) {
            List<Alerta> alertas = servico.listarAlertas();
            if (alertas.isEmpty()) {
                System.out.println("Nenhum alerta emitido até agora.");
            }
            for (Alerta a : alertas) {
                System.out.println("  #" + a.getId() + "  " + a.getMensagem());
            }
        } else {
            System.out.println("Opção inválida.");
        }
    }


    private void gerenciarTerritorio() {
        DadosTerritorio territorio = ConfiguracaoSistema.getInstance().getTerritorio();

        System.out.println();
        System.out.println("1) Ver dados e limiares");
        System.out.println("2) Alterar população");
        System.out.println("3) Alterar limiares de um tipo de sensor");
        String opcao = pedir("Escolha: ");

        if (opcao.equals("1")) {
            mostrarTerritorio(territorio);
        } else if (opcao.equals("2")) {
            territorio.setPopulacao(Integer.parseInt(pedir("Nova população: ")));
            System.out.println("OK  População atualizada.");
        } else if (opcao.equals("3")) {
            System.out.println("Tipos disponíveis: " + territorio.getTodosLimiares().keySet());
            String tipo = pedir("Tipo do sensor: ").toUpperCase();
            territorio.getLimiares(tipo); // lança DadosInvalidosException se o tipo não existir
            double atencao = Double.parseDouble(pedir("Limite de ATENÇÃO: "));
            double alerta = Double.parseDouble(pedir("Limite de ALERTA: "));
            double critico = Double.parseDouble(pedir("Limite CRÍTICO: "));
            territorio.definirLimiares(tipo, atencao, alerta, critico);
            System.out.println("OK  Limiares de " + tipo + " atualizados.");
        } else {
            System.out.println("Opção inválida.");
        }
    }

    private void mostrarTerritorio(DadosTerritorio territorio) {
        System.out.println("Território: " + territorio.getNome() + " | região: " + territorio.getRegiao()
                + " | população: " + territorio.getPopulacao());
        System.out.println("Limiares por tipo de sensor:");
        for (String tipo : territorio.getTodosLimiares().keySet()) {
            Limiares l = territorio.getLimiares(tipo);
            System.out.println("  " + tipo + " -> ATENÇÃO=" + l.getAtencao() + ", ALERTA=" + l.getAlerta()
                    + ", CRÍTICO=" + l.getCritico());
        }
    }


    private void statusCompleto() {
        System.out.println();
        System.out.println("=== STATUS COMPLETO ===");
        mostrarTerritorio(ConfiguracaoSistema.getInstance().getTerritorio());
        for (Sensor sensor : servico.listarSensores()) {
            System.out.println("  " + sensor.getId() + " (" + sensor.getNome() + "): " + sensor.verificarStatus() + " | "
                    + servico.historicoPorSensor(sensor.getId()).size() + " medição(ões) gravada(s)");
        }
        System.out.println("Alertas emitidos: " + servico.listarAlertas().size());
    }


    private void listarIdsDosSensores() {
        System.out.print("Sensores: ");
        for (Sensor sensor : servico.listarSensores()) {
            System.out.print(sensor.getId() + " (" + sensor.getNome() + ")  ");
        }
        System.out.println();
    }

    private String pedir(String mensagem) {
        System.out.print(mensagem);
        return entrada.nextLine().trim();
    }

    private double arredondar(double valor) {
        return Math.round(valor * 100) / 100.0;
    }
}
