package br.com.meuprojeto.model;
import java.util.List;
import java.util.ArrayList;

/**
 * Classe que representa um filme, herança de DigitalMedia.
 */
public class Movie extends DigitalMedia {
    private int duration;
    private String director;
    private String screenplay;

    /**
     * Construtor padrão (necessário para Jackson).
     */
    public Movie() {
        super("", "", 0, "", ""); // Valores temporários
        this.duration = 0;
        this.director = "";
        this.screenplay = "";
    }

    /**
     * Construtor da classe Movie.
     * 
     * @param title Título do filme
     * @param genre Gênero do filme
     * @param releaseYear Ano de lançamento do filme
     * @param originalTitle Título original do filme
     * @param whereToWatch Plataforma onde o filme pode ser assistido
     * @param duration Duração do filme em minutos
     * @param director Nome do diretor do filme
     * @param screenplay Nome do roteirista do filme
     */
    public Movie(String title, String genre, int releaseYear, String originalTitle,
                 String whereToWatch, int duration, String director, String screenplay) {
        super(title, genre, releaseYear, originalTitle, whereToWatch);
        this.duration = duration;
        this.director = director;
        this.screenplay = screenplay;
    }

    public int getDuration() {
        return duration;
    }

    /**
     * Define uma nova duração para o filme.
     * 
     * @param duration A nova duração em minutos
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getDirector() {
        return director;
    }

    /**
     * Define um novo diretor para o filme.
     * 
     * @param director O novo nome do diretor
     */
    public void setDirector(String director) {
        this.director = director;
    }

    public String getScreenplay() {
        return screenplay;
    }

    /**
     * Define um novo roteirista para o filme.
     * 
     * @param screenplay O novo nome do roteirista
     */
    public void setScreenplay(String screenplay) {
        this.screenplay = screenplay;
    }

    /**
     * Retorna uma representação em string do filme.
     * Estende o método toString da superclasse (DigitalMedia) adicionando informações específicas de filmes.
     * 
     * @return Uma string formatada com as informações do filme
     */
    @Override
    public String toString() {
        return super.toString() + String.format("\nDuração: %d minutos\nDiretor: %s\nRoteirista: %s",
                duration, director, screenplay);
    }
}
