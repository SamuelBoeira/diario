package br.com.meuprojeto.controller;

import br.com.meuprojeto.model.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Controlador para a janela de listagem de mídias.
 * Carrega todas as mídias salvas e permite a ordenação da lista.
 */
public class ListMediaController {

    @FXML private VBox mediaContainer;
    @FXML private ComboBox<String> sortOptionsComboBox;
    @FXML private Button backButton;

    private List<Media> allMedia;

    @FXML
    public void initialize() {
        // Opções de ordenação centralizadas e completas
        sortOptionsComboBox.setItems(FXCollections.observableArrayList(
                "Padrão (Por Tipo)",
                "Título (A-Z)",
                "Ano (Mais Recente)",
                "Ano (Mais Antigo)",
                "Melhor Avaliado",
                "Pior Avaliado"
        ));
        sortOptionsComboBox.setValue("Padrão (Por Tipo)");

        loadAllMedia();
        displayMedia(allMedia);
    }

    private void loadAllMedia() {
        this.allMedia = new ArrayList<>();
        try {
            JsonPersistenceManager persistenceManager = new JsonPersistenceManager();
            CulturalData culturalData = persistenceManager.loadCulturalData();
            
            allMedia.addAll(culturalData.getBooks());
            allMedia.addAll(culturalData.getMovies());
            allMedia.addAll(culturalData.getSeries());

        } catch (IOException e) {
            SceneManager.showAlert(Alert.AlertType.ERROR, "Erro de Carregamento", "Não foi possível carregar os dados para listagem.");
            e.printStackTrace();
        }
    }

    private void displayMedia(List<Media> mediaList) {
        mediaContainer.getChildren().clear();
        for (Media media : mediaList) {
            addMediaItemToView(media);
        }
    }

    @FXML
    private void handleSort() {
        String selection = sortOptionsComboBox.getValue();
        if (selection == null) return;

        List<Media> sortedList = new ArrayList<>(allMedia);

        // Lógica de ordenação expandida
        switch (selection) {
            case "Título (A-Z)":
                sortedList.sort(Comparator.comparing(Media::getTitle, String.CASE_INSENSITIVE_ORDER));
                break;
            case "Ano (Mais Recente)":
                sortedList.sort(Comparator.comparingInt(Media::getReleaseYear).reversed());
                break;
            case "Ano (Mais Antigo)":
                sortedList.sort(Comparator.comparingInt(Media::getReleaseYear));
                break;
            case "Melhor Avaliado":
                sortedList.sort(Comparator.comparingDouble(Media::getAverageRating).reversed());
                break;
            case "Pior Avaliado":
                sortedList.sort(Comparator.comparingDouble(Media::getAverageRating));
                break;
            default:
                // "Padrão (Por Tipo)" não requer ordenação extra,
                // pois os dados já são carregados nessa ordem.
                sortedList = new ArrayList<>(allMedia);
                break;
        }
        
        displayMedia(sortedList);
    }

    private void addMediaItemToView(Media media) {
        try {
            URL fxmlUrl = getClass().getResource("/br/com/meuprojeto/view/media_item_view.fxml");
            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Node mediaNode = loader.load();

            MediaItemController controller = loader.getController();
            controller.setData(media);
            // Mostra o botão de editar ao listar as mídias
            controller.showEditButton();

            mediaContainer.getChildren().add(mediaNode);
        } catch (IOException e) {
            System.err.println("Falha ao carregar media_item_view.fxml");
            e.printStackTrace();
        }
    }
    
    @FXML
    private void handleBackButton() {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
    }
}