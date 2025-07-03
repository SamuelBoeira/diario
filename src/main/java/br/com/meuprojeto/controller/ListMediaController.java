package br.com.meuprojeto.controller;

import br.com.meuprojeto.model.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Controlador para a janela de listagem de mídias.
 * Carrega todas as mídias salvas e permite a ordenação da lista.
 */
public class ListMediaController {

    @FXML private VBox mediaContainer;
    @FXML private ComboBox<String> sortOptionsComboBox;
    @FXML private Button backButton;

    private List<Media> allMedia;

    /**
     * Método de inicialização do controlador.
     * Carrega os dados, configura as opções de ordenação e exibe a lista inicial.
     */
    @FXML
    public void initialize() {
        // Configura as opções no ComboBox (menu de ordenação)
        sortOptionsComboBox.setItems(FXCollections.observableArrayList(
                "Padrão (Por Tipo)",
                "Título (A-Z)",
                "Ano (Mais Recente)",
                "Ano (Mais Antigo)"
        ));
        // Define um valor padrão
        sortOptionsComboBox.setValue("Padrão (Por Tipo)");

        // Carrega todas as mídias
        loadAllMedia();
        // Exibe as mídias na tela
        displayMedia(allMedia);
    }

    /**
     * Carrega todas as mídias (livros, filmes e séries) do arquivo JSON
     * e as armazena em uma única lista `allMedia`.
     */
    private void loadAllMedia() {
        this.allMedia = new ArrayList<>();
        try {
            JsonPersistenceManager persistenceManager = new JsonPersistenceManager();
            CulturalData culturalData = persistenceManager.loadCulturalData();
            
            allMedia.addAll(culturalData.getBooks());
            allMedia.addAll(culturalData.getMovies());
            allMedia.addAll(culturalData.getSeries());

        } catch (IOException e) {
            SceneManager.showAlert(Alert.AlertType.ERROR, "Erro de Carregamento", "Não foi possível carregar os dados para listagem.");
            e.printStackTrace();
        }
    }

    /**
     * Limpa a visualização atual e exibe uma nova lista de mídias.
     * @param mediaList A lista de mídias a ser exibida.
     */
    private void displayMedia(List<Media> mediaList) {
        mediaContainer.getChildren().clear();
        for (Media media : mediaList) {
            addMediaItemToView(media);
        }
    }

    /**
     * Manipula a seleção de uma nova opção de ordenação.
     * Ordena a lista `allMedia` de acordo com a opção escolhida e atualiza a visualização.
     */
    @FXML
    private void handleSort() {
        String selection = sortOptionsComboBox.getValue();
        if (selection == null) return;

        // Cria uma cópia da lista para não modificar a original permanentemente
        List<Media> sortedList = new ArrayList<>(allMedia);

        switch (selection) {
            case "Título (A-Z)":
                sortedList.sort(Comparator.comparing(Media::getTitle, String.CASE_INSENSITIVE_ORDER));
                break;
            case "Ano (Mais Recente)":
                sortedList.sort(Comparator.comparingInt(Media::getReleaseYear).reversed());
                break;
            case "Ano (Mais Antigo)":
                sortedList.sort(Comparator.comparingInt(Media::getReleaseYear));
                break;
            default: // "Padrão (Por Tipo)"
                // A lista `allMedia` já está ordenada por tipo por padrão
                sortedList = allMedia;
                break;
        }
        
        displayMedia(sortedList);
    }

    /**
     * Carrega a "caixa" de item de mídia e a adiciona ao contêiner principal.
     * @param media O objeto de mídia a ser exibido.
     */
    private void addMediaItemToView(Media media) {
        try {
            URL fxmlUrl = getClass().getResource("/br/com/meuprojeto/view/media_item_view.fxml");
            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Node mediaNode = loader.load();

            MediaItemController controller = loader.getController();
            controller.setData(media);

            mediaContainer.getChildren().add(mediaNode);
        } catch (IOException e) {
            System.err.println("Falha ao carregar media_item_view.fxml");
            e.printStackTrace();
        }
    }
    
    /**
     * Fecha a janela atual, voltando para o menu anterior.
     */
    @FXML
    private void handleBackButton() {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
    }
}