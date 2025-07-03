package br.com.meuprojeto.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;

public class SceneManager {
    public static Object openModalWindow(String fxmlFile, String title) {
        try {
            String fxmlPath = "/br/com/meuprojeto/view/" + fxmlFile;
            URL fxmlUrl = SceneManager.class.getResource(fxmlPath);
            if (fxmlUrl == null) {
                showAlert(Alert.AlertType.ERROR, "Erro Crítico", "Não foi possível encontrar o arquivo de interface: " + fxmlFile);
                return null;
            }
            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            return loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erro de Carregamento", "Ocorreu um erro fatal ao tentar abrir a tela: " + e.getMessage());
            return null;
        }
    }

    public static void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}