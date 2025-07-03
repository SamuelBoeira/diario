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

    /**
     * Calcula a média de avaliação (rating) GERAL para a série.
     * A média é calculada a partir da média das médias de cada temporada que possui avaliação.
     * @return A média geral de 1 a 5, ou 0.0 se nenhuma temporada foi avaliada.
     */
    @Override
    @JsonIgnore
    public double getAverageRating() {
        if (seasons == null || seasons.isEmpty()) {
            return 0.0;
        }
        // Calcula a média das médias das temporadas que têm avaliações.
        return seasons.stream()
                .mapToDouble(Season::getAverageRating) // Pega a média de cada temporada
                .filter(avg -> avg > 0) // Considera apenas temporadas que foram avaliadas
                .average() // Calcula a média final
                .orElse(0.0); // Retorna 0.0 se nenhuma temporada tiver avaliação
    }

    // Getters e Setters
    public int getEndYear() { return endYear; }
    public void setEndYear(int endYear) { this.endYear = endYear; }
    public List<Season> getSeasons() { return seasons; }
    public void setSeasons(List<Season> seasons) { this.seasons = seasons; }
}