package br.ufal.ic.p2.myfood.exception;

public class DadoInvalidoException extends RuntimeException {
    public DadoInvalidoException(String message) {
        super(message+" invalido");
    }
}
