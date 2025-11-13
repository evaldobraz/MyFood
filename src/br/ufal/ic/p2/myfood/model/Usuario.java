package br.ufal.ic.p2.myfood.model;

public abstract class Usuario {
    private final int id;
    private static int proximoId = 0;
    private String nome;
    private String email;
    private String senha;
    private String endereco;

    public Usuario(String nome, String email, String senha, String endereco) {
        id = proximoId++;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.endereco = endereco;
    }

    public int getId() {
        return this.id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
}
