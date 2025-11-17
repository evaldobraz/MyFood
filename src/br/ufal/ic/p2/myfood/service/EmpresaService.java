package br.ufal.ic.p2.myfood.service;

import br.ufal.ic.p2.myfood.exception.*;
import br.ufal.ic.p2.myfood.model.Empresa;
import br.ufal.ic.p2.myfood.model.Restaurante;
import br.ufal.ic.p2.myfood.model.Usuario;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class EmpresaService {
    public static Restaurante criarRestaurante(int idDono, String nome, String endereco, String tipoCozinha, List<Empresa> empresas) throws Exception {
        verificarRestaurantesExistentes(idDono, nome, endereco, empresas);
        return new Restaurante(idDono, nome, endereco, tipoCozinha);
    }

    public static void verificarRestaurantesExistentes(int idDonoCadastro, String nomeCadastro, String enderecoCadastro, List<Empresa>  empresas) throws Exception{
        List<Restaurante> restaurantes = empresas.stream()
                .filter(e -> e instanceof Restaurante) // Filtra todas as Empresas que são Restaurante
                .map(Restaurante.class::cast)
                .toList();

        for(Restaurante restaurante : restaurantes) {
            // Um dono diferente não pode cadastrar uma empresa com o mesmo nome de uma existente,
            // o dono de um restaurante pode cadastrar uma nova empresa desde que seja em endereço diferente.
            if(restaurante.getNome().equalsIgnoreCase(nomeCadastro)) {
                if(restaurante.getIdDono() != idDonoCadastro) {
                    throw new EmpresaExistenteException("Empresa com esse nome ja existe");
                }
                else if(restaurante.getEndereco().equalsIgnoreCase(enderecoCadastro)) {
                    throw new EmpresaExistenteException("Proibido cadastrar duas empresas com o mesmo nome e local");
                }
            }
        }
    }

    public static int getIdEmpresa(int idDono, String nome, int indice, List<Empresa> empresas) throws Exception {
        List<Empresa> empresasDoUsuario = empresas.stream()
                .filter(e -> e.getIdDono() == idDono && e.getNome().equals(nome))
                .toList();

        if (empresasDoUsuario.isEmpty()) {
            throw new EmpresaNaoExisteException();
        }
        if (indice >= empresasDoUsuario.size()) {
            throw new IndiceException("maior que o esperado");
        }
        Optional<Integer> idRetorno = empresasDoUsuario.stream()
                .skip(indice)
                .findFirst()
                .map(Empresa::getIdEmpresa);
        return idRetorno.get();
    }

    public static String getAtributoEmpresa(int idEmpresa, String atributo, List<Empresa> empresas, List<Usuario> usuarios) throws Exception {
        return empresas.stream()
                .filter(empresa -> empresa.getIdEmpresa() == idEmpresa)
                .findFirst()
                .map(empresa -> {
                    switch (atributo) {
                        case "nome":
                            return empresa.getNome();
                        case "endereco":
                            return empresa.getEndereco();
                        case "dono":
                            try {
                                Usuario u = UsuarioService.getUsuarioPorId(empresa.getIdDono(), usuarios);
                                return u.getNome();
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        case "tipoCozinha": {
                            if(empresa instanceof Restaurante r) {
                                return r.getTipoCozinha();
                            }
                            throw new AtributoInvalidoException("Atributo");
                        }
                        default:
                            throw new AtributoInvalidoException("Atributo");
                    }
                })
                .orElseThrow(EmpresaNaoCadastradaException::new);
    }
}
