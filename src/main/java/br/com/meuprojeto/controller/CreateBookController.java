package br.com.meuprojeto.controller;

import br.com.meuprojeto.model.Book;
import br.com.meuprojeto.model.CulturalData;
import br.com.meuprojeto.model.JsonPersistenceManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Controlador para a tela de criação e edição de Livros.
 */
public class CreateBookController {
    
    @FXML private TextField titleField;
    @FXML private TextField genreField;
    @FXML private TextField releaseYearField;
    @FXML private TextField authorField;
    @FXML private TextField publisherField;
    @FXML private TextField isbnField;
    @FXML private Button backButton;
    @FXML private Button saveButton;

    private JsonPersistenceManager persistenceManager = new JsonPersistenceManager();
    private CulturalData culturalData;
    private Book bookToEdit;

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
     * Configura a tela para edição, preenchendo os campos com os dados de um livro existente.
     * @param book O livro a ser editado.
     */
    public void setBookToEdit(Book book) {
        this.bookToEdit = book;
        titleField.setText(book.getTitle());
        genreField.setText(book.getGenre());
        releaseYearField.setText(String.valueOf(book.getReleaseYear()));
        authorField.setText(book.getAuthor());
        publisherField.setText(book.getPublisher());
        isbnField.setText(book.getIsbn());
        saveButton.setText("Salvar Alterações");
    }

    /**
     * Manipula o clique no botão "Salvar".
     */
    @FXML
    private void handleSave() {
        try {
            // Cria um novo objeto com os dados da tela
            Book updatedBook = new Book(
                titleField.getText(),
                genreField.getText(),
                Integer.parseInt(releaseYearField.getText()),
                authorField.getText(),
                publisherField.getText(),
                isbnField.getText(),
                (bookToEdit != null) ? bookToEdit.isOwned() : false
            );

            if (bookToEdit != null) {
                // Modo de Edição: remove o antigo para não duplicar
                culturalData.getBooks().remove(bookToEdit);
            }
            
            // Adiciona o livro (novo ou atualizado) à lista
            culturalData.getBooks().add(updatedBook);
            
            persistenceManager.saveCulturalData(culturalData);
            SceneManager.showAlert(Alert.AlertType.INFORMATION, "Sucesso", "Livro salvo com sucesso!");
            handleBackButton();

        } catch (NumberFormatException e) {
            SceneManager.showAlert(Alert.AlertType.ERROR, "Erro de Formato", "Ano de Lançamento deve ser um número.");
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