package br.com.alura.screenmatch.services;


import br.com.alura.screenmatch.model.DadosSerie;
import com.google.gson.Gson;

public class ConverteDados implements IConverteDados{
    Gson gson = new Gson();


    @Override
    public <T> T obterDados(String json, Class<T> classe) {
        return gson.fromJson(json, classe);
    }
}
