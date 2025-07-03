package br.com.meuprojeto.model;

import java.util.ArrayList;
import java.util.List;

public abstract class DigitalMedia extends Media {
    protected String originalTitle;
    protected String whereToWatch;
    protected List<Actor> cast = new ArrayList<>();

    public DigitalMedia() { super(); }

    public DigitalMedia(String title, String genre, int releaseYear, String originalTitle, String whereToWatch) {
        super(title, genre, releaseYear);
        this.originalTitle = originalTitle;
        this.whereToWatch = whereToWatch;
    }

    // Getters e Setters
    public String getOriginalTitle() { return originalTitle; }
    public void setOriginalTitle(String originalTitle) { this.originalTitle = originalTitle; }
    public String getWhereToWatch() { return whereToWatch; }
    public void setWhereToWatch(String whereToWatch) { this.whereToWatch = whereToWatch; }
    public List<Actor> getCast() { return cast; }
    public void setCast(List<Actor> cast) { this.cast = cast; }
}