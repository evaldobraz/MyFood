package br.ufal.ic.p2.myfood.exception;

public class AtributoInvalidoException extends RuntimeException {
    public AtributoInvalidoException(String ATRIBUTO) {
        super(ATRIBUTO + " invalido");
    }
}
