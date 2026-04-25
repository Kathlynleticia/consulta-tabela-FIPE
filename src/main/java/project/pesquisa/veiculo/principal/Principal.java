package project.pesquisa.veiculo.principal;

import project.pesquisa.veiculo.model.Dados;
import project.pesquisa.veiculo.model.Modelos;
import project.pesquisa.veiculo.model.Veiculo;
import project.pesquisa.veiculo.service.ConsumoApi;
import project.pesquisa.veiculo.service.ConverteDados;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private Scanner leitura = new Scanner(System.in);
    private ConverteDados conversor = new ConverteDados();
    private ConsumoApi consumo = new ConsumoApi(); // Deixando a classe consumoApi como atributo da classe Principal
    private final String URL_BASE = "https://parallelum.com.br/fipe/api/v1/";  //Constantes, final significa que nao vai ser alterada mais a frente

    public void exibeMenu() { //criando um metodo
        var menu = """
                   \n++++ OPÇÕES VÁLIDAS ++++
                    
                   - Carro
                   - Moto
                   - Caminhão
                    
                   Digite uma das opções para consultar:
                   """;

        String endereco = null;

        while (endereco == null) {
            System.out.println(menu);
            var opcao = leitura.nextLine().toLowerCase();

            if (opcao.contains("carr")) {
                endereco = URL_BASE + "carros/marcas";
            } else if (opcao.contains("mot")) {
                endereco = URL_BASE + "motos/marcas";
            } else if (opcao.contains("camin")) {
                endereco = URL_BASE + "caminhoes/marcas";
            } else {
                System.out.println("Digite uma opção válida!\n");
            }
        }


        var json = consumo.obterDados(endereco);
        System.out.println(json);
        var marcas = conversor.obterLista(json, Dados.class);
        marcas.stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);

        System.out.println("\nDigite o código da marca para consulta dos modelos: ");
        var codigoMarca = leitura.nextLine();

        endereco = endereco + "/" + codigoMarca + "/modelos";
        json = consumo.obterDados(endereco);
        var modeloLista = conversor.obterDados(json, Modelos.class);

        System.out.println("\nModelos dessa marca: ");
        modeloLista.modelos().stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);


        System.out.println("\nDigite um trecho do nome do veículo a ser buscado: ");
        var nomeVeiculo = leitura.nextLine();

        List<Dados> modelosFiltrados = modeloLista.modelos().stream()
                .filter(m -> m.nome().toLowerCase().contains(nomeVeiculo.toLowerCase()))
                        .collect(Collectors.toList());

        System.out.println("\nModelos filtrados: ");
        modelosFiltrados.forEach(System.out::println);

        System.out.println("\nDigite por favor, o código do modelo para busca de valores de avaliação: ");
        var codigoModelo = leitura.nextLine();

        endereco = endereco + "/" + codigoModelo + "/anos";
        json = consumo.obterDados(endereco);
        List<Dados> anos = conversor.obterLista(json, Dados.class);
        List<Veiculo> veiculos = new ArrayList<>();

        for (int i = 0; i < anos.size(); i++) {
            var enderecoAnos = endereco + "/" + anos.get(i).codigo();
            json = consumo.obterDados(enderecoAnos);
            Veiculo veiculo = conversor.obterDados(json, Veiculo.class);
            veiculos.add(veiculo);
        }

        System.out.println("\nTodos os veículos filtrados com avaliações por ano: ");
        veiculos.forEach(System.out::println);
    }
}
