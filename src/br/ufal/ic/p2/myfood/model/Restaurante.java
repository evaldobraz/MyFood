package br.ufal.ic.p2.myfood.model;

public class Restaurante extends Empresa{
    private String tipoCozinha;

    public Restaurante() {}

    public Restaurante(int idDono, String nome, String endereco, String tipoCozinha){
        super(idDono, nome, endereco);
        this.tipoCozinha = tipoCozinha;
    }

    public String getTipoCozinha() {
        return tipoCozinha;
    }

    public void setTipoCozinha(String tipoCozinha) {
        this.tipoCozinha = tipoCozinha;
    }
}
