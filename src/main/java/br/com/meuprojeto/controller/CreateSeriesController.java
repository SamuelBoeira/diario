package br.com.meuprojeto.controller;

import br.com.meuprojeto.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Controlador para a tela de criação e edição de Séries.
 * LÓGICA DE EDIÇÃO CORRIGIDA PARA MODIFICAR O OBJETO EXISTENTE.
 */
public class CreateSeriesController {

    @FXML private TextField titleField;
    @FXML private TextField genreField;
    @FXML private TextField releaseYearField;
    @FXML private TextField originalTitleField;
    @FXML private TextField whereToWatchField;
    @FXML private TextField endYearField;
    @FXML private Button backButton;
    @FXML private Button saveButton;

    private JsonPersistenceManager persistenceManager = new JsonPersistenceManager();
    private CulturalData culturalData;
    private Series seriesToEdit;
    
    // Listas locais para gerenciar os dados durante a edição
    private List<Actor> mainCast = new ArrayList<>();
    private List<Season> seasons = new ArrayList<>();

    @FXML
    public void initialize() {
        try {
            culturalData = persistenceManager.loadCulturalData();
        } catch (IOException e) {
            SceneManager.showAlert(Alert.AlertType.ERROR, "Erro", "Não foi possível carregar os dados existentes.");
            culturalData = new CulturalData();
        }
    }

    public void setSeriesToEdit(Series series) {
        this.seriesToEdit = series;
        titleField.setText(series.getTitle());
        genreField.setText(series.getGenre());
        releaseYearField.setText(String.valueOf(series.getReleaseYear()));
        originalTitleField.setText(series.getOriginalTitle());
        whereToWatchField.setText(series.getWhereToWatch());
        endYearField.setText(String.valueOf(series.getEndYear()));
        
        // Carrega os dados existentes para as listas locais para edição
        this.mainCast.clear();
        this.seasons.clear();
        this.mainCast.addAll(series.getCast());
        this.seasons.addAll(series.getSeasons());

        saveButton.setText("Salvar Alterações");
    }

    @FXML
    private void handleManageMainCast() {
        Object controller = SceneManager.openModalWindow("actor_management_view.fxml", "Gerenciar Elenco Principal");
        if (controller instanceof ActorManagementController actorController) {
            actorController.setCast(this.mainCast);
            this.mainCast = actorController.getCast();
        }
    }

    @FXML
    private void handleManageSeasons() {
        Object controller = SceneManager.openModalWindow("season_management_view.fxml", "Gerenciar Temporadas");
        if (controller instanceof SeasonManagementController seasonController) {
            seasonController.setSeasons(this.seasons);
            this.seasons = seasonController.getSeasons();
        }
    }
    
    @FXML
    private void handleSave() {
        try {
            String title = titleField.getText().trim();
            int releaseYear = Integer.parseInt(releaseYearField.getText());
            int endYear = Integer.parseInt(endYearField.getText());

            if (title.isEmpty()) {
                SceneManager.showAlert(Alert.AlertType.ERROR, "Erro de Validação", "O campo 'Título' não pode estar vazio.");
                return;
            }
            if (releaseYear < 0 || endYear < 0) {
                SceneManager.showAlert(Alert.AlertType.ERROR, "Erro de Validação", "Os anos não podem ser negativos.");
                return;
            }

            boolean titleExists = culturalData.getSeries().stream()
                .anyMatch(s -> s.getTitle().equalsIgnoreCase(title) && s != seriesToEdit);
            if (titleExists) {
                SceneManager.showAlert(Alert.AlertType.ERROR, "Erro de Validação", "Já existe uma série com este título.");
                return;
            }

            if (seriesToEdit == null) { // --- MODO DE CRIAÇÃO ---
                Series newSeries = new Series(
                    title, genreField.getText(), releaseYear,
                    originalTitleField.getText(), whereToWatchField.getText(), endYear
                );
                newSeries.setCast(this.mainCast);
                newSeries.setSeasons(this.seasons);
                culturalData.getSeries().add(newSeries);

            } else { // --- MODO DE EDIÇÃO (CORRIGIDO) ---
                seriesToEdit.setTitle(title);
                seriesToEdit.setGenre(genreField.getText());
                seriesToEdit.setReleaseYear(releaseYear);
                seriesToEdit.setOriginalTitle(originalTitleField.getText());
                seriesToEdit.setWhereToWatch(whereToWatchField.getText());
                seriesToEdit.setEndYear(endYear);
                // Atualiza o elenco e as temporadas NO OBJETO ORIGINAL
                seriesToEdit.getCast().clear();
                seriesToEdit.getCast().addAll(this.mainCast);
                seriesToEdit.getSeasons().clear();
                seriesToEdit.getSeasons().addAll(this.seasons);
            }

            persistenceManager.saveCulturalData(culturalData);
            SceneManager.showAlert(Alert.AlertType.INFORMATION, "Sucesso", "Série salva com sucesso!");
            handleBackButton();

        } catch (NumberFormatException e) {
            SceneManager.showAlert(Alert.AlertType.ERROR, "Erro de Formato", "Ano de Lançamento e Ano de Término devem ser números.");
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