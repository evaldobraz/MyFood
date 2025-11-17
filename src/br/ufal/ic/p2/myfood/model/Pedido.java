package br.ufal.ic.p2.myfood.model;

import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private int idPedido = 0;
    private static int proximoIdPedido = 0;
    private int idCliente;
    private int idEmpresa;
    private String estado;
    private List<ItemPedido> itens = new ArrayList<>();
    private float valorTotal;

    public Pedido() {}
    public Pedido(int idCliente, int idEmpresa) {
        this.idPedido = proximoIdPedido++;
        this.idCliente = idCliente;
        this.idEmpresa = idEmpresa;
        this.estado = "aberto";
    }

    public int getIdPedido() {
        return idPedido;
    }

    public static int getProximoIdPedido() {
        return proximoIdPedido;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<ItemPedido> getItens() {
        return itens;
    }

    public void setValorTotal(float valorTotal) {
        float temp = valorTotal + this.valorTotal;

        if (temp <= 0) this.valorTotal = 0;
        else this.valorTotal += valorTotal;
    }

    public float getValorTotal() {
        return valorTotal;
    }

}
