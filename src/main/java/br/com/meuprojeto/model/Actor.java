package br.com.meuprojeto.model;

import java.util.Objects;

public class Actor {
    private String name;
    private String character;

    public Actor() {}
    public Actor(String name, String character) {
        this.name = name;
        this.character = character;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Actor actor = (Actor) o;
        return Objects.equals(name, actor.name) && Objects.equals(character, actor.character);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, character);
    }

    // Getters e Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCharacter() { return character; }
    public void setCharacter(String character) { this.character = character; }
    @Override
    public String toString() { return name + " como " + character; }
}