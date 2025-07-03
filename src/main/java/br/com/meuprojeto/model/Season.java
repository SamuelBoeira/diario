package br.com.meuprojeto.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Representa uma temporada de uma Série.
 * Contém informações específicas da temporada, como número, ano, elenco e suas próprias avaliações.
 */
public class Season {
    private int seasonNumber;
    private int releaseYear; // Ano de lançamento da temporada
    private int numberOfEpisodes; // Número de episódios
    private List<Review> reviews = new ArrayList<>();
    private List<Actor> cast = new ArrayList<>(); // Elenco específico da temporada

    public Season() {}

    public Season(int seasonNumber, int releaseYear, int numberOfEpisodes) {
        this.seasonNumber = seasonNumber;
        this.releaseYear = releaseYear;
        this.numberOfEpisodes = numberOfEpisodes;
    }

    /**
     * Calcula a média de avaliação (rating) para esta temporada.
     * A média é baseada em todas as reviews que a temporada recebeu.
     * @return A média de 1 a 5, ou 0.0 se não houver avaliações.
     */
    @JsonIgnore
    public double getAverageRating() {
        if (reviews == null || reviews.isEmpty()) {
            return 0.0;
        }
        return reviews.stream().mapToInt(Review::getRating).average().orElse(0.0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Season season = (Season) o;
        return seasonNumber == season.seasonNumber && releaseYear == season.releaseYear;
    }

    @Override
    public int hashCode() {
        return Objects.hash(seasonNumber, releaseYear);
    }

    @Override
    public String toString() {
        return "Temporada " + seasonNumber + " (" + releaseYear + ")";
    }

    // --- Getters e Setters ---

    public int getSeasonNumber() { return seasonNumber; }
    public void setSeasonNumber(int seasonNumber) { this.seasonNumber = seasonNumber; }
    public int getReleaseYear() { return releaseYear; }
    public void setReleaseYear(int releaseYear) { this.releaseYear = releaseYear; }
    public int getNumberOfEpisodes() { return numberOfEpisodes; }
    public void setNumberOfEpisodes(int numberOfEpisodes) { this.numberOfEpisodes = numberOfEpisodes; }
    public List<Review> getReviews() { return reviews; }
    public void setReviews(List<Review> reviews) { this.reviews = reviews; }
    public List<Actor> getCast() { return cast; }
    public void setCast(List<Actor> cast) { this.cast = cast; }
}