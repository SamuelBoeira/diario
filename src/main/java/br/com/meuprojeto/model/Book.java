package br.com.meuprojeto.model;
/**
 * Classe que representa um livro, herança da classe Media.
 */
public class Book extends Media {
    private String author;
    private String publisher;
    private String isbn;
    private boolean owned;

    /**
     * Construtor padrão (necessário para Jackson).
     */
    public Book() {
        super("", "", 0); // Valores temporários que serão substituídos durante a desserialização
        this.author = "";
        this.publisher = "";
        this.isbn = "";
        this.owned = false;
    }

    /**
     * Construtor da classe Book.
     * Inicializa um livro com seus atributos básicos e específicos.
     * 
     * @param title Título do livro
     * @param author Nome do autor do livro
     * @param publisher Nome da editora do livro
     * @param isbn Código ISBN do livro
     * @param genre Gênero do livro
     * @param releaseYear Ano de publicação do livro
     * @param owned Indica se o usuário possui um exemplar físico do livro
     */
    public Book(String title, String author, String publisher, String isbn,
                String genre, int releaseYear, boolean owned) {
        // Chama o construtor da superclasse (Media) com os parâmetros necessários
        super(title, genre, releaseYear);  // Ordem: title, genre, releaseYear
        this.author = author;
        this.publisher = publisher;
        this.isbn = isbn;
        this.owned = owned;
    }

    // Métodos getters e setters para acesso e modificação dos atributos específicos de livros

    /**
     * Retorna o nome do autor do livro.
     * 
     * @return O nome do autor
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Define um novo autor para o livro.
     * 
     * @param author O novo nome do autor
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Retorna o nome da editora do livro.
     * 
     * @return O nome da editora
     */
    public String getPublisher() {
        return publisher;
    }

    /**
     * Define uma nova editora para o livro.
     * 
     * @param publisher O novo nome da editora
     */
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    /**
     * Retorna o código ISBN do livro.
     * 
     * @return O código ISBN
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Define um novo código ISBN para o livro.
     * 
     * @param isbn O novo código ISBN
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * Verifica se o usuário possui um exemplar físico do livro.
     * 
     * @return true se o usuário possui o livro, false caso contrário
     */
    public boolean isOwned() {
        return owned;
    }

    /**
     * Define se o usuário possui um exemplar físico do livro.
     * 
     * @param owned O novo estado de posse (true para possui, false para não possui)
     */
    public void setOwned(boolean owned) {
        this.owned = owned;
    }

    /**
     * Retorna uma representação em string do livro.
     * Estende o método toString da superclasse (Media) adicionando informações específicas de livros.
     * 
     * @return Uma string formatada com as informações do livro
     */
    @Override
    public String toString() {
        return super.toString() + String.format("\nAutor: %s\nPublisher: %s\nISBN: %s\nPossui: %s\n%s",
                author, publisher, isbn, owned ? "Sim" : "Não", getReviewsAsString());
    }
}
