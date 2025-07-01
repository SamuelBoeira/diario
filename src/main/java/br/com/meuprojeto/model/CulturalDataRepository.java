package br.com.meuprojeto.model;

import java.util.List;
import java.util.ArrayList;

/**
 * Repositório para gerenciar os dados culturais (livros, filmes, séries).
 * Atua como uma camada entre o Controller e os dados persistidos.
 */
public class CulturalDataRepository {
    private CulturalData culturalData;

    /**
     * Construtor do repositório de dados culturais.
     * @param culturalData Objeto CulturalData contendo as listas de mídias.
     */
    public CulturalDataRepository(CulturalData culturalData) {
        this.culturalData = culturalData;
    }

    /**
     * Retorna o objeto CulturalData completo.
     * @return O objeto CulturalData.
     */
    public CulturalData getCulturalData() {
        return culturalData;
    }

    // Métodos para adicionar e obter mídias específicas

    public List<Book> getBooks() {
        return culturalData.getBooks();
    }

    public void addBook(Book book) {
        culturalData.getBooks().add(book);
    }

    public List<Movie> getMovies() {
        return culturalData.getMovies();
    }

    public void addMovie(Movie movie) {
        culturalData.getMovies().add(movie);
    }

    public List<Series> getSeries() {
        return culturalData.getSeries();
    }

    public void addSeries(Series series) {
        culturalData.getSeries().add(series);
    }
}


