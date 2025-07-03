package br.com.meuprojeto.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = Book.class, name = "book"),
    @JsonSubTypes.Type(value = Movie.class, name = "movie"),
    @JsonSubTypes.Type(value = Series.class, name = "series")
})
public abstract class Media {
    protected String title;
    protected String genre;
    protected int releaseYear;
    protected boolean consumed;
    protected LocalDate consumptionDate;
    protected List<Review> reviews = new ArrayList<>();

    public Media() {}

    public Media(String title, String genre, int releaseYear) {
        this.title = title;
        this.genre = genre;
        this.releaseYear = releaseYear;
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
        Media media = (Media) o;
        return releaseYear == media.releaseYear && Objects.equals(title, media.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, releaseYear);
    }

    // Getters e Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
    public int getReleaseYear() { return releaseYear; }
    public void setReleaseYear(int releaseYear) { this.releaseYear = releaseYear; }
    public boolean isConsumed() { return consumed; }
    public void setConsumed(boolean consumed) { this.consumed = consumed; }
    public LocalDate getConsumptionDate() { return consumptionDate; }
    public void setConsumptionDate(LocalDate consumptionDate) { this.consumptionDate = consumptionDate; }
    public List<Review> getReviews() { return reviews; }
    public void setReviews(List<Review> reviews) { this.reviews = reviews; }
}