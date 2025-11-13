package br.ufal.ic.p2.myfood.exception;

public class UsuarioNaoExisteException extends Exception{
    public UsuarioNaoExisteException(/*String mensage*/){
        super("Usuario nao cadastrado.");
    }
}
