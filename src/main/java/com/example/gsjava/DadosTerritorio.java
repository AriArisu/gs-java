package com.example.gsjava;

import java.util.Objects;

public class DadosTerritorio {

    private String idTerritorio;
    private String nomeTerritorio;
    private double limiarMinimo;
    private double limiarMaximo;
    private String codigoAcesso;

    public DadosTerritorio(String idTerritorio, String nomeTerritorio, double limiarMinimo, double limiarMaximo, String codigoAcesso) {
        if (codigoAcesso == null || codigoAcesso.isBlank()) {
            throw new IllegalArgumentException("O código de acesso não pode ser vazio.");
        }
        validarLimiares(limiarMinimo, limiarMaximo);

        this.idTerritorio = idTerritorio;
        this.nomeTerritorio = nomeTerritorio;
        this.limiarMinimo = limiarMinimo;
        this.limiarMaximo = limiarMaximo;
        this.codigoAcesso = codigoAcesso;
    }


    public String getIdTerritorio() {
        return idTerritorio;
    }

    public String getNomeTerritorio() {
        return nomeTerritorio;
    }

    public double getLimiarMinimo() {
        return limiarMinimo;
    }

    public double getLimiarMaximo() {
        return limiarMaximo;
    }

    // Método protegido por código de acesso para alterar os limiares
    public boolean alterarLimiares(String codigoInformado, double novoLimiarMinimo, double novoLimiarMaximo) {
        if (!validarCodigoAcesso(codigoInformado)) {
            throw new SecurityException("Código de acesso inválido. Alteração dos limiares não autorizada.");
        }

        validarLimiares(novoLimiarMinimo, novoLimiarMaximo);

        this.limiarMinimo = novoLimiarMinimo;
        this.limiarMaximo = novoLimiarMaximo;
        return true;
    }

    // Método para atualizar o próprio código de acesso
    public void alterarCodigoAcesso(String codigoAtual, String novoCodigo) {
        if (!validarCodigoAcesso(codigoAtual)) {
            throw new SecurityException("Código de acesso atual inválido.");
        }
        if (novoCodigo == null || novoCodigo.isBlank()) {
            throw new IllegalArgumentException("O novo código de acesso não pode ser vazio.");
        }
        this.codigoAcesso = novoCodigo;
    }

    private boolean validarCodigoAcesso(String codigoInformado) {
        return codigoInformado != null && Objects.equals(this.codigoAcesso, codigoInformado);
    }

    private void validarLimiares(double minimo, double maximo) {
        if (minimo < 0 || maximo < 0) {
            throw new IllegalArgumentException("Os limiares não podem ser negativos.");
        }
        if (minimo >= maximo) {
            throw new IllegalArgumentException("O limiar mínimo deve ser menor que o limiar máximo.");
        }
    }

    @Override
    public String toString() {
        return "DadosTerritorio{" +
                "idTerritorio='" + idTerritorio + '\'' +
                ", nomeTerritorio='" + nomeTerritorio + '\'' +
                ", limiarMinimo=" + limiarMinimo +
                ", limiarMaximo=" + limiarMaximo +
                '}';
    }
}