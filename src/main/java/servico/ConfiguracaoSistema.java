package servico;

import excecao.DadosInvalidosException;

public final class ConfiguracaoSistema {

    private static ConfiguracaoSistema instancia;

    private int janela = 3;
    private int minimoLeituras = 5;

    private ConfiguracaoSistema() {
    }

    public static synchronized ConfiguracaoSistema getInstance() {
        if (instancia == null) {
            instancia = new ConfiguracaoSistema();
        }
        return instancia;
    }

    public int getJanela() {
        return janela;
    }

    public void setJanela(int janela) throws DadosInvalidosException {
        if (janela < 1 || janela > 20) {
            throw new DadosInvalidosException("Janela inválida (" + janela + "): use um valor entre 1 e 20.");
        }
        this.janela = janela;
    }

    public int getMinimoLeituras() {
        return minimoLeituras;
    }

    public void setMinimoLeituras(int minimoLeituras) throws DadosInvalidosException {
        if (minimoLeituras < 2) {
            throw new DadosInvalidosException("Mínimo de leituras inválido (" + minimoLeituras + "): use pelo menos 2.");
        }
        this.minimoLeituras = minimoLeituras;
    }
}
