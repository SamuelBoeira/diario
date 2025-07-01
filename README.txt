Diário Cultural - Projeto Refatorado com Maven

Este projeto é uma refatoração do Diário Cultural, seguindo o padrão MVC (Model-View-Controller) e utilizando JavaFX para a interface gráfica. O projeto utiliza Maven para gerenciamento de dependências e construção, o que simplifica a configuração e execução.

## Estrutura do Projeto

O projeto está organizado nos seguintes pacotes:
- `br.com.meuprojeto.model`: Contém as entidades de dados, lógica de negócio e classes de persistência (Jackson).
- `br.com.meuprojeto.view`: Contém os arquivos FXML, classes auxiliares e recursos visuais.
- `br.com.meuprojeto.controller`: Contém as classes controladoras que gerenciam os eventos da interface e a interação entre View e Model.

## Requisitos de Execução

Para executar este projeto, você precisará do Java Development Kit (JDK) 17 ou superior e do Maven instalado.

### 1. Configuração no IntelliJ IDEA

Siga os passos abaixo para importar e executar o projeto no IntelliJ IDEA:

1.  **Abrir o Projeto:**
    *   Abra o IntelliJ IDEA.
    *   Selecione `File` -> `Open...` e navegue até a pasta `diario-cultural` (a pasta raiz do projeto Maven). Clique em `Open`.
    *   O IntelliJ IDEA deve reconhecer automaticamente o projeto Maven e importar as dependências. Aguarde a indexação e o download das dependências pelo Maven.

2.  **Configurar o SDK do Projeto:**
    *   Vá em `File` -> `Project Structure...` (ou pressione `Ctrl+Alt+Shift+S`).
    *   Em `Project Settings` -> `Project`, certifique-se de que o `Project SDK` esteja configurado para um JDK 17 ou superior (ex: `17 (openjdk)`).
    *   Em `Project language level`, selecione `SDK default (17 - preview)` ou a versão correspondente ao seu JDK.
    *   Clique em `Apply` e depois em `OK`.

3.  **Executar a Aplicação (MÉTODO RECOMENDADO):**
    *   A forma mais confiável de executar aplicações JavaFX com Maven no IntelliJ IDEA é através dos Maven Goals.
    *   No IntelliJ IDEA, abra a aba `Maven` (geralmente localizada à direita da tela).
    *   Expanda `diario-cultural` -> `Lifecycle`.
    *   Dê um duplo clique em `clean` e depois em `install` para garantir que o projeto esteja compilado e as dependências resolvidas.
    *   Expanda `Plugins` -> `javafx`.
    *   Dê um duplo clique em `javafx:run`.
    *   Isso iniciará a aplicação JavaFX corretamente, pois o `javafx-maven-plugin` gerencia automaticamente os parâmetros de VM (`--module-path`, `--add-modules`) necessários.

4.  **Executar a Aplicação (Via Terminal):**
    *   Abra o terminal na raiz do projeto (`diario-cultural`) e execute o comando:
        ```bash
        mvn clean install javafx:run
        ```
        Este comando primeiro limpa, compila e instala o projeto, e depois executa a aplicação JavaFX.

## Observações Importantes

*   **Erro "Unrecognized option: --module-path"**: Este erro ocorre quando a JVM que tenta executar a aplicação não é compatível com o sistema de módulos do Java (introduzido no Java 9). Ao usar o `javafx-maven-plugin` (seja via IntelliJ Maven Goals ou terminal), o plugin se encarrega de passar os argumentos corretos para a JVM. Se você tentar executar a classe `Main` diretamente pelo botão de `Run` do IntelliJ sem uma configuração específica, o IntelliJ pode não incluir esses argumentos, causando o erro. **Sempre prefira o método de execução via Maven Goals no IntelliJ ou via terminal.**
*   O Maven se encarregará de baixar todas as dependências (JavaFX, Jackson, etc.) automaticamente.
*   Os comentários e Javadoc foram atualizados para seguir as boas práticas de documentação Java.
*   A validação de inputs e o tratamento de erros foram implementados para garantir a robustez da aplicação.

Qualquer dúvida ou problema, consulte a documentação oficial do JavaFX, Maven e IntelliJ IDEA.


