package br.com.meuprojeto.model;

import java.time.LocalDate;

public class Review {
    private int rating; // 1 a 5
    private String comment;
    private LocalDate reviewDate;

    public Review() {}
    public Review(int rating, String comment) {
        this.rating = rating;
        this.comment = comment;
        this.reviewDate = LocalDate.now();
    }

    // Getters e Setters
    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }
    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
    public LocalDate getReviewDate() { return reviewDate; }
    public void setReviewDate(LocalDate reviewDate) { this.reviewDate = reviewDate; }
}