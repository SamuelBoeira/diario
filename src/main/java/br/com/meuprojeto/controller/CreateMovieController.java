package br.com.meuprojeto.controller;

import br.com.meuprojeto.model.CulturalData;
import br.com.meuprojeto.model.JsonPersistenceManager;
import br.com.meuprojeto.model.Movie;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controlador para a tela de criação e edição de Filmes.
 */
public class CreateMovieController {

    // Campos do FXML
    @FXML private TextField titleField;
    @FXML private TextField genreField;
    @FXML private TextField releaseYearField;
    @FXML private TextField originalTitleField;
    @FXML private TextField whereToWatchField;
    @FXML private TextField durationField;
    @FXML private TextField directorField;
    @FXML private TextField screenplayField;
    @FXML private Button backButton;
    @FXML private Button saveButton;

    private JsonPersistenceManager persistenceManager = new JsonPersistenceManager();
    private CulturalData culturalData;
    private Movie movieToEdit; // Se for nulo, é criação. Se não, é edição.

    /**
     * Inicializa o controlador, carregando os dados existentes.
     */
    @FXML
    public void initialize() {
        try {
            culturalData = persistenceManager.loadCulturalData();
        } catch (IOException e) {
            SceneManager.showAlert(Alert.AlertType.ERROR, "Erro", "Não foi possível carregar os dados existentes.");
            culturalData = new CulturalData();
        }
    }

    /**
     * Configura a tela para edição, preenchendo os campos com os dados de um filme existente.
     * @param movie O filme a ser editado.
     */
    public void setMovieToEdit(Movie movie) {
        this.movieToEdit = movie;
        titleField.setText(movie.getTitle());
        genreField.setText(movie.getGenre());
        releaseYearField.setText(String.valueOf(movie.getReleaseYear()));
        originalTitleField.setText(movie.getOriginalTitle());
        whereToWatchField.setText(movie.getWhereToWatch());
        durationField.setText(String.valueOf(movie.getDuration()));
        directorField.setText(movie.getDirector());
        screenplayField.setText(movie.getScreenplay());
        saveButton.setText("Salvar Alterações");
    }

    /**
     * Manipula o clique no botão "Salvar".
     * Cria um novo filme ou atualiza um existente e salva no arquivo JSON.
     */
    @FXML
    private void handleSave() {
        try {
            if (movieToEdit == null) { // Modo de Criação
                Movie newMovie = new Movie(
                        titleField.getText(), genreField.getText(), Integer.parseInt(releaseYearField.getText()),
                        originalTitleField.getText(), whereToWatchField.getText(), Integer.parseInt(durationField.getText()),
                        directorField.getText(), screenplayField.getText()
                );
                culturalData.getMovies().add(newMovie);
            } else { // Modo de Edição
                culturalData.getMovies().remove(movieToEdit); // Remove o antigo
                Movie updatedMovie = new Movie(
                        titleField.getText(), genreField.getText(), Integer.parseInt(releaseYearField.getText()),
                        originalTitleField.getText(), whereToWatchField.getText(), Integer.parseInt(durationField.getText()),
                        directorField.getText(), screenplayField.getText()
                );
                culturalData.getMovies().add(updatedMovie); // Adiciona o novo
            }

            persistenceManager.saveCulturalData(culturalData);
            SceneManager.showAlert(Alert.AlertType.INFORMATION, "Sucesso", "Filme salvo com sucesso!");
            handleBackButton();

        } catch (NumberFormatException e) {
            SceneManager.showAlert(Alert.AlertType.ERROR, "Erro de Formato", "Ano e Duração devem ser números.");
        } catch (IOException e) {
            SceneManager.showAlert(Alert.AlertType.ERROR, "Erro ao Salvar", "Falha ao salvar dados no arquivo.");
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