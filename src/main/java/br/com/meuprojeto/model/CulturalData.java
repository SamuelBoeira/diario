package br.com.meuprojeto.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe wrapper que contém todas as listas de mídias.
 * Facilita a serialização e desserialização para o arquivo JSON.
 */
public class CulturalData {
    private List<Book> books = new ArrayList<>();
    private List<Movie> movies = new ArrayList<>();
    private List<Series> series = new ArrayList<>();

    public List<Book> getBooks() { return books; }
    public void setBooks(List<Book> books) { this.books = books; }
    public List<Movie> getMovies() { return movies; }
    public void setMovies(List<Movie> movies) { this.movies = movies; }
    public List<Series> getSeries() { return series; }
    public void setSeries(List<Series> series) { this.series = series; }
}