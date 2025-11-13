package br.ufal.ic.p2.myfood.service;

import java.util.List;

public class SistemaService {
    public static void reset(List<?> list) {
        if(!list.isEmpty()){
            list.clear();
        }
    }
}
