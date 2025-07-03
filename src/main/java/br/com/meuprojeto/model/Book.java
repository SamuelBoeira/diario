package br.com.meuprojeto.model;

/**
 * Representa um livro, uma especialização da classe Media.
 * Esta versão contém todos os campos e métodos necessários para a aplicação completa.
 */
public class Book extends Media {
    private String author;
    private String publisher;
    private String isbn;
    private boolean owned;

    /**
     * Construtor padrão para uso do Jackson (JSON).
     */
    public Book() {
        super();
    }

    /**
     * Construtor completo da classe Livro.
     */
    public Book(String title, String genre, int releaseYear, String author, String publisher, String isbn, boolean owned) {
        super(title, genre, releaseYear);
        this.author = author;
        this.publisher = publisher;
        this.isbn = isbn;
        this.owned = owned;
    }

    /**
     * Retorna o autor do livro.
     * @return O nome do autor.
     */
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Retorna a editora do livro.
     * @return O nome da editora.
     */
    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    /**
     * Retorna o código ISBN do livro.
     * @return O código ISBN.
     */
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * Verifica se o usuário possui uma cópia física do livro.
     * @return true se possui, false caso contrário.
     */
    public boolean isOwned() {
        return owned;
    }

    public void setOwned(boolean owned) {
        this.owned = owned;
    }
}