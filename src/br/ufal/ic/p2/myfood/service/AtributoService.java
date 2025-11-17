package br.ufal.ic.p2.myfood.service;

import br.ufal.ic.p2.myfood.exception.AtributoInvalidoException;
import br.ufal.ic.p2.myfood.exception.EmpresaNaoCadastradaException;
import br.ufal.ic.p2.myfood.model.Empresa;

import java.util.List;

public class AtributoService {

    public static void validarIndice(int indice) throws Exception {
        if (indice < 0) {
            throw new AtributoInvalidoException("Indice");
        }
    }
    public static void validarNome(String nome) throws Exception {
        if (nome == null || nome.isEmpty()) {
            throw new AtributoInvalidoException("Nome");
        }
    }
    public static void validarAtributo(String atributo) throws Exception {
        if (atributo == null || atributo.isEmpty()) {
            throw new AtributoInvalidoException("Atributo");
        }
    }

    public static void validarAtributo(String atributo, String nomeAtributo) throws Exception {
        if (atributo == null || atributo.isEmpty()) {
            throw new AtributoInvalidoException(nomeAtributo);
        }
    }
    public static void validarIdEmpresa(int idEmpresa, List<Empresa> empresas) throws Exception {
        empresas.stream()
                .filter(e -> e.getIdEmpresa()==idEmpresa)
                .findAny()
                .orElseThrow(EmpresaNaoCadastradaException::new);
    }


    public static void validarValor(float valor) throws Exception {
        if (valor <= 0) {
            throw new AtributoInvalidoException("Valor");
        }
    }

    public static void validarCategoria(String categoria) throws Exception {
        if (categoria == null || categoria.isEmpty()) {
            throw new AtributoInvalidoException("Categoria");
        }
    }
}
