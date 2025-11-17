package br.ufal.ic.p2.myfood.exception;

public class ProdutoNaoCadastradoException extends RuntimeException {
    public ProdutoNaoCadastradoException(String message) {
        super("Produto " + message);
    }
}
