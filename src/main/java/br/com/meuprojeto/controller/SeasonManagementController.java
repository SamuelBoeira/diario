package br.com.meuprojeto.controller;

import br.com.meuprojeto.model.Actor;
import br.com.meuprojeto.model.Season;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Controlador para a janela modal de gerenciamento de Temporadas de uma Série.
 * COM VALIDAÇÃO DE NÚMEROS E DUPLICATAS.
 */
public class SeasonManagementController {

    @FXML private ListView<Season> seasonListView;
    @FXML private TextField seasonNumberField;
    @FXML private TextField releaseYearField;
    @FXML private TextField episodesField;
    @FXML private Button closeButton;
    @FXML private Button saveSeasonButton;

    private ObservableList<Season> seasonList = FXCollections.observableArrayList();
    private Season selectedSeason = null;

    @FXML
    public void initialize() {
        seasonListView.setItems(seasonList);
        
        seasonListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedSeason = newSelection;
                fillFieldsForEdit(newSelection);
            } else {
                clearFields();
            }
        });
    }

    public void setSeasons(List<Season> currentSeasons) {
        this.seasonList.setAll(currentSeasons);
    }

    public List<Season> getSeasons() {
        return new ArrayList<>(seasonList);
    }

    private void fillFieldsForEdit(Season season) {
        seasonNumberField.setText(String.valueOf(season.getSeasonNumber()));
        releaseYearField.setText(String.valueOf(season.getReleaseYear()));
        episodesField.setText(String.valueOf(season.getNumberOfEpisodes()));
        saveSeasonButton.setText("Salvar Alterações");
    }

    @FXML
    private void clearFields() {
        selectedSeason = null;
        seasonListView.getSelectionModel().clearSelection();
        seasonNumberField.clear();
        releaseYearField.clear();
        episodesField.clear();
        saveSeasonButton.setText("Adicionar Nova");
    }

    @FXML
    private void handleSaveSeason() {
        try {
            int seasonNumber = Integer.parseInt(seasonNumberField.getText());
            int releaseYear = Integer.parseInt(releaseYearField.getText());
            int numEpisodes = Integer.parseInt(episodesField.getText());

            if (seasonNumber <= 0 || releaseYear <= 0 || numEpisodes <= 0) {
                SceneManager.showAlert(Alert.AlertType.ERROR, "Erro de Validação", "Os campos numéricos devem ser maiores que zero.");
                return;
            }

            boolean numberExists = seasonList.stream()
                .anyMatch(s -> s.getSeasonNumber() == seasonNumber && s != selectedSeason);
            if (numberExists) {
                SceneManager.showAlert(Alert.AlertType.ERROR, "Erro de Validação", "Já existe uma temporada com este número.");
                return;
            }

            if (selectedSeason == null) { // --- MODO DE CRIAÇÃO ---
                Season newSeason = new Season(seasonNumber, releaseYear, numEpisodes);
                seasonList.add(newSeason);
            } else { // --- MODO DE EDIÇÃO ---
                selectedSeason.setSeasonNumber(seasonNumber);
                selectedSeason.setReleaseYear(releaseYear);
                selectedSeason.setNumberOfEpisodes(numEpisodes);
                seasonListView.refresh(); // Atualiza a lista para refletir as mudanças
            }
            clearFields();

        } catch (NumberFormatException e) {
            SceneManager.showAlert(Alert.AlertType.ERROR, "Erro de Formato", "Todos os campos devem ser números válidos.");
        }
    }
    
    @FXML
    private void handleManageSeasonCast() {
        Season seasonToManage = seasonListView.getSelectionModel().getSelectedItem();
        if (seasonToManage == null) {
            SceneManager.showAlert(Alert.AlertType.WARNING, "Aviso", "Por favor, selecione uma temporada para gerenciar o elenco.");
            return;
        }

        Object controller = SceneManager.openModalWindow("actor_management_view.fxml", "Gerenciar Elenco da Temporada " + seasonToManage.getSeasonNumber());
        if (controller instanceof ActorManagementController actorController) {
            actorController.setCast(seasonToManage.getCast());
            seasonToManage.setCast(actorController.getCast());
        }
    }

    @FXML
    private void handleRemoveSeason() {
        Season seasonToRemove = seasonListView.getSelectionModel().getSelectedItem();
        if (seasonToRemove != null) {
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("Confirmar Remoção");
            confirmation.setHeaderText("Remover Temporada " + seasonToRemove.getSeasonNumber());
            confirmation.setContentText("Você tem certeza que deseja remover esta temporada?");

            Optional<ButtonType> result = confirmation.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                seasonList.remove(seasonToRemove);
                clearFields();
            }
        } else {
            SceneManager.showAlert(Alert.AlertType.WARNING, "Aviso", "Nenhuma temporada selecionada para remoção.");
        }
    }
    
    @FXML
    private void handleClose() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}