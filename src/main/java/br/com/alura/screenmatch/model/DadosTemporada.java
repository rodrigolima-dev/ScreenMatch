package br.com.alura.screenmatch.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public record DadosTemporada(
        @SerializedName("Season") Integer temporada,
        @SerializedName("Episodes") List<DadosEpisodio> episodios
) {
}
