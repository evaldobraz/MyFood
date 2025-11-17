package br.ufal.ic.p2.myfood.service;

import br.ufal.ic.p2.myfood.exception.AtributoInvalidoException;
import br.ufal.ic.p2.myfood.exception.EmpresaNaoCadastradaException;
import br.ufal.ic.p2.myfood.exception.ProdutoJaExisteException;
import br.ufal.ic.p2.myfood.exception.ProdutoNaoCadastradoException;
import br.ufal.ic.p2.myfood.model.Empresa;
import br.ufal.ic.p2.myfood.model.Produto;
import br.ufal.ic.p2.myfood.model.Restaurante;
import br.ufal.ic.p2.myfood.model.Usuario;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class ProdutoService {
    public static Produto criarProduto(int idEmpresa, String nome, float valor, String categoria,  List<Produto> produtos) throws Exception{
        if(produtoJaExiste(idEmpresa, nome, produtos)) throw new ProdutoJaExisteException();
        return new Produto(idEmpresa, nome, valor, categoria);
    }

    public static void editarProduto(int idProduto, String nome, float valor, String categoria, List<Produto> produtos) throws Exception{
        Produto p = produtos.stream()
                .filter(produto -> produto.getIdProduto() == idProduto)
                .findFirst()
                .orElseThrow(() -> new ProdutoNaoCadastradoException("nao cadastrado"));
        p.setNome(nome);
        p.setValor(valor);
        p.setCategoria(categoria);
    }

    public static String getAtributoProdudo(String nome, int idEmpresa, String atributo, List<Produto> produtos, List<Empresa> empresas) throws Exception{
        return produtos.stream()
                .filter(produto -> produto.getNome().equals(nome) && produto.getIdEmpresa() == idEmpresa)
                .findFirst()
                .map(produto -> switch (atributo) {
                    case "nome" -> produto.getNome();
                    case "valor" -> String.format(Locale.US, "%.2f", produto.getValor());
                    case "categoria" -> produto.getCategoria();
                    case "empresa" -> {
                        String nomeEmpresa;
                        try {
                            nomeEmpresa = EmpresaService.getAtributoEmpresa(idEmpresa, "nome", empresas, null);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                        yield nomeEmpresa;
                    }
                    default -> throw new RuntimeException("Atributo nao existe");
                })
                .orElseThrow(() -> new ProdutoNaoCadastradoException("nao encontrado"));
    }

    public static String getProdutosDaEmpresa(int idEmpresa, List<Produto> produtos) throws Exception {
        String DELIMITADOR = ", ";
        String PREFIXO = "{[";
        String SUFIXO = "]}";

        return produtos.stream()
                .filter(produto -> produto.getIdEmpresa() == idEmpresa)
                .map(Produto::toString)
                .collect(Collectors.joining(DELIMITADOR, PREFIXO, SUFIXO));
    }

    public static boolean produtoJaExiste(int idEmpresa, String nome, List<Produto> produtos) {
        return produtos.stream()
                .anyMatch(p -> p.getNome().equals(nome) && p.getIdEmpresa() == idEmpresa);
    }

}
