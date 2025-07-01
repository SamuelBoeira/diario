package br.com.meuprojeto.model;
import java.time.LocalDate;

/**
 * Classe que representa uma avaliação/review de uma mídia.
 */
public class Review {
    private int rating;
    private String comment;
    private LocalDate date;

    /**
     * Construtor padrão (necessário para Jackson).
     */
    public Review() {
        // Construtor vazio necessário para desserialização JSON
    }

    /**
     * Construtor da classe Review.
     * 
     * @param rating Avaliação de 1 a 5 estrelas
     * @param comment Comentário sobre a mídia avaliada
     * @param date Data em que a avaliação foi feita
     */
    public Review(int rating, String comment, LocalDate date) {
        this.rating = rating;
        this.comment = comment;
        this.date = date;
    }

    public int getRating() {
        return rating;
    }

    /**
     * Define uma nova nota para a avaliação.
     * 
     * @param rating A nova nota da avaliação
     */
    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    /**
     * Define um novo comentário para a avaliação.
     * 
     * @param comment O novo comentário da avaliação
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDate getDate() {
        return date;
    }

    /**
     * Define uma nova data para a avaliação.
     * 
     * @param date A nova data da avaliação
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return String.format("Avaliação: %d/5 - Data: %s\nComentário: %s",
                rating, date.toString(), comment);
    }
}
