package br.com.meuprojeto.controller;

import br.com.meuprojeto.model.CulturalData;
import br.com.meuprojeto.model.CulturalDataRepository;
import br.com.meuprojeto.model.JsonPersistenceManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;

public class MainController {

    @FXML
    private Label mainTitleLabel;
    @FXML
    private Button createNewMediaButton;
    @FXML
    private Button evaluateMediaButton;
    @FXML
    private Button searchSystemButton;

    private CulturalDataRepository culturalDataRepository;
    private JsonPersistenceManager persistenceManager;

    public MainController() {
        persistenceManager = new JsonPersistenceManager();
        try {
            CulturalData loadedData = persistenceManager.loadCulturalData();
            this.culturalDataRepository = new CulturalDataRepository(loadedData);
        } catch (IOException e) {
            System.err.println("Erro ao carregar dados culturais: " + e.getMessage());
            this.culturalDataRepository = new CulturalDataRepository(new CulturalData());
            showAlert(AlertType.ERROR, "Erro de Carregamento", "Não foi possível carregar os dados culturais. Uma nova sessão será iniciada.");
        }
    }

    @FXML
    private void initialize() {
        mainTitleLabel.setText("Diário Cultural");
    }

    @FXML
    private void handleCreateNewMediaButton() {
        System.out.println("Botão 'Criar nova mídia' clicado!");
        showAlert(AlertType.INFORMATION, "Funcionalidade em Desenvolvimento", "A funcionalidade de criação de nova mídia será implementada em breve.");
    }

    @FXML
    private void handleEvaluateMediaButton() {
        System.out.println("Botão 'Avaliar mídia' clicado!");
        showAlert(AlertType.INFORMATION, "Funcionalidade em Desenvolvimento", "A funcionalidade de avaliação de mídia será implementada em breve.");
    }

    @FXML
    private void handleSearchSystemButton() {
        System.out.println("Botão 'Sistema de Busca' clicado!");
        showAlert(AlertType.INFORMATION, "Funcionalidade em Desenvolvimento", "A funcionalidade de busca será implementada em breve.");
    }

    public void saveCulturalData() {
        try {
            persistenceManager.saveCulturalData(culturalDataRepository.getCulturalData());
            System.out.println("Dados culturais salvos com sucesso!");
        } catch (IOException e) {
            System.err.println("Erro ao salvar dados culturais: " + e.getMessage());
            showAlert(AlertType.ERROR, "Erro ao Salvar", "Não foi possível salvar os dados culturais: " + e.getMessage());
        }
    }

    private void showAlert(AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}


