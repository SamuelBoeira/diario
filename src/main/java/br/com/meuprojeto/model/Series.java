package br.com.meuprojeto.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;

public class Series extends DigitalMedia {
    private int endYear;
    private List<Season> seasons = new ArrayList<>();

    public Series() { super(); }

    public Series(String title, String genre, int releaseYear, String originalTitle, String whereToWatch, int endYear) {
        super(title, genre, releaseYear, originalTitle, whereToWatch);
        this.endYear = endYear;
    }

    @Override
    @JsonIgnore
    public double getAverageRating() {
        if (seasons == null || seasons.isEmpty()) {
            return 0.0;
        }
        // Calcula a média das médias das temporadas que têm avaliações.
        return seasons.stream()
                .mapToDouble(Season::getAverageRating)
                .filter(avg -> avg > 0)
                .average()
                .orElse(0.0);
    }

    // Getters e Setters
    public int getEndYear() { return endYear; }
    public void setEndYear(int endYear) { this.endYear = endYear; }
    public List<Season> getSeasons() { return seasons; }
    public void setSeasons(List<Season> seasons) { this.seasons = seasons; }
}