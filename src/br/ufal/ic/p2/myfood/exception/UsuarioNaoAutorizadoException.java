package br.ufal.ic.p2.myfood.exception;

public class UsuarioNaoAutorizadoException extends RuntimeException {
    public UsuarioNaoAutorizadoException(String message) {
        super("Usuario nao pode " + message);
    }
}
