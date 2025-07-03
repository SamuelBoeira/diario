package br.com.meuprojeto.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            URL fxmlUrl = getClass().getResource("/br/com/meuprojeto/view/main_view.fxml");
            if (fxmlUrl == null) {
                System.err.println("ERRO CRÍTICO: Não foi possível encontrar 'main_view.fxml'.");
                return;
            }
            Parent root = FXMLLoader.load(fxmlUrl);
            primaryStage.setTitle("Diário Cultural");
            primaryStage.setScene(new Scene(root, 600, 400));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}