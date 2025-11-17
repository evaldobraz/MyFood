package br.ufal.ic.p2.myfood.model;

public class Empresario extends Usuario {
    private String cpf="000.000.000-00";

    public Empresario() {}

    public Empresario(String nome, String email, String senha, String endereco, String cpf) {
        super(nome, email, senha, endereco);
        this.cpf = cpf;
    }

    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
