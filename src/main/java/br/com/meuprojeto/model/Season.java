package br.com.meuprojeto.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Season {
    private int seasonNumber;
    private List<Review> reviews = new ArrayList<>();

    public Season() {}
    public Season(int seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

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
        return seasonNumber == season.seasonNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(seasonNumber);
    }
    
    // Getters e Setters
    public int getSeasonNumber() { return seasonNumber; }
    public void setSeasonNumber(int seasonNumber) { this.seasonNumber = seasonNumber; }
    public List<Review> getReviews() { return reviews; }
    public void setReviews(List<Review> reviews) { this.reviews = reviews; }
    @Override
    public String toString() { return "Temporada " + seasonNumber; }
}