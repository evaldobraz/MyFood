package br.ufal.ic.p2.myfood.exception;

public class LoginException extends RuntimeException {
    public LoginException() {
        super("Login ou senha invalidos");
    }
}
