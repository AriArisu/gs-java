package servico;


import com.example.gsjava.DadosTerritorio;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ServicoMonitoramento {

    private final DadosTerritorio territorio;
    private final List<RegistroLeitura> historicoLeituras;

    public ServicoMonitoramento(DadosTerritorio territorio) {
        this.territorio = Objects.requireNonNull(territorio, "O território não pode ser nulo.");
        this.historicoLeituras = new ArrayList<>();
    }

    public DadosTerritorio getTerritorio() {
        return territorio;
    }

    public List<RegistroLeitura> getHistoricoLeituras() {
        return Collections.unmodifiableList(historicoLeituras);
    }

    // Registra uma leitura e avalia o status em relação aos limiares do território
    public StatusLeitura registrarLeitura(double valorLeitura) {
        if (Double.isNaN(valorLeitura) || Double.isInfinite(valorLeitura)) {
            throw new IllegalArgumentException("Valor de leitura inválido.");
        }

        StatusLeitura status = avaliarValor(valorLeitura);
        historicoLeituras.add(new RegistroLeitura(LocalDateTime.now(), valorLeitura, status));
        return status;
    }

    // Avalia o valor sem alterar o histórico
    public StatusLeitura avaliarValor(double valor) {
        if (valor < territorio.getLimiarMinimo()) {
            return StatusLeitura.ABAIXO_DO_LIMIAR;
        }
        if (valor > territorio.getLimiarMaximo()) {
            return StatusLeitura.ACIMA_DO_LIMIAR;
        }
        return StatusLeitura.NORMAL;
    }

    // Atualiza os limiares delegando a validação do código de acesso para DadosTerritorio
    public boolean atualizarLimiares(String codigoAcesso, double novoMinimo, double novoMaximo) {
        return territorio.alterarLimiares(codigoAcesso, novoMinimo, novoMaximo);
    }

    public enum StatusLeitura {
        ABAIXO_DO_LIMIAR,
        NORMAL,
        ACIMA_DO_LIMIAR
    }

    public static class RegistroLeitura {
        private final LocalDateTime dataHora;
        private final double valor;
        private final StatusLeitura status;

        public RegistroLeitura(LocalDateTime dataHora, double valor, StatusLeitura status) {
            this.dataHora = dataHora;
            this.valor = valor;
            this.status = status;
        }

        public LocalDateTime getDataHora() {
            return dataHora;
        }

        public double getValor() {
            return valor;
        }

        public StatusLeitura getStatus() {
            return status;
        }

        @Override
        public String toString() {
            return "RegistroLeitura{" +
                    "dataHora=" + dataHora +
                    ", valor=" + valor +
                    ", status=" + status +
                    '}';
        }
    }
}