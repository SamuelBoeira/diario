package br.com.meuprojeto.model;
/**
 * Classe que representa um ator em uma mídia digital.
 */
public class Actor {
    private String name;
    private String character;

    /**
     * Construtor padrão (necessário para Jackson).
     */
    public Actor() {
        this.name = "";
        this.character = "";
    }

    /**
     * Construtor da classe Actor.
     * 
     * @param name Nome do ator
     * @param character Nome do personagem interpretado pelo ator
     */
    public Actor(String name, String character) {
        this.name = name;
        this.character = character;
    }

    public String getName() {
        return name;
    }

    /**
     * Define um novo nome para o ator.
     * 
     * @param name O novo nome do ator
     */
    public void setName(String name) {
        this.name = name;
    }

    public String getCharacter() {
        return character;
    }

    /**
     * Define um novo nome de personagem para o ator.
     * 
     * @param character O novo nome do personagem
     */
    public void setCharacter(String character) {
        this.character = character;
    }

    @Override
    public String toString() {
        return name + " como " + character;
    }
}
