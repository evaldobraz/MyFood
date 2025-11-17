package br.ufal.ic.p2.myfood.service;

import br.ufal.ic.p2.myfood.model.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileOutputStream;


public class SistemaService {

    public static void salvarDados(List<?> dados, String nomeArquivo) {
        try (XMLEncoder encoder = new XMLEncoder(new FileOutputStream(nomeArquivo))) {
            encoder.writeObject(dados);
        } catch (Exception e) {
            System.err.println("Erro ao salvar dados: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> carregarDados(String nomeArquivo) {
        try (XMLDecoder decoder = new XMLDecoder(new FileInputStream(nomeArquivo))) {
            // O cast é inseguro, mas inevitável
            return (List<T>) decoder.readObject();
        } catch (Exception e) {
            return new ArrayList<T>(); // Retorna lista vazia do tipo T
        }
    }

    public static void reset(List<?> list) {
        if(!list.isEmpty()){
            list.clear();
        }
    }
}
