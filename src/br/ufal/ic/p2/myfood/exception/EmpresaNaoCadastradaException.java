package br.ufal.ic.p2.myfood.exception;

public class EmpresaNaoCadastradaException extends RuntimeException {
    public EmpresaNaoCadastradaException() {
        super("Empresa nao cadastrada");
    }
}
