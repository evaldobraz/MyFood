package br.ufal.ic.p2.myfood.model;

public class Produto {
    //      <int> criarProduto empresa=<int> nome=<String> valor=<Float> categoria=<String>
    private int idProduto = 0;
    private static int proximoIdProduto = 0;
    private int idEmpresa;
    private String nome;
    private float valor;
    private String categoria;

    public Produto () {};

    public Produto (int idEmpresa, String nome, float valor, String categoria) {
        idProduto = proximoIdProduto++;
        this.idEmpresa = idEmpresa;
        this.nome = nome;
        this.valor = valor;
        this.categoria = categoria;
    }

    public int getIdProduto() {
        return idProduto;
    }


    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return String.format(
                this.getNome()
        );
    }
}
