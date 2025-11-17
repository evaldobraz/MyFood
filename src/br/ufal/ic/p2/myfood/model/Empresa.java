package br.ufal.ic.p2.myfood.model;

public abstract class Empresa {
    private int idEmpresa = 0;
    private static int proximoIdEmpresa = 0;
    private int idDono;
    private String nome;
    private String endereco;

    public Empresa() {}

    public Empresa(int idDono, String nome, String endereco) {
        this.idEmpresa = proximoIdEmpresa++;
        this.idDono = idDono;
        this.nome = nome;
        this.endereco = endereco;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public static int getProximoIdEmpresa() {
        return proximoIdEmpresa;
    }

    public static void setProximoIdEmpresa(int proximoIdEmpresa) {
        Empresa.proximoIdEmpresa = proximoIdEmpresa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public int getIdDono() {
        return idDono;
    }

    public void setIdDono(int idDono) {
        this.idDono = idDono;
    }

    @Override
    public String toString() {
        return String.format(
                "["+this.getNome()+", "+this.getEndereco()+"]"
        );
    }
}
