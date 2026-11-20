package com.example.gsjava.excecao;

public class FonteIndisponivelException extends RuntimeException {

    public FonteIndisponivelException(String mensagem) {
        super(mensagem);
    }
}
