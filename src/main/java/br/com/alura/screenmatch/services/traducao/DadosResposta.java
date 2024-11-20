package br.com.alura.screenmatch.services.traducao;

import com.google.gson.annotations.SerializedName;

public record DadosResposta(
        @SerializedName("translatedText") String textoTraduzido
) {
}
