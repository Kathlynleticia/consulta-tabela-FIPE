package project.pesquisa.veiculo.service;

import java.util.List;

public interface IConverteDados {
    <T> T obterDados(String json, Class<T> classe );// recebe um json, uma classe e no converteDados vamos tentar converter um json na classe indicada

    <T> List<T> obterLista(String json, Class<T> classe );
}
