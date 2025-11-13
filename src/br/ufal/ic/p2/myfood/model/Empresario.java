package br.ufal.ic.p2.myfood.model;

public class Empresario extends Usuario {
    private final String cpf;
    public Empresario(String nome, String email, String senha, String endereco, String cpf) {
        super(nome, email, senha, endereco);
        this.cpf = cpf;
    }

    public String getCpf() {
        return cpf;
    }
}
