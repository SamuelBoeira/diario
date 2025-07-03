package br.com.meuprojeto.controller;

import br.com.meuprojeto.model.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Controlador para a tela de busca de mídias.
 * Permite filtrar por tipo, critério e termo, e ordenar os resultados.
 */
public class SearchController {

    @FXML private ComboBox<String> mediaTypeComboBox;
    @FXML private ComboBox<String> searchCriteriaComboBox;
    @FXML private ComboBox<String> sortOrderComboBox; // Novo ComboBox para ordenação
    @FXML private TextField searchTextField;
    @FXML private VBox resultsContainer;
    @FXML private Button backButton;

    private CulturalData culturalData;

    /**
     * Inicializa o controlador, carregando os dados e configurando os menus.
     */
    @FXML
    public void initialize() {
        try {
            culturalData = new JsonPersistenceManager().loadCulturalData();
        } catch (IOException e) {
            SceneManager.showAlert(Alert.AlertType.ERROR, "Erro", "Não foi possível carregar os dados para busca.");
            culturalData = new CulturalData();
        }

        // Configura o menu de tipo de mídia
        mediaTypeComboBox.setItems(FXCollections.observableArrayList("Todos", "Livro", "Filme", "Série"));
        mediaTypeComboBox.setValue("Todos");

        // Adiciona um listener para mudar os critérios de busca quando o tipo de mídia muda
        mediaTypeComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> updateSearchCriteria());

        // Configura as opções de ordenação
        sortOrderComboBox.setItems(FXCollections.observableArrayList(
                "Padrão",
                "Título (A-Z)",
                "Melhor Avaliado",
                "Pior Avaliado"
        ));
        sortOrderComboBox.setValue("Padrão");

        updateSearchCriteria();
    }

    /**
     * Atualiza o menu de critérios de busca com base no tipo de mídia selecionado.
     */
    private void updateSearchCriteria() {
        String mediaType = mediaTypeComboBox.getValue();
        if (mediaType == null) return;

        // Critérios comuns a todos
        List<String> criteria = new ArrayList<>(List.of("Título", "Gênero", "Ano de Lançamento"));

        // Adiciona critério de Ator se o tipo for Filme, Série ou Todos
        if (mediaType.equals("Filme") || mediaType.equals("Série") || mediaType.equals("Todos")) {
            criteria.add("Ator");
        }
        
        searchCriteriaComboBox.setItems(FXCollections.observableArrayList(criteria));
        searchCriteriaComboBox.setValue("Título"); // Define um padrão
    }

    /**
     * Executa a busca com base nos filtros selecionados e exibe os resultados.
     */
    @FXML
    private void handleSearch() {
        String mediaType = mediaTypeComboBox.getValue();
        String criteria = searchCriteriaComboBox.getValue();
        String sortOrder = sortOrderComboBox.getValue();
        String query = searchTextField.getText().toLowerCase().trim();

        if (mediaType == null || criteria == null || query.isEmpty()) {
            SceneManager.showAlert(Alert.AlertType.WARNING, "Aviso", "Por favor, selecione o tipo, critério e digite um termo para a busca.");
            return;
        }

        resultsContainer.getChildren().clear();

        // Coleta os resultados da busca
        List<Media> searchResults = findMedia(mediaType, criteria, query);

        // Ordena os resultados
        sortMedia(searchResults, sortOrder);

        if (searchResults.isEmpty()) {
            SceneManager.showAlert(Alert.AlertType.INFORMATION, "Busca", "Nenhum resultado encontrado.");
        } else {
            for (Media media : searchResults) {
                addMediaItemToView(media);
            }
        }
    }

    /**
     * Encontra mídias com base no tipo, critério e termo de busca.
     * @return Uma lista de mídias que correspondem à busca.
     */
    private List<Media> findMedia(String mediaType, String criteria, String query) {
        // Começa com um stream de todas as mídias
        Stream<Media> allMediaStream = Stream.concat(
            culturalData.getBooks().stream(),
            Stream.concat(
                culturalData.getMovies().stream(),
                culturalData.getSeries().stream()
            )
        );

        // Filtra pelo tipo de mídia, se não for "Todos"
        if (!mediaType.equals("Todos")) {
            allMediaStream = allMediaStream.filter(media -> {
                return switch (mediaType) {
                    case "Livro" -> media instanceof Book;
                    case "Filme" -> media instanceof Movie;
                    case "Série" -> media instanceof Series;
                    default -> false;
                };
            });
        }

        // Filtra pelo critério de busca
        return allMediaStream.filter(media -> mediaMatches(media, criteria, query))
                             .collect(Collectors.toList());
    }

    /**
     * Verifica se uma mídia corresponde a um critério de busca.
     * @return true se a mídia corresponder, false caso contrário.
     */
    private boolean mediaMatches(Media media, String criteria, String query) {
        return switch (criteria) {
            case "Título" -> media.getTitle().toLowerCase().contains(query);
            case "Gênero" -> media.getGenre().toLowerCase().contains(query);
            case "Ano de Lançamento" -> String.valueOf(media.getReleaseYear()).contains(query);
            case "Ator" -> {
                if (media instanceof DigitalMedia digitalMedia) {
                    // Busca no elenco principal
                    boolean inMainCast = digitalMedia.getCast().stream()
                        .anyMatch(actor -> actor.getName().toLowerCase().contains(query));
                    if (inMainCast) yield true;

                    // Se for série, busca no elenco de cada temporada
                    if (digitalMedia instanceof Series series) {
                        yield series.getSeasons().stream()
                            .flatMap(season -> season.getCast().stream())
                            .anyMatch(actor -> actor.getName().toLowerCase().contains(query));
                    }
                }
                yield false; // Não é DigitalMedia ou não encontrou o ator
            }
            default -> false;
        };
    }
    
    /**
     * Ordena a lista de mídias de acordo com a opção selecionada.
     */
    private void sortMedia(List<Media> mediaList, String sortOrder) {
        switch (sortOrder) {
            case "Título (A-Z)" -> mediaList.sort(Comparator.comparing(Media::getTitle, String.CASE_INSENSITIVE_ORDER));
            case "Melhor Avaliado" -> mediaList.sort(Comparator.comparingDouble(Media::getAverageRating).reversed());
            case "Pior Avaliado" -> mediaList.sort(Comparator.comparingDouble(Media::getAverageRating));
            default -> { /* Padrão, não faz nada */ }
        }
    }
    
    /**
     * Adiciona um item de mídia à lista de resultados, tornando o botão "Editar" visível.
     */
    private void addMediaItemToView(Media media) {
        try {
            URL fxmlUrl = getClass().getResource("/br/com/meuprojeto/view/media_item_view.fxml");
            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Node mediaNode = loader.load();

            MediaItemController controller = loader.getController();
            controller.setData(media);
            controller.showEditButton();

            resultsContainer.getChildren().add(mediaNode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBackButton() {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
    }
}