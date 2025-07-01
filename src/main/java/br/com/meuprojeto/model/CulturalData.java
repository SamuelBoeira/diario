package br.com.meuprojeto.model;
/**
 * Autor: Samuel Boeira Dantas
 * Componenete curricular: 2025.1 EXA863 - MI - PROGRAMAÇÃO (TP05)
 * Concluído em: 20/05/2025
 * Declaro que este código foi elaborado por mim de forma individual e não contém nenhum techo de código
 * de outro colega ou de outro autor, tais como provindos de livros e apostilas, e páginas de documentos
 * eletrônicos da internet. Qualquer trecho de código de outra autoria que não a minha está destacado com
 * uma citação para o autor e a fonte do código, e estou ciente que esses techaos não serão considerados
 * para fins de avaliação.
 */

import java.util.List;
import java.util.ArrayList;

/**
 * Classe wrapper para conter as listas de mídias para facilitar a serialização/desserialização JSON.
 */
public class CulturalData {
    private List<Book> books;
    private List<Movie> movies;
    private List<Series> series;

    /**
     * Construtor padrão (necessário para Jackson).
     */
    public CulturalData() {
        this.books = new ArrayList<>();
        this.movies = new ArrayList<>();
        this.series = new ArrayList<>();
    }

    /**
     * Construtor com parâmetros.
     * 
     * @param books Lista de livros
     * @param movies Lista de filmes
     * @param series Lista de séries
     */
    public CulturalData(List<Book> books, List<Movie> movies, List<Series> series) {
        this.books = books;
        this.movies = movies;
        this.series = series;
    }

    // Getters e Setters (necessários para Jackson)
    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    public List<Series> getSeries() {
        return series;
    }

    public void setSeries(List<Series> series) {
        this.series = series;
    }
}
