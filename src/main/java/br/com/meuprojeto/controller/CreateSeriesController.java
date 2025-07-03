package br.com.meuprojeto.controller;

import br.com.meuprojeto.model.CulturalData;
import br.com.meuprojeto.model.JsonPersistenceManager;
import br.com.meuprojeto.model.Series;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controlador para a tela de criação e edição de Séries.
 */
public class CreateSeriesController {

    // Campos do FXML
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
    private Series seriesToEdit; // Se for nulo, é criação. Se não, é edição.

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
     * Configura a tela para edição, preenchendo os campos com os dados de uma série existente.
     * @param series A série a ser editada.
     */
    public void setSeriesToEdit(Series series) {
        this.seriesToEdit = series;
        titleField.setText(series.getTitle());
        genreField.setText(series.getGenre());
        releaseYearField.setText(String.valueOf(series.getReleaseYear()));
        originalTitleField.setText(series.getOriginalTitle());
        whereToWatchField.setText(series.getWhereToWatch());
        endYearField.setText(String.valueOf(series.getEndYear()));
        saveButton.setText("Salvar Alterações");
    }

    /**
     * Manipula o clique no botão "Salvar".
     * Cria uma nova série ou atualiza uma existente e salva no arquivo JSON.
     */
    @FXML
    private void handleSave() {
        try {
            if (seriesToEdit == null) { // Modo de Criação
                Series newSeries = new Series(
                        titleField.getText(), genreField.getText(), Integer.parseInt(releaseYearField.getText()),
                        originalTitleField.getText(), whereToWatchField.getText(), Integer.parseInt(endYearField.getText())
                );
                culturalData.getSeries().add(newSeries);
            } else { // Modo de Edição
                culturalData.getSeries().remove(seriesToEdit); // Remove a antiga
                Series updatedSeries = new Series(
                        titleField.getText(), genreField.getText(), Integer.parseInt(releaseYearField.getText()),
                        originalTitleField.getText(), whereToWatchField.getText(), Integer.parseInt(endYearField.getText())
                );
                culturalData.getSeries().add(updatedSeries); // Adiciona a nova
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

    /**
     * Fecha a janela atual.
     */
    @FXML
    private void handleBackButton() {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
    }
}