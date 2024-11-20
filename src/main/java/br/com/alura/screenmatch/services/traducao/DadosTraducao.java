package br.com.alura.screenmatch.services.traducao;

import com.google.gson.annotations.SerializedName;

public record DadosTraducao(
        @SerializedName("responseData") DadosResposta dadosResposta
) {
}
