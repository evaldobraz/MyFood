package br.ufal.ic.p2.myfood;

import br.ufal.ic.p2.myfood.model.Cliente;
import br.ufal.ic.p2.myfood.model.Empresario;
import br.ufal.ic.p2.myfood.model.Usuario;
import br.ufal.ic.p2.myfood.service.SistemaService;
import br.ufal.ic.p2.myfood.service.UsuarioService;

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

    static List<Usuario> usuarios = new ArrayList<Usuario>();

    public void zerarSistema(){
        SistemaService.reset(usuarios);
    }

    public void encerrarSistema(){

    }


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
}
