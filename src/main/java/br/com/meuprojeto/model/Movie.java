package br.com.meuprojeto.model;

public class Movie extends DigitalMedia {
    private int duration;
    private String director;
    private String screenplay;

    public Movie() { super(); }

    public Movie(String title, String genre, int releaseYear, String originalTitle, String whereToWatch, int duration, String director, String screenplay) {
        super(title, genre, releaseYear, originalTitle, whereToWatch);
        this.duration = duration;
        this.director = director;
        this.screenplay = screenplay;
    }
    
    // Getters e Setters
    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }
    public String getDirector() { return director; }
    public void setDirector(String director) { this.director = director; }
    public String getScreenplay() { return screenplay; }
    public void setScreenplay(String screenplay) { this.screenplay = screenplay; }
}