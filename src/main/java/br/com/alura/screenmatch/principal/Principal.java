package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.DadosEpisodio;
import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.model.Episodio;
import br.com.alura.screenmatch.services.ConsumoApi;
import br.com.alura.screenmatch.services.ConverteDados;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumoApi =  new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();

    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=1ec26a6f";

    public void exibeMenu() {
        System.out.println("---------- Menu ----------");
        System.out.print("Série: ");
        var nomeSerie = leitura.nextLine();

        var json = consumoApi
                .obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        DadosSerie dadosSerie = conversor.obterDados(json, DadosSerie.class);
        System.out.println(dadosSerie);

        List<DadosTemporada> temporadas = new ArrayList<>();
		for (int i = 1; i <= dadosSerie.totalTemporadas(); i++) {
			json = consumoApi
                    .obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY + "&season=" + i);
			DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
			temporadas.add(dadosTemporada);
		}

		temporadas.forEach(System.out::println);

        //Para cada temporada pegue os episódios e para cada episódio imprima o titulo.
        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));

        //Pegando todos os episodios das temporadas com stream.
        List<DadosEpisodio> dadosEpisodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream())
                .collect(Collectors.toList());

//        //Coloca em ordem decrescente e limita os 10 primeiros. (top 10 episódios)
//        System.out.println("Top 10 episódios");
//        dadosEpisodios.stream()
//                .filter(e -> !Objects.equals(e.avaliacao(), "N/A"))
//                .peek(e -> System.out.println("Primeiro filtro(N/A)" + e))
//                .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
//                .peek(e -> System.out.println("Ordenação. " + e))
//                .limit(10)
//                .map(e -> e.titulo().toUpperCase())
//                .forEach(System.out::println);

        List<Episodio> episodios = temporadas.stream()
                        .flatMap(t -> t.episodios().stream()
                                .map(d -> new Episodio(t.temporada(), d))
                        ).collect(Collectors.toList());

        episodios.forEach(System.out::println);


//        System.out.println("Título: ");
//        var trechoTitulo = leitura.nextLine();
//        Optional<Episodio> episodioBuscado = episodios.stream()
//                .filter(e -> e.getTitulo().toUpperCase().contains(trechoTitulo.toUpperCase()))
//                .findFirst();
//        if (episodioBuscado.isPresent()) {
//            System.out.println("Episódio encontrado.");
//            System.out.println("Temporada: " + episodioBuscado.get().getTemporada());
//        } else {
//            System.out.println("Episódio não encontrado.");
//        }
//
//        System.out.println("\n ---- Continuação ----");
//        System.out.println("A partir de que ano deseja ver os eps: ");
//        var ano = leitura.nextInt();
//        leitura.nextLine();
//
//        LocalDate dataBusca = LocalDate.of(ano, 1, 1);
//        //Formato brasiileiro de datas.
//        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//        episodios.stream()
//                .filter(
//                        e -> e.getDataLancamento() != null && e.getDataLancamento().isAfter(dataBusca)
//                )
//                .forEach(e -> System.out.println(
//                        "Temporada: " + e.getTemporada() +
//                        " Episódio: " + e.getTitulo() +
//                        " Data de lançamento: " + e.getDataLancamento().format(formatador)
//
//                ));


        //Mapeei e criei uma lista com chave e valor, a chave é o numero da temporada e o valor sua avaliação.
        Map<Integer, Double> avaliacoesPorTemporada = episodios.stream()
                .filter(e -> e.getAvaliacao() != 0.0)
                .collect(Collectors.groupingBy(Episodio::getTemporada,
                        Collectors.averagingDouble(Episodio::getAvaliacao)));

        System.out.println(avaliacoesPorTemporada);

        DoubleSummaryStatistics est = episodios.stream()
                .filter(e -> e.getAvaliacao() != 0.0)
                .collect(Collectors.summarizingDouble(Episodio::getAvaliacao));

        System.out.println(est);
        System.out.println("Média: " + est.getAverage());
        System.out.println("--------------------------");

    }
}
