package br.com.meuprojeto.model;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Classe que representa uma temporada de uma série.
 */
public class Season {
    private int seasonNumber;
    private int year;
    private int episodeCount;
    private List<Review> reviews;

    /**
     * Construtor padrão (necessário para Jackson).
     */
    public Season() {
        this.seasonNumber = 0;
        this.year = 0;
        this.episodeCount = 0;
        this.reviews = new ArrayList<>();
    }

    /**
     * Construtor da classe Season.
     * 
     * @param seasonNumber Número da temporada
     * @param year Ano de lançamento da temporada
     * @param episodeCount Quantidade de episódios da temporada
     */
    public Season(int seasonNumber, int year, int episodeCount) {
        this.seasonNumber = seasonNumber;
        this.year = year;
        this.episodeCount = episodeCount;
        this.reviews = new ArrayList<>();
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    /**
     * Define um novo número para a temporada.
     * 
     * @param seasonNumber O novo número da temporada
     */
    public void setSeasonNumber(int seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public int getYear() {
        return year;
    }

    /**
     * Define um novo ano para a temporada.
     * 
     * @param year O novo ano da temporada
     */
    public void setYear(int year) {
        this.year = year;
    }

    public int getEpisodeCount() {
        return episodeCount;
    }

    /**
     * Define uma nova quantidade de episódios para a temporada.
     * 
     * @param episodeCount A nova quantidade de episódios
     */
    public void setEpisodeCount(int episodeCount) {
        this.episodeCount = episodeCount;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    /**
     * Define a lista de avaliações da temporada.
     * 
     * @param reviews A nova lista de avaliações
     */
    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    /**
     * Adiciona uma avaliação à temporada.
     * 
     * @param rating Avaliação de 1 a 5 estrelas
     * @param comment Comentário sobre a temporada
     * @param date Data em que a temporada foi assistida
     */
    public void addReview(int rating, String comment, LocalDate date) {
        reviews.add(new Review(rating, comment, date));
    }

    /**
     * Calcula a média das avaliações da temporada.
     * 
     * @return A média das avaliações, ou 0 se não houver avaliações
     */
    @JsonIgnore //
    public double getAverageRating() {
        if (reviews.isEmpty()) {
            return 0;
        }
        return reviews.stream().mapToInt(Review::getRating).average().orElse(0);
    }

    @Override
    public String toString() {
        return String.format("Temporada %d (%d) - %d episódios - Avaliação média: %.1f",
                seasonNumber, year, episodeCount, getAverageRating());
    }
}
