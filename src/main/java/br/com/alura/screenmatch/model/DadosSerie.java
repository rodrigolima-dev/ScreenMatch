package br.com.alura.screenmatch.model;


import com.google.gson.annotations.SerializedName;

public record DadosSerie(
        @SerializedName("Title") String titulo,
        @SerializedName("totalSeasons") Integer totalTemporadas,
        @SerializedName("imdbRating") String avaliacao,
        @SerializedName("Genre") String genero,
        @SerializedName("Actors") String atores,
        @SerializedName("Plot") String sinopse,
        @SerializedName("Poster") String poster

) {
}
