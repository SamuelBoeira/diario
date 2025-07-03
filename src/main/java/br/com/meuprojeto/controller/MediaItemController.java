package br.com.meuprojeto.controller;

import br.com.meuprojeto.model.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert; // IMPORT ADICIONADO AQUI
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;

/**
 * Controlador para o item visual individual de uma mídia (media_item_view.fxml).
 * Cada "caixa" na tela de listagem ou busca terá uma instância deste controlador.
 */
public class MediaItemController {

    @FXML private Label titleLabel;
    @FXML private Label typeLabel;
    @FXML private Label detailsLabel;
    @FXML private VBox mediaBox;
    @FXML private Button editButton;

    private Media media;

    public void setData(Media media) {
        this.media = media;
        titleLabel.setText(media.getTitle() + " (" + media.getReleaseYear() + ")");
        String details = "Gênero: " + media.getGenre() + "\n";

        if (media instanceof Book book) {
            typeLabel.setText("Livro");
            details += "Autor: " + book.getAuthor();
            mediaBox.setStyle("-fx-background-color: #E8F8F5; -fx-border-color: #16A085; -fx-border-width: 1.5; -fx-background-radius: 5; -fx-border-radius: 5;");
        } else if (media instanceof Movie movie) {
            typeLabel.setText("Filme");
            details += "Diretor: " + movie.getDirector();
            mediaBox.setStyle("-fx-background-color: #FEF9E7; -fx-border-color: #F1C40F; -fx-border-width: 1.5; -fx-background-radius: 5; -fx-border-radius: 5;");
        } else if (media instanceof Series series) {
            typeLabel.setText("Série");
            details += "Onde Assistir: " + series.getWhereToWatch();
            mediaBox.setStyle("-fx-background-color: #EBF5FB; -fx-border-color: #3498DB; -fx-border-width: 1.5; -fx-background-radius: 5; -fx-border-radius: 5;");
        }
        detailsLabel.setText(details);
    }

    public void showEditButton() {
        editButton.setVisible(true);
    }

    @FXML
    private void handleEdit() {
        if (media instanceof Book) {
            openEditWindow("create_book_view.fxml", "Editar Livro", media);
        } else if (media instanceof Movie) {
            openEditWindow("create_movie_view.fxml", "Editar Filme", media);
        } else if (media instanceof Series) {
            openEditWindow("create_series_view.fxml", "Editar Série", media);
        }
    }

    private void openEditWindow(String fxmlFile, String title, Media mediaData) {
        try {
            String fxmlPath = "/br/com/meuprojeto/view/" + fxmlFile;
            URL fxmlUrl = getClass().getResource(fxmlPath);

            if (fxmlUrl == null) {
                SceneManager.showAlert(Alert.AlertType.ERROR, "Erro", "Não foi possível encontrar o FXML de edição: " + fxmlFile);
                return;
            }

            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Parent root = loader.load();

            if (mediaData instanceof Book book) {
                CreateBookController controller = loader.getController();
                controller.setBookToEdit(book);
            } else if (mediaData instanceof Movie movie) {
                CreateMovieController controller = loader.getController();
                controller.setMovieToEdit(movie);
            } else if (mediaData instanceof Series series) {
                CreateSeriesController controller = loader.getController();
                controller.setSeriesToEdit(series);
            }

            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
            SceneManager.showAlert(Alert.AlertType.ERROR, "Erro", "Não foi possível abrir a tela de edição.");
        }
    }
}