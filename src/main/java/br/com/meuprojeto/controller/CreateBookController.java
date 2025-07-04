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

    @FXML
    public void initialize() {
        try {
            culturalData = persistenceManager.loadCulturalData();
        } catch (IOException e) {
            SceneManager.showAlert(Alert.AlertType.ERROR, "Erro", "Não foi possível carregar os dados existentes.");
            culturalData = new CulturalData();
        }
    }

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

    @FXML
    private void handleSave() {
        try {
            String title = titleField.getText().trim();
            int releaseYear = Integer.parseInt(releaseYearField.getText());

            if (title.isEmpty()) {
                SceneManager.showAlert(Alert.AlertType.ERROR, "Erro de Validação", "O campo 'Título' não pode estar vazio.");
                return;
            }
            if (releaseYear < 0) {
                SceneManager.showAlert(Alert.AlertType.ERROR, "Erro de Validação", "O ano não pode ser negativo.");
                return;
            }

            /*usa .equals() para comparar o conteúdo dos objetos.
            */
            boolean titleExists = culturalData.getBooks().stream()
                .anyMatch(b -> b.getTitle().equalsIgnoreCase(title) && !b.equals(bookToEdit));
            if (titleExists) {
                SceneManager.showAlert(Alert.AlertType.ERROR, "Erro de Validação", "Já existe um livro com este título.");
                return;
            }

            if (bookToEdit == null) { // --- MODO DE CRIAÇÃO ---
                Book newBook = new Book(
                    title, genreField.getText(), releaseYear,
                    authorField.getText(), publisherField.getText(), isbnField.getText(), false
                );
                culturalData.getBooks().add(newBook);

            } else { // --- MODO DE EDIÇÃO ---
                bookToEdit.setTitle(title);
                bookToEdit.setGenre(genreField.getText());
                bookToEdit.setReleaseYear(releaseYear);
                bookToEdit.setAuthor(authorField.getText());
                bookToEdit.setPublisher(publisherField.getText());
                bookToEdit.setIsbn(isbnField.getText());
            }
            
            persistenceManager.saveCulturalData(culturalData);
            SceneManager.showAlert(Alert.AlertType.INFORMATION, "Sucesso", "Livro salvo com sucesso!");
            handleBackButton();

        } catch (NumberFormatException e) {
            SceneManager.showAlert(Alert.AlertType.ERROR, "Erro de Formato", "Ano de Lançamento deve ser um número.");
        } catch (IOException e) {
            SceneManager.showAlert(Alert.AlertType.ERROR, "Erro ao Salvar", "Falha ao salvar dados no arquivo.");
        }
    }

    @FXML
    private void handleBackButton() {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
    }
}