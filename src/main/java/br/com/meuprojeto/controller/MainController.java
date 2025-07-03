package br.com.meuprojeto.controller;
import javafx.fxml.FXML;

public class MainController {
    @FXML private void handleCreateNewMedia() {
        SceneManager.openModalWindow("create_media_selection_view.fxml", "Selecionar Tipo de Mídia");
    }
    @FXML private void handleListMedia() {
        SceneManager.openModalWindow("list_media_view.fxml", "Listar Mídias");
    }
    @FXML private void handleSearchMedia() {
        SceneManager.openModalWindow("search_view.fxml", "Buscar Mídia");
    }
}