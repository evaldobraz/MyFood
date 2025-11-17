package br.ufal.ic.p2.myfood.model;

public abstract class Usuario {
    private int idUsuario = 0;
    private static int proximoIdUsuario = 0;
    private String nome;
    private String email;
    private String senha;
    private String endereco;

    public Usuario() {}

    public Usuario(String nome, String email, String senha, String endereco) {
        idUsuario = proximoIdUsuario++;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.endereco = endereco;
    }

    public int getIdUsuario() {
        return this.idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
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
