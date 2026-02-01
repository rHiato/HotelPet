package br.com.hotelpet.infra.exception;
public class EntidadeNaoEncontradaException extends RuntimeException {
    public EntidadeNaoEncontradaException(String msg) {

        super(msg);

    }
}