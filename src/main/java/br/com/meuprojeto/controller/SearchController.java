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
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Controlador para a tela de busca de mídias.
 * Permite filtrar por tipo, critério e termo. A funcionalidade de ordenação foi movida.
 */
public class SearchController {

    @FXML private ComboBox<String> mediaTypeComboBox;
    @FXML private ComboBox<String> searchCriteriaComboBox;
    @FXML private TextField searchTextField;
    @FXML private VBox resultsContainer;
    @FXML private Button backButton;

    private CulturalData culturalData;

    @FXML
    public void initialize() {
        try {
            culturalData = new JsonPersistenceManager().loadCulturalData();
        } catch (IOException e) {
            SceneManager.showAlert(Alert.AlertType.ERROR, "Erro", "Não foi possível carregar os dados para busca.");
            culturalData = new CulturalData();
        }

        mediaTypeComboBox.setItems(FXCollections.observableArrayList("Todos", "Livro", "Filme", "Série"));
        mediaTypeComboBox.setValue("Todos");

        mediaTypeComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> updateSearchCriteria());

        updateSearchCriteria();
    }

    private void updateSearchCriteria() {
        String mediaType = mediaTypeComboBox.getValue();
        if (mediaType == null) return;

        List<String> criteria = new ArrayList<>(List.of("Título", "Gênero", "Ano de Lançamento"));

        if (mediaType.equals("Filme") || mediaType.equals("Série") || mediaType.equals("Todos")) {
            criteria.add("Ator");
        }
        
        searchCriteriaComboBox.setItems(FXCollections.observableArrayList(criteria));
        searchCriteriaComboBox.setValue("Título");
    }

    @FXML
    private void handleSearch() {
        String mediaType = mediaTypeComboBox.getValue();
        String criteria = searchCriteriaComboBox.getValue();
        String query = searchTextField.getText().toLowerCase().trim();

        if (mediaType == null || criteria == null || query.isEmpty()) {
            SceneManager.showAlert(Alert.AlertType.WARNING, "Aviso", "Por favor, selecione o tipo, critério e digite um termo para a busca.");
            return;
        }

        resultsContainer.getChildren().clear();

        List<Media> searchResults = findMedia(mediaType, criteria, query);

        if (searchResults.isEmpty()) {
            SceneManager.showAlert(Alert.AlertType.INFORMATION, "Busca", "Nenhum resultado encontrado.");
        } else {
            for (Media media : searchResults) {
                addMediaItemToView(media);
            }
        }
    }

    private List<Media> findMedia(String mediaType, String criteria, String query) {
        Stream<Media> allMediaStream = Stream.concat(
            culturalData.getBooks().stream(),
            Stream.concat(
                culturalData.getMovies().stream(),
                culturalData.getSeries().stream()
            )
        );

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

        return allMediaStream.filter(media -> mediaMatches(media, criteria, query))
                             .collect(Collectors.toList());
    }

    private boolean mediaMatches(Media media, String criteria, String query) {
        return switch (criteria) {
            case "Título" -> media.getTitle().toLowerCase().contains(query);
            case "Gênero" -> media.getGenre().toLowerCase().contains(query);
            case "Ano de Lançamento" -> String.valueOf(media.getReleaseYear()).contains(query);
            case "Ator" -> {
                if (media instanceof DigitalMedia digitalMedia) {
                    boolean inMainCast = digitalMedia.getCast().stream()
                        .anyMatch(actor -> actor.getName().toLowerCase().contains(query));
                    if (inMainCast) yield true;

                    if (digitalMedia instanceof Series series) {
                        yield series.getSeasons().stream()
                            .flatMap(season -> season.getCast().stream())
                            .anyMatch(actor -> actor.getName().toLowerCase().contains(query));
                    }
                }
                yield false;
            }
            default -> false;
        };
    }
    
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