package br.com.alura.screenmatch.repository;

import br.com.alura.screenmatch.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;

//Entidade e tipo de ID
public interface SerieRepository extends JpaRepository<Serie, Long> {

}
