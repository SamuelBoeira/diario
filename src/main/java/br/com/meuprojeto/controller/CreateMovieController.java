package br.com.meuprojeto.controller;

import br.com.meuprojeto.model.Actor;
import br.com.meuprojeto.model.CulturalData;
import br.com.meuprojeto.model.JsonPersistenceManager;
import br.com.meuprojeto.model.Movie;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Controlador para a tela de criação e edição de Filmes.
 * LÓGICA DE EDIÇÃO CORRIGIDA PARA MODIFICAR O OBJETO EXISTENTE.
 */
public class CreateMovieController {

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
    private Movie movieToEdit;
    private List<Actor> currentCast = new ArrayList<>();

    @FXML
    public void initialize() {
        try {
            culturalData = persistenceManager.loadCulturalData();
        } catch (IOException e) {
            SceneManager.showAlert(Alert.AlertType.ERROR, "Erro", "Não foi possível carregar os dados existentes.");
            culturalData = new CulturalData();
        }
    }

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
        
        this.currentCast.clear();
        this.currentCast.addAll(movie.getCast());
        saveButton.setText("Salvar Alterações");
    }
    
    @FXML
    private void handleManageCast() {
        Object controller = SceneManager.openModalWindow("actor_management_view.fxml", "Gerenciar Elenco do Filme");

        if (controller instanceof ActorManagementController actorController) {
            actorController.setCast(this.currentCast);
            this.currentCast = actorController.getCast();
        }
    }

    @FXML
    private void handleSave() {
        try {
            String title = titleField.getText().trim();
            int releaseYear = Integer.parseInt(releaseYearField.getText());
            int duration = Integer.parseInt(durationField.getText());

            if (title.isEmpty()) {
                SceneManager.showAlert(Alert.AlertType.ERROR, "Erro de Validação", "O campo 'Título' não pode estar vazio.");
                return;
            }
            if (releaseYear < 0 || duration < 0) {
                SceneManager.showAlert(Alert.AlertType.ERROR, "Erro de Validação", "Ano e Duração não podem ser negativos.");
                return;
            }

            boolean titleExists = culturalData.getMovies().stream()
                .anyMatch(m -> m.getTitle().equalsIgnoreCase(title) && m != movieToEdit);
            if (titleExists) {
                SceneManager.showAlert(Alert.AlertType.ERROR, "Erro de Validação", "Já existe um filme com este título.");
                return;
            }
            
            if (movieToEdit == null) { // --- MODO DE CRIAÇÃO ---
                 Movie newMovie = new Movie(
                    title, genreField.getText(), releaseYear,
                    originalTitleField.getText(), whereToWatchField.getText(), duration,
                    directorField.getText(), screenplayField.getText()
                );
                newMovie.setCast(this.currentCast);
                culturalData.getMovies().add(newMovie);

            } else { // --- MODO DE EDIÇÃO (CORRIGIDO) ---
                movieToEdit.setTitle(title);
                movieToEdit.setGenre(genreField.getText());
                movieToEdit.setReleaseYear(releaseYear);
                movieToEdit.setOriginalTitle(originalTitleField.getText());
                movieToEdit.setWhereToWatch(whereToWatchField.getText());
                movieToEdit.setDuration(duration);
                movieToEdit.setDirector(directorField.getText());
                movieToEdit.setScreenplay(screenplayField.getText());
                movieToEdit.getCast().clear();
                movieToEdit.getCast().addAll(this.currentCast);
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

    @FXML
    private void handleBackButton() {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
    }
}