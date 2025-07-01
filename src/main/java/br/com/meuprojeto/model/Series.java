package br.com.meuprojeto.model;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

/**
 * Classe que representa uma série, herança de DigitalMedia.
 */
public class Series extends DigitalMedia {
    private int endYear;
    private List<Season> seasons;

    /**
     * Construtor padrão (necessário para Jackson).
     */
    public Series() {
        super(); // Chama o construtor padrão da classe pai (DigitalMedia)
        this.endYear = 0;
        this.seasons = new ArrayList<>();
    }

    /**
     * Construtor da classe Series.
     * 
     * @param title Título da série
     * @param genre Gênero da série
     * @param releaseYear Ano de lançamento da série (primeira temporada)
     * @param originalTitle Título original da série
     * @param whereToWatch Plataforma onde a série pode ser assistida
     * @param endYear Ano de término da série (0 se ainda estiver em produção)
     */
    public Series(String title, String genre, int releaseYear, String originalTitle,
                  String whereToWatch, int endYear) {
        super(title, genre, releaseYear, originalTitle, whereToWatch);
        this.endYear = endYear;
        this.seasons = new ArrayList<>();  // Inicializa a lista de temporadas vazia
    }

    public int getEndYear() {
        return endYear;
    }

    /**
     * Define um novo ano de término para a série.
     * 
     * @param endYear O novo ano de término da série
     */
    public void setEndYear(int endYear) {
        this.endYear = endYear;
    }

    public List<Season> getSeasons() {
        return seasons;
    }

    /**
     * Define a lista de temporadas da série.
     * 
     * @param seasons A nova lista de temporadas
     */
    public void setSeasons(List<Season> seasons) {
        this.seasons = seasons;
    }

    /**
     * Verifica se já existe uma temporada com o número especificado.
     * 
     * @param seasonNumber Número da temporada a verificar
     * @return true se já existe uma temporada com esse número, false caso contrário
     */
    public boolean hasSeasonNumber(int seasonNumber) {
        return seasons.stream()
                .anyMatch(s -> s.getSeasonNumber() == seasonNumber);
    }

    /**
     * Adiciona uma nova temporada à série.
     * 
     * @param seasonNumber Número da temporada
     * @param year Ano de lançamento
     * @param episodeCount Quantidade de episódios
     * @return true se a temporada foi adicionada com sucesso, false se já existia uma temporada com o mesmo número
     */
    public boolean addSeason(int seasonNumber, int year, int episodeCount) {
        if (hasSeasonNumber(seasonNumber)) {
            return false;
        }
        seasons.add(new Season(seasonNumber, year, episodeCount));
        return true;
    }

    /**
     * Adiciona uma avaliação para uma temporada específica da série.
     * Busca a temporada pelo número e adiciona a avaliação a ela.
     * Também marca a série como consumida.
     * 
     * @param seasonNumber Número da temporada a ser avaliada
     * @param rating Avaliação de 1 a 5 estrelas
     * @param comment Comentário sobre a temporada
     * @param date Data em que a temporada foi assistida
     * @throws IllegalArgumentException Se a temporada não for encontrada
     */
    public void addSeasonReview(int seasonNumber, int rating, String comment, LocalDate date) {
        // Stream API para buscar n° da temporada
        Season season = seasons.stream()
                .filter(s -> s.getSeasonNumber() == seasonNumber)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Temporada não encontrada"));

        // Adiciona a avaliação à temporada encontrada
        season.addReview(rating, comment, date);
        // Marca a série como consumida
        this.consumed = true;
    }

    /**
     * Calcula a média das avaliações de todas as temporadas da série.
     * Sobrescreve o método da classe pai para considerar as avaliações das temporadas, já que séries tem uma avaliação diferente.
     * Se não houver temporadas ou avaliações, retorna 0.
     */
    @Override
    public double getAverageRating() {
        if (seasons.isEmpty()) {
            return 0;
        }
        // Stream API para calcular a média das avaliações das temporadas
        // Pega apenas temporadas que têm avaliações (média > 0)
        return seasons.stream()
                .filter(s -> s.getAverageRating() > 0)
                .mapToDouble(Season::getAverageRating)
                .average()
                .orElse(0);
    }

    /**
     * Retorna uma representação em string da série.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        sb.append(String.format("\nAno de encerramento: %s\nTemporadas: %d\nMédia de avaliação: %.1f",
                endYear == 0 ? "Ainda rolando..." : endYear, seasons.size(), getAverageRating()));
        
        if (!seasons.isEmpty()) {
            sb.append("\nLista de temporadas:");
            for (Season season : seasons) {
                sb.append("\n  ").append(season.toString());
                
                // Exibição das reviews de cada temporada
                List<Review> seasonReviews = season.getReviews();
                if (!seasonReviews.isEmpty()) {
                    sb.append("\n    Reviews da temporada:");
                    for (Review review : seasonReviews) {
                        sb.append("\n      ").append(review.toString().replace("\n", "\n      "));
                    }
                }
            }
        }
        
        return sb.toString();
    }
}
