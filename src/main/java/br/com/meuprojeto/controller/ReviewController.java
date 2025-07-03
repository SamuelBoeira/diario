package br.com.meuprojeto.controller;

import br.com.meuprojeto.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Controlador para a tela de seleção de mídia para avaliação.
 * Gerencia o fluxo completo de avaliação, incluindo o salvamento final.
 */
public class ReviewController {

    @FXML private TextField searchField;
    @FXML private ListView<Media> mediaListView;
    @FXML private Button backButton;

    private CulturalData culturalData;
    private JsonPersistenceManager persistenceManager = new JsonPersistenceManager();
    
    // **INÍCIO DA CORREÇÃO**
    // Esta linha estava faltando
    private ObservableList<Media> mediaList = FXCollections.observableArrayList();
    // **FIM DA CORREÇÃO**

    @FXML
    public void initialize() {
        try {
            culturalData = persistenceManager.loadCulturalData();
        } catch (IOException e) {
            SceneManager.showAlert(Alert.AlertType.ERROR, "Erro", "Não foi possível carregar os dados.");
            culturalData = new CulturalData();
        }
        mediaListView.setItems(mediaList);
        
        mediaListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Media item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getTitle() + " (" + item.getReleaseYear() + ")");
                }
            }
        });
    }

    @FXML
    private void handleSearch() {
        String query = searchField.getText().toLowerCase().trim();
        if (query.isEmpty()) {
            mediaList.clear();
            return;
        }

        List<Media> allMedia = Stream.concat(
            culturalData.getBooks().stream(),
            Stream.concat(culturalData.getMovies().stream(), culturalData.getSeries().stream())
        ).toList();

        List<Media> searchResults = allMedia.stream()
                .filter(media -> media.getTitle().toLowerCase().contains(query))
                .toList();

        mediaList.setAll(searchResults);
    }

    @FXML
    private void handleSelectMedia() {
        Media selectedMedia = mediaListView.getSelectionModel().getSelectedItem();
        if (selectedMedia == null) {
            SceneManager.showAlert(Alert.AlertType.WARNING, "Aviso", "Por favor, selecione uma mídia para avaliar.");
            return;
        }

        if (!selectedMedia.isConsumed()) {
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("Confirmação de Consumo");
            confirmation.setHeaderText("Você consumiu '" + selectedMedia.getTitle() + "'?");
            confirmation.setContentText("Para avaliar, você precisa confirmar que já assistiu/leu esta mídia.");
            Optional<ButtonType> result = confirmation.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                selectedMedia.setConsumed(true);
                selectedMedia.setConsumptionDate(LocalDate.now());
            } else {
                return;
            }
        }

        openReviewWindow(selectedMedia);
        
        saveData();
        handleBackButton();
    }
    
    private void openReviewWindow(Media media) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/com/meuprojeto/view/make_review_view.fxml"));
            Parent root = loader.load();
            MakeReviewController makeReviewController = loader.getController();
            makeReviewController.setMediaToReview(media);

            Stage stage = new Stage();
            stage.setTitle("Fazer Avaliação para: " + media.getTitle());
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
            SceneManager.showAlert(Alert.AlertType.ERROR, "Erro", "Não foi possível abrir a tela de avaliação.");
        }
    }
    
    private void saveData() {
        try {
            persistenceManager.saveCulturalData(culturalData);
        } catch (IOException e) {
            SceneManager.showAlert(Alert.AlertType.ERROR, "Erro ao Salvar", "Não foi possível salvar a avaliação no arquivo.");
        }
    }

    @FXML
    private void handleBackButton() {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
    }
}