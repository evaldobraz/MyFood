package br.ufal.ic.p2.myfood;

import br.ufal.ic.p2.myfood.exception.EmpresaNaoCadastradaException;
import br.ufal.ic.p2.myfood.model.*;
import br.ufal.ic.p2.myfood.service.*;

import java.util.ArrayList;
import java.util.List;

public class Facade {
//    <void> zerarSistema
//            <void> encerrarSistema
//      <void> criarUsuario nome=<String> email=<String> senha=<String> endereco=<String>
//      <int> login email=<String> senha=<String>
//    <String> getAtributoUsuario id=<int> atributo=<String>
//      <int> criarEmpresa tipoEmpresa=<String> dono=<int> nome=<String> endereco=<String> tipoCozinha=<String>
//      <List<List>> getEmpresasDoUsuario idDono=<int>
//    <String> getAtributoEmpresa empresa=<int> atributo=<String>
//      <int> getIdEmpresa idDono=<int> nome=<String> indice=<int>
//      <int> criarProduto empresa=<int> nome=<String> valor=<Float> categoria=<String>
//      <void> editarProduto produto=<int> nome=<String> valor=<Float> categoria=<String>
//    <String> getProduto nome=<String> empresa=<int> atributo=<String>
//    <List> listarProdutos empresa=<int>
//      <int> criarPedido cliente=<int> empresa=<int>
//      <void> adcionarProduto numero=<int> produto=<int>
//    <String> getPedidos numero=<int> atributo=<String>
//      <void> fecharPedido numero=<int>
//      <void> removerProduto pedido=<int> produto=<String>
//      <int> getNumeroPedido cliente=<int> empresa=<int> indice=<int>

    private static final String USUARIO_DB = "db_usuarios.xml";
    private static final String EMPRESA_DB = "db_empresas.xml";
    private static final String PRODUTO_DB = "db_produtos.xml";
    private static final String PEDIDOS_DB = "db_pedidos.xml";

    static List<Usuario> usuarios = SistemaService.carregarDados(USUARIO_DB);
    static List<Empresa> empresas = SistemaService.carregarDados(EMPRESA_DB);
    static List<Produto> produtos = SistemaService.carregarDados(PRODUTO_DB);
    static List<Pedido> pedidos = SistemaService.carregarDados(PEDIDOS_DB);

    public void zerarSistema(){
        SistemaService.reset(usuarios);
        SistemaService.reset(empresas);
        SistemaService.reset(produtos);
        SistemaService.reset(pedidos);
        SistemaService.salvarDados(usuarios, USUARIO_DB);
        SistemaService.salvarDados(empresas, EMPRESA_DB);
        SistemaService.salvarDados(produtos, PRODUTO_DB);
        SistemaService.salvarDados(pedidos, PEDIDOS_DB);
    }

    public void encerrarSistema(){
        SistemaService.salvarDados(usuarios,  USUARIO_DB);
        SistemaService.salvarDados(empresas,  EMPRESA_DB);
        SistemaService.salvarDados(produtos,  PRODUTO_DB);
        SistemaService.salvarDados(pedidos,  PEDIDOS_DB);
    }

    //Us1

    public void criarUsuario(String nome, String email, String senha, String endereco) throws Exception{ //cria usuário cliente
        UsuarioService.validarDados(nome, email, senha, endereco, usuarios);
        Cliente cliente = new Cliente(nome, email, senha, endereco);
        usuarios.add(cliente);
    }
    public void criarUsuario(String nome, String email, String senha, String endereco, String cpf) throws Exception { //cria usuário empresario, cpf identifica empresario
        UsuarioService.validarDados(nome, email, senha, endereco, cpf, usuarios);
        Empresario empresario = new Empresario(nome, email, senha, endereco, cpf);
        usuarios.add(empresario);
    }

    public String getAtributoUsuario(int id, String atributo) throws Exception {
        return UsuarioService.getAtributo(id, atributo, usuarios);
    }

    public int login(String email, String senha) throws Exception {
        return UsuarioService.login(email, senha, usuarios);
    }

    //Us2
    public int criarEmpresa(String tipoEmpresa, int idDono, String nome, String endereco, String tipoCozinha) throws Exception {
        return switch(tipoEmpresa){
            case("restaurante")->{
                UsuarioService.verificarPermissoes(usuarios, idDono, "criar uma empresa");
                Restaurante restaurante = EmpresaService.criarRestaurante(idDono, nome, endereco, tipoCozinha, empresas);
                empresas.add(restaurante);
                yield restaurante.getIdEmpresa();
            }
            default -> throw new Exception("Empresa nao pode ser criada");
        };
    }

    public String getEmpresasDoUsuario(int idDono) throws Exception {
        UsuarioService.verificarPermissoes(usuarios, idDono, "criar uma empresa");
        return UsuarioService.getEmpresasDoUsuario(idDono, empresas);
    }

    public int getIdEmpresa(int idDono, String nome, int indice) throws Exception {
        AtributoService.validarIndice(indice);
        AtributoService.validarNome(nome);
        return EmpresaService.getIdEmpresa(idDono, nome, indice, empresas);
    }

    public String getAtributoEmpresa(int idEmpresa, String atributo) throws Exception {
        AtributoService.validarIdEmpresa(idEmpresa, empresas);
        AtributoService.validarAtributo(atributo);
        return EmpresaService.getAtributoEmpresa(idEmpresa, atributo, empresas, usuarios);
    }

    //Us3
    public int criarProduto(int idEmpresa, String nome, float valor, String categoria) throws Exception {
        AtributoService.validarIdEmpresa(idEmpresa, empresas);
        AtributoService.validarNome(nome);
        AtributoService.validarValor(valor);
        AtributoService.validarCategoria(categoria);
        Produto p = ProdutoService.criarProduto(idEmpresa, nome, valor, categoria, produtos);
        produtos.add(p);
        return p.getIdProduto();
    }

    public void editarProduto(int idProduto, String nome, float valor, String categoria)  throws Exception {
        AtributoService.validarNome(nome);
        AtributoService.validarValor(valor);
        AtributoService.validarCategoria(categoria);
        ProdutoService.editarProduto(idProduto, nome, valor, categoria, produtos);
    }

    public String getProduto(String nome, int idEmpresa, String atributo) throws Exception {
        AtributoService.validarNome(nome);
        AtributoService.validarIdEmpresa(idEmpresa, empresas);
        return ProdutoService.getAtributoProdudo(nome, idEmpresa, atributo, produtos, empresas);
    }

    public String listarProdutos(int idEmpresa) throws Exception {
        try {
            AtributoService.validarIdEmpresa(idEmpresa, empresas);
        } catch(EmpresaNaoCadastradaException e){
            throw new RuntimeException("Empresa nao encontrada");
        }
        return ProdutoService.getProdutosDaEmpresa(idEmpresa, produtos);
    }

    //Us4
    public int criarPedido(int idCliente, int idEmpresa) throws Exception {
        return PedidoService.criarPedido(idCliente, idEmpresa, usuarios, pedidos);
    }

    public void adicionarProduto(int idPedido, int idProduto) throws Exception {
        PedidoService.adicionarProduto(idPedido, idProduto, pedidos, produtos);
    }

    public String getPedidos(int idPedido, String atributo) throws Exception {
        AtributoService.validarAtributo(atributo);
        return PedidoService.getAtributoPedido(idPedido, atributo, pedidos, produtos, empresas, usuarios);
    }

    public void fecharPedido(int idPedido) throws Exception {
        PedidoService.fecharPedido(idPedido, pedidos);
    }

    public void removerProduto(int idPedido, String produto) throws Exception {
        AtributoService.validarAtributo(produto, "Produto");
        PedidoService.removerProduto(idPedido, produto, pedidos, produtos);
    }

    public int getNumeroPedido(int idCliente, int idEmpresa, int indice) throws Exception {
        AtributoService.validarIdEmpresa(idEmpresa, empresas);
        return PedidoService.getNumeroPedido(indice, idCliente, idEmpresa, pedidos);
    }
}
