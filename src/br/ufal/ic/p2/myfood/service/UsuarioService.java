package br.ufal.ic.p2.myfood.service;

import java.util.List;

import br.ufal.ic.p2.myfood.exception.DadoInvalidoException;
import br.ufal.ic.p2.myfood.exception.EmailJaExisteException;
import br.ufal.ic.p2.myfood.exception.LoginException;
import br.ufal.ic.p2.myfood.exception.UsuarioNaoExisteException;
import br.ufal.ic.p2.myfood.model.Empresario;
import br.ufal.ic.p2.myfood.model.Usuario;

public class UsuarioService {
    public static String getAtributo(int id, String atributo, List<Usuario> usuarios) throws Exception{
        Usuario u = getUsuarioPorId(id, usuarios);
        return switch (atributo){
            case "email"-> u.getEmail();
            case "nome"-> u.getNome();
            case "endereco"-> u.getEndereco();
            case "senha"-> u.getSenha();
            case "cpf"->{
                if(u instanceof Empresario e) {
                    yield e.getCpf();
                }
                throw new Exception("Atributo invalido");
            }
            default -> throw new Exception("Atributo invalido");
        };
    }

    public static Usuario getUsuarioPorId(int id, List<Usuario> usuarios) throws Exception {
        for(Usuario u : usuarios){
            if(u.getId() == id){
                return u;
            }
        }
        throw new UsuarioNaoExisteException();
    }

    public static int login(String email, String senha, List<Usuario> usuarios) throws Exception {
        for(Usuario u : usuarios){
            if(u.getEmail().equals(email) && u.getSenha().equals(senha)){
                return u.getId();
            }
        }
        throw new LoginException();
    }

    public static void validarDados(String nome, String email, String senha, String endereco, List<Usuario> usuarios) throws Exception{
        if(nome     == null || nome.isEmpty()    ) throw new DadoInvalidoException("Nome");
        if(email    == null || email.isEmpty()   ) throw new DadoInvalidoException("Email");
        if(senha    == null || senha.isEmpty()   ) throw new DadoInvalidoException("Senha");
        if(endereco == null || endereco.isEmpty()) throw new DadoInvalidoException("Endereco");
        verificarEmail(email, usuarios);
    }
    public static void validarDados(String nome, String email, String senha, String endereco, String cpf, List<Usuario> usuarios) throws Exception{
        if(cpf      == null || cpf.isEmpty()     ) throw new DadoInvalidoException("CPF");
        verificarCpf(cpf);
        validarDados(nome, email, senha, endereco, usuarios);
    }


    public static void verificarEmail(String email, List<Usuario> usuarios) throws Exception {
        for(Usuario u : usuarios){
            if(u.getEmail().equals(email)){
                throw new EmailJaExisteException();
            }
        }
        String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        if(!email.matches(EMAIL_REGEX)){
            throw new DadoInvalidoException("Email");
        }
    }

    public static void verificarCpf(String cpf) throws Exception{
        if(cpf.length() != 14){
            throw new DadoInvalidoException("CPF");
        }
    }

}
