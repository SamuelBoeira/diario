package br.com.meuprojeto.model;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa uma mídia digital (Movie e Series).
 * Herda de Media e adiciona atributos específicos de mídias digitais.
 */
public abstract class DigitalMedia extends Media {
    protected String originalTitle;
    protected String whereToWatch;
    protected List<Actor> cast;

    /**
     * Construtor padrão (necessário para Jackson).
     */
    protected DigitalMedia() {
        super(); // Chama o construtor padrão da classe pai (Media)
        this.originalTitle = "";
        this.whereToWatch = "";
        this.cast = new ArrayList<>();
    }

    /**
     * Construtor da classe DigitalMedia.
     * 
     * @param title Título da mídia
     * @param genre Gênero da mídia
     * @param releaseYear Ano de lançamento da mídia
     * @param originalTitle Título original da mídia
     * @param whereToWatch Plataforma onde a mídia pode ser assistida
     */
    public DigitalMedia(String title, String genre, int releaseYear, String originalTitle, String whereToWatch) {
        super(title, genre, releaseYear);
        this.originalTitle = originalTitle;
        this.whereToWatch = whereToWatch;
        this.cast = new ArrayList<>();  // Inicializa a lista de atores vazia
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    /**
     * Define um novo título original para a mídia.
     * 
     * @param originalTitle O novo título original da mídia
     */
    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getWhereToWatch() {
        return whereToWatch;
    }

    /**
     * Define uma nova plataforma onde a mídia pode ser assistida.
     * 
     * @param whereToWatch A nova plataforma
     */
    public void setWhereToWatch(String whereToWatch) {
        this.whereToWatch = whereToWatch;
    }

    public List<Actor> getCast() {
        return cast;
    }

    /**
     * Define um novo elenco para a mídia.
     * 
     * @param cast A nova lista de atores
     */
    public void setCast(List<Actor> cast) {
        this.cast = cast;
    }

    /**
     * Adiciona um ator ao elenco da mídia.
     * 
     * @param actor O ator a ser adicionado
     */
    public void addActor(Actor actor) {
        cast.add(actor);
    }

    /**
     * Retorna uma representação em string da mídia digital.
     * Estende o método toString da superclasse (Media) adicionando informações específicas de mídias digitais.
     * 
     * @return Uma string formatada com as informações da mídia digital
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        sb.append(String.format("\nTítulo Original: %s\nOnde Assistir: %s", originalTitle, whereToWatch));
        
        if (!cast.isEmpty()) {
            sb.append("\nElenco:");
            for (Actor actor : cast) {
                sb.append("\n  ").append(actor.toString());
            }
        }
        sb.append("\n").append(getReviewsAsString());
        
        return sb.toString();
    }
}
