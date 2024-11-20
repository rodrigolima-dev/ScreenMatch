package br.com.alura.screenmatch.services.traducao;

import br.com.alura.screenmatch.services.ConsumoApi;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import java.net.URLEncoder;

public class ConsultaMyMemory {
    public static String obterTraducao(String text) {
        Gson gson = new Gson();
        ConsumoApi consumo = new ConsumoApi();

        String texto = URLEncoder.encode(text);
        String langpair = URLEncoder.encode("en|pt-br");

        String url = "https://api.mymemory.translated.net/get?q=" + texto + "&langpair=" + langpair;

        String json = consumo.obterDados(url);

        DadosTraducao traducao;
        traducao = gson.fromJson(json, DadosTraducao.class);

        return traducao.dadosResposta().textoTraduzido();
    }
}

