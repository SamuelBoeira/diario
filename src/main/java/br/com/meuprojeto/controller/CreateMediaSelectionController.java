package br.com.meuprojeto.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * Controlador para a tela que permite ao usuário escolher qual tipo de mídia criar.
 */
public class CreateMediaSelectionController {

    @FXML private Button backButton;

    /**
     * Abre a janela de criação de Livro.
     */
    @FXML
    private void handleCreateBook() {
        SceneManager.openModalWindow("create_book_view.fxml", "Adicionar Novo Livro");
    }

    /**
     * Abre a janela de criação de Filme.
     */
    @FXML
    private void handleCreateMovie() {
        SceneManager.openModalWindow("create_movie_view.fxml", "Adicionar Novo Filme");
    }

    /**
     * Abre a janela de criação de Série.
     */
    @FXML
    private void handleCreateSeries() {
        SceneManager.openModalWindow("create_series_view.fxml", "Adicionar Nova Série");
    }

    /**
     * Fecha a janela atual, voltando para o menu principal.
     */
    @FXML
    private void handleBackButton() {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
    }
}