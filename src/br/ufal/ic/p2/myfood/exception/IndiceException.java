package br.ufal.ic.p2.myfood.exception;

public class IndiceException extends RuntimeException {
    public IndiceException(String message) {
        super("Indice " + message);
    }
}
