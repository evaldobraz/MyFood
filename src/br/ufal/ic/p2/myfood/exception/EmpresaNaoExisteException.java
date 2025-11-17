package br.ufal.ic.p2.myfood.exception;

public class EmpresaNaoExisteException extends RuntimeException {
    public EmpresaNaoExisteException() {
        super("Nao existe empresa com esse nome");
    }
}
