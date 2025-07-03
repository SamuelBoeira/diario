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

/**
 * Controlador para a tela de busca de mídias.
 */
public class SearchController {

    @FXML private ComboBox<String> mediaTypeComboBox;
    @FXML private ComboBox<String> searchCriteriaComboBox;
    @FXML private TextField searchTextField;
    @FXML private VBox resultsContainer;
    @FXML private Button backButton;

    private CulturalData culturalData;

    /**
     * Inicializa o controlador, carregando os dados e configurando os menus.
     */
    @FXML
    public void initialize() {
        // Carrega os dados
        try {
            culturalData = new JsonPersistenceManager().loadCulturalData();
        } catch (IOException e) {
            SceneManager.showAlert(Alert.AlertType.ERROR, "Erro", "Não foi possível carregar os dados para busca.");
            culturalData = new CulturalData();
        }

        // Configura o menu de tipo de mídia
        mediaTypeComboBox.setItems(FXCollections.observableArrayList("Livro", "Filme", "Série"));
        // Adiciona um listener para mudar os critérios de busca quando o tipo de mídia muda
        mediaTypeComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> updateSearchCriteria());
    }

    /**
     * Atualiza o menu de critérios de busca com base no tipo de mídia selecionado.
     */
    private void updateSearchCriteria() {
        String mediaType = mediaTypeComboBox.getValue();
        if (mediaType == null) return;

        List<String> criteria = new ArrayList<>(List.of("Título", "Gênero", "Ano de Lançamento"));
        switch (mediaType) {
            case "Livro":
                criteria.add("Autor");
                criteria.add("Editora");
                break;
            case "Filme":
                criteria.add("Diretor");
                break;
            case "Série":
                // Séries não têm critérios de busca adicionais neste exemplo
                break;
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
        String query = searchTextField.getText().toLowerCase();

        if (mediaType == null || criteria == null || query.isEmpty()) {
            SceneManager.showAlert(Alert.AlertType.WARNING, "Aviso", "Por favor, selecione o tipo, critério e digite um termo para a busca.");
            return;
        }
        
        resultsContainer.getChildren().clear();
        
        List<? extends Media> searchResults = switch (mediaType) {
            case "Livro" -> searchBooks(criteria, query);
            case "Filme" -> searchMovies(criteria, query);
            case "Série" -> searchSeries(criteria, query);
            default -> new ArrayList<>();
        };

        if (searchResults.isEmpty()) {
            SceneManager.showAlert(Alert.AlertType.INFORMATION, "Busca", "Nenhum resultado encontrado.");
        } else {
            for (Media media : searchResults) {
                addMediaItemToView(media);
            }
        }
    }

    // Métodos de busca específicos para cada tipo
    private List<Book> searchBooks(String criteria, String query) {
        return culturalData.getBooks().stream().filter(book -> switch (criteria) {
            case "Título" -> book.getTitle().toLowerCase().contains(query);
            case "Gênero" -> book.getGenre().toLowerCase().contains(query);
            case "Ano de Lançamento" -> String.valueOf(book.getReleaseYear()).contains(query);
            case "Autor" -> book.getAuthor().toLowerCase().contains(query);
            case "Editora" -> book.getPublisher().toLowerCase().contains(query);
            default -> false;
        }).collect(Collectors.toList());
    }
    
    private List<Movie> searchMovies(String criteria, String query) {
        return culturalData.getMovies().stream().filter(movie -> switch (criteria) {
            case "Título" -> movie.getTitle().toLowerCase().contains(query);
            case "Gênero" -> movie.getGenre().toLowerCase().contains(query);
            case "Ano de Lançamento" -> String.valueOf(movie.getReleaseYear()).contains(query);
            case "Diretor" -> movie.getDirector().toLowerCase().contains(query);
            default -> false;
        }).collect(Collectors.toList());
    }

    private List<Series> searchSeries(String criteria, String query) {
        return culturalData.getSeries().stream().filter(series -> switch (criteria) {
            case "Título" -> series.getTitle().toLowerCase().contains(query);
            case "Gênero" -> series.getGenre().toLowerCase().contains(query);
            case "Ano de Lançamento" -> String.valueOf(series.getReleaseYear()).contains(query);
            default -> false;
        }).collect(Collectors.toList());
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
            controller.showEditButton(); // Torna o botão de edição visível

            resultsContainer.getChildren().add(mediaNode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Fecha a janela atual.
     */
    @FXML
    private void handleBackButton() {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
    }
}