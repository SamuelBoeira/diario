package br.com.meuprojeto.controller;

import javafx.fxml.FXML;

/**
 * Controlador para a tela principal (main_view.fxml).
 * Gerencia a navegação para as principais seções da aplicação.
 */
public class MainController {

    /**
     * Abre a janela para selecionar o tipo de mídia a ser criada.
     */
    @FXML
    private void handleCreateNewMedia() {
        SceneManager.openModalWindow("create_media_selection_view.fxml", "Selecionar Tipo de Mídia");
    }

    /**
     * Abre a janela para listar todas as mídias.
     */
    @FXML
    private void handleListMedia() {
        SceneManager.openModalWindow("list_media_view.fxml", "Listar Mídias");
    }

    /**
     * Abre a janela de busca de mídias.
     */
    @FXML
    private void handleSearchMedia() {
        SceneManager.openModalWindow("search_view.fxml", "Buscar Mídia");
    }

    /**
     * Abre a janela para iniciar o processo de avaliação de uma mídia.
     */
    @FXML
    private void handleMakeReview() {
        SceneManager.openModalWindow("review_view.fxml", "Fazer Avaliação");
    }
}