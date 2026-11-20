package com.example.gsjava.model;

import com.example.gsjava.excecao.DadosInvalidosException;

public class Limiares {

    private double atencao;
    private double alerta;
    private double critico;

    public Limiares(double atencao, double alerta, double critico) {
        if (atencao < 0 || alerta < 0 || critico < 0) {
            throw new DadosInvalidosException("Limiar inválido: os limites não podem ser negativos.");
        }
        if (!(atencao < alerta && alerta < critico)) {
            throw new DadosInvalidosException("Limiares inválidos (ATENÇÃO=" + atencao + ", ALERTA=" + alerta
                    + ", CRÍTICO=" + critico + "): use ATENÇÃO < ALERTA < CRÍTICO.");
        }
        this.atencao = atencao;
        this.alerta = alerta;
        this.critico = critico;
    }

    public NivelAlerta classificar(double valor) {
        if (valor >= critico) {
            return NivelAlerta.CRITICO;
        } else if (valor >= alerta) {
            return NivelAlerta.ALERTA;
        } else if (valor >= atencao) {
            return NivelAlerta.ATENCAO;
        }
        return null;
    }

    public double limitePara(NivelAlerta nivel) {
        if (nivel == NivelAlerta.CRITICO) {
            return critico;
        } else if (nivel == NivelAlerta.ALERTA) {
            return alerta;
        }
        return atencao;
    }

    public double getAtencao() { return atencao; }
    public double getAlerta() { return alerta; }
    public double getCritico() { return critico; }
}
