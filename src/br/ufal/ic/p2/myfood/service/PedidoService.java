package br.ufal.ic.p2.myfood.service;

import br.ufal.ic.p2.myfood.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

public class PedidoService {
    public static int criarPedido(int idCliente, int idEmpresa, List<Usuario> usuarios, List<Pedido> pedidos) throws Exception {
        boolean ehEmpresario = usuarios.stream()
                .anyMatch(u -> u.getIdUsuario() == idCliente && u instanceof Empresario);
        if(ehEmpresario) throw new RuntimeException("Dono de empresa nao pode fazer um pedido");

        boolean haPedidoEmAberto = pedidos.stream()
                        .anyMatch(pedido -> pedido.getEstado().equals("aberto")
                                                    && pedido.getIdEmpresa() == idEmpresa
                                                    && pedido.getIdCliente() == idCliente);
        if(haPedidoEmAberto) throw new RuntimeException("Nao e permitido ter dois pedidos em aberto para a mesma empresa");

        Pedido pedido = new Pedido(idCliente, idEmpresa);
        pedidos.add(pedido);
        return pedido.getIdPedido();
    }

    public static void adicionarProduto(int idPedido, int idProduto, List<Pedido> pedidos, List<Produto> produtos) {
        Pedido pedidoAtual = pedidos.stream()
                .filter(pedido -> pedido.getIdPedido() == idPedido)
                .findFirst()
                .orElseThrow(()-> new RuntimeException("Nao existe pedido em aberto"));

        if(!pedidoAtual.getEstado().equals("aberto")) throw new RuntimeException("Nao e possivel adcionar produtos a um pedido fechado");

        Produto produtoParaAdicionar = produtos.stream()
                .filter(produto -> pedidoAtual.getIdEmpresa() == produto.getIdEmpresa() && produto.getIdProduto() == idProduto)
                .findFirst()
                .orElseThrow(()->new RuntimeException("O produto nao pertence a essa empresa"));

        pedidoAtual.getItens().stream()
                .filter(item -> item.getIdProduto() == idProduto)
                .findFirst()
                .ifPresentOrElse(
                        item -> item.setQuantidade(item.getQuantidade() + 1),
                        () -> {
                            ItemPedido novoItem = new ItemPedido(idProduto, 1, produtoParaAdicionar.getValor());
                            pedidoAtual.getItens().add(novoItem);
                        }
                );
        pedidoAtual.setValorTotal(produtoParaAdicionar.getValor());
    }

    public static String getAtributoPedido(int idPedido, String atributo, List<Pedido> pedidos, List<Produto> produtos, List<Empresa> empresas, List<Usuario> usuarios) throws Exception {
        Pedido pedido = getPedidoPorId(idPedido, pedidos);
        return switch (atributo) {
            case "cliente" -> UsuarioService.getAtributo(pedido.getIdCliente(), "nome", usuarios);
            case "empresa" -> EmpresaService.getAtributoEmpresa(pedido.getIdEmpresa(), "nome", empresas, null);
            case "estado" -> pedido.getEstado();
            case "valor" -> String.format(Locale.US, "%.2f", pedido.getValorTotal());
            case "produtos" -> {

                yield pedido.getItens().stream()
                        .flatMap(item -> {
                            Produto p = produtos.stream()
                                    .filter(produto -> produto.getIdProduto() == item.getIdProduto())
                                    .findFirst()
                                    .orElseThrow(() -> new RuntimeException("Produto no pedido nao existe."));
                            return java.util.stream.Stream.generate(p::getNome)
                                    .limit(item.getQuantidade());
                        })
                        .collect(Collectors.joining(", ", "{[", "]}"));

            }
            default -> throw new RuntimeException("Atributo nao existe");
        };
    }

    public static Pedido getPedidoPorId(int idPedido, List<Pedido> pedidos) throws Exception {
        return pedidos.stream()
                .filter(pedido -> pedido.getIdPedido() == idPedido)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Pedido nao encontrado"));
    }

    public static void fecharPedido(int idPedido, List<Pedido> pedidos) throws Exception {
        Pedido pedido = getPedidoPorId(idPedido, pedidos);
        if(pedido.getEstado().equals("aberto")) {
            pedido.setEstado("preparando");
        }
    }

    public static void removerProduto(int idPedido, String nomeProduto, List<Pedido> pedidos, List<Produto> produtos) throws Exception {
        Pedido pedido = getPedidoPorId(idPedido, pedidos);
        if(!pedido.getEstado().equals("aberto")) throw new RuntimeException("Nao e possivel remover produtos de um pedido fechado");

        //busca na lista de produtos o produto correspondente ao pedido
        Produto produtoParaRemover = produtos.stream()
                .filter(produto -> produto.getNome().equals(nomeProduto)
                        && produto.getIdEmpresa() == pedido.getIdEmpresa())
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Produto nao encontrado"));

        //busca na lista de itens do pedido o que será removido
        Optional<ItemPedido> itemParaRemover = pedido.getItens().stream()
                .filter(item -> item.getIdProduto() == produtoParaRemover.getIdProduto())
                .findFirst();

        //remoção de fato do item da lista de itens do pedido
        if (itemParaRemover.isPresent()) {
            ItemPedido item = itemParaRemover.get();
            int quantidadeAtual = item.getQuantidade();

            pedido.setValorTotal(-produtoParaRemover.getValor()); //se a remoção deixar o pedido com valor negativo, o valor vira 0

            if (quantidadeAtual > 1) {
                item.setQuantidade(quantidadeAtual - 1);
            } else {
                pedido.getItens().remove(item);
            }
        }
    }

    public static int getNumeroPedido(int indice, int idCliente, int idEmpresa, List<Pedido> pedidos) throws Exception {
        Pedido pedidoDoCliente = pedidos.stream()
                .filter(pedido -> pedido.getIdCliente() == idCliente && pedido.getIdEmpresa() == idEmpresa)
                .skip(indice)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Pedido nao encontrado"));
        return pedidoDoCliente.getIdPedido();
    }
}
