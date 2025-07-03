package br.com.meuprojeto.controller;

import br.com.meuprojeto.model.Actor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.util.List;
import java.util.ArrayList;

/**
 * Controlador para a janela modal de gerenciamento de elenco.
 * Permite adicionar e remover atores de uma lista.
 */
public class ActorManagementController {

    @FXML private ListView<Actor> castListView;
    @FXML private TextField actorNameField;
    @FXML private TextField characterNameField;
    @FXML private Button closeButton;

    private ObservableList<Actor> castList = FXCollections.observableArrayList();

    /**
     * Inicializa o controlador.
     * Configura a ListView para usar a lista observável de atores.
     */
    @FXML
    public void initialize() {
        castListView.setItems(castList);
    }

    /**
     * Configura o elenco inicial a ser exibido na lista.
     * Este método é chamado para preencher a janela com os atores já existentes.
     * @param currentCast A lista de atores atual.
     */
    public void setCast(List<Actor> currentCast) {
        this.castList.setAll(currentCast);
    }

    /**
     * Retorna a lista final de atores após a edição.
     * @return A lista de atores atualizada.
     */
    public List<Actor> getCast() {
        return new ArrayList<>(castList);
    }

    /**
     * Manipula o clique no botão "Adicionar".
     * Cria um novo objeto Ator com os dados dos campos de texto e o adiciona à lista.
     */
    @FXML
    private void handleAddActor() {
        String actorName = actorNameField.getText().trim();
        String characterName = characterNameField.getText().trim();

        if (!actorName.isEmpty() && !characterName.isEmpty()) {
            Actor newActor = new Actor(actorName, characterName);
            // Evita duplicação simples na mesma sessão de edição
            if (!castList.contains(newActor)) {
                castList.add(newActor);
                actorNameField.clear();
                characterNameField.clear();
            }
        }
    }

    /**
     * Manipula o clique no botão "Remover Ator Selecionado".
     * Remove o ator atualmente selecionado na ListView.
     */
    @FXML
    private void handleRemoveSelectedActor() {
        Actor selectedActor = castListView.getSelectionModel().getSelectedItem();
        if (selectedActor != null) {
            castList.remove(selectedActor);
        }
    }

    /**
     * Manipula o clique no botão "Salvar e Fechar".
     * Fecha a janela modal.
     */
    @FXML
    private void handleClose() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}