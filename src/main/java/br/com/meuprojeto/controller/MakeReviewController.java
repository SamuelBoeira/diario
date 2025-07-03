package br.com.meuprojeto.controller;

import br.com.meuprojeto.model.*;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Optional;

/**
 * Controlador para a tela de efetivação da avaliação (review).
 * Apenas modifica os objetos em memória. O salvamento é feito pelo controlador que o chamou.
 */
public class MakeReviewController {

    @FXML private VBox mainContainer;
    @FXML private Button backButton;

    private Media mediaToReview;

    public void setMediaToReview(Media media) {
        this.mediaToReview = media;
        buildReviewUI();
    }

    private void buildReviewUI() {
        mainContainer.getChildren().clear();

        if (mediaToReview instanceof Series series) {
            Label title = new Label("Avaliar Temporadas de: " + series.getTitle());
            title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
            mainContainer.getChildren().add(title);

            if (series.getSeasons().isEmpty()) {
                mainContainer.getChildren().add(new Label("Esta série ainda não possui temporadas cadastradas."));
            } else {
                for (Season season : series.getSeasons()) {
                    mainContainer.getChildren().add(createSeasonReviewBox(season));
                }
            }
        } else {
            mainContainer.getChildren().add(createSingleReviewBox(mediaToReview));
        }
    }

    private VBox createSingleReviewBox(Media media) {
        VBox reviewBox = new VBox(10);
        Label title = new Label("Sua Avaliação para: " + media.getTitle());
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Review existingReview = media.getReviews().stream().findFirst().orElse(new Review(0, ""));

        HBox ratingBox = createRatingBox(existingReview.getRating(), existingReview::setRating);

        Label commentLabel = new Label("Comentário (opcional):");
        TextArea commentArea = new TextArea(existingReview.getComment());
        commentArea.setWrapText(true);
        commentArea.setPrefRowCount(4);

        Button saveButton = new Button("Salvar Avaliação");
        saveButton.setOnAction(e -> {
            media.getReviews().clear();
            existingReview.setComment(commentArea.getText());
            if (existingReview.getRating() > 0) {
                media.getReviews().add(existingReview);
            }
            handleBackButton(); // Apenas fecha a janela
        });
        
        reviewBox.getChildren().addAll(title, ratingBox, commentLabel, commentArea, saveButton);
        return reviewBox;
    }

    private HBox createSeasonReviewBox(Season season) {
        HBox seasonBox = new HBox(20);
        seasonBox.setAlignment(Pos.CENTER_LEFT);
        seasonBox.setStyle("-fx-border-color: #ccc; -fx-padding: 10; -fx-border-radius: 5;");
        
        Label seasonLabel = new Label(season.toString());
        seasonLabel.setPrefWidth(200);

        Review existingReview = season.getReviews().stream().findFirst().orElse(new Review(0, ""));
        
        HBox ratingBox = createRatingBox(existingReview.getRating(), rating -> {
            existingReview.setRating(rating);
            // Garante que a review (mesmo que sem comentário) esteja na lista se tiver nota
            if (!season.getReviews().contains(existingReview) && rating > 0) {
                season.getReviews().clear(); // Garante uma única review por temporada
                season.getReviews().add(existingReview);
            } else if (rating == 0) {
                season.getReviews().remove(existingReview);
            }
        });
        
        Button commentButton = new Button("Adicionar/Editar Comentário");
        commentButton.setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog(existingReview.getComment());
            dialog.setTitle("Comentário para " + season);
            dialog.setHeaderText("Digite seu comentário sobre a " + season);
            dialog.setContentText("Comentário:");

            Optional<String> result = dialog.showAndWait();
            result.ifPresent(existingReview::setComment);
        });
        
        seasonBox.getChildren().addAll(seasonLabel, ratingBox, commentButton);
        return seasonBox;
    }

    private HBox createRatingBox(int currentRating, java.util.function.Consumer<Integer> onRate) {
        HBox ratingBox = new HBox(5);
        for (int i = 1; i <= 5; i++) {
            Button starButton = new Button(i <= currentRating ? "★" : "☆");
            starButton.setStyle("-fx-background-color: transparent; -fx-font-size: 20px;");
            final int rating = i;
            starButton.setOnAction(e -> {
                onRate.accept(rating);
                for (int j = 0; j < 5; j++) {
                    Button btn = (Button) ratingBox.getChildren().get(j);
                    btn.setText(j < rating ? "★" : "☆");
                }
            });
            ratingBox.getChildren().add(starButton);
        }
        return ratingBox;
    }

    @FXML
    private void handleBackButton() {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
    }
}