package br.com.alura.screenmatch.model;


import com.google.gson.annotations.SerializedName;

public record DadosSerie(
        @SerializedName("Title") String titulo,
        @SerializedName("totalSeasons") Integer totalTemporadas,
        @SerializedName("imdbRating") String avaliacao

) {
}
