package br.ufal.ic.p2.myfood.model;

public class ItemPedido {
    private int idProduto;
    private int quantidade;
    private float valorUnitario;

    public ItemPedido() {}

    public ItemPedido(int idProduto, int quantidade, float valorUnitario) {
        this.idProduto = idProduto;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public float getValorUnitario() {
        return valorUnitario;
    }
    public void setValorUnitario(float valorUnitario) {
        this.valorUnitario = valorUnitario;
    }
}
