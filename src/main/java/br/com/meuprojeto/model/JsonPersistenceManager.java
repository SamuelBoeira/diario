package br.com.meuprojeto.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.File;
import java.io.IOException;

/**
 * Gerencia a persistência dos dados culturais em um arquivo JSON.
 */
public class JsonPersistenceManager {
    private static final String FILE_PATH = "diario_cultural_data.json";
    private final ObjectMapper objectMapper;

    public JsonPersistenceManager() {
        objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // Formata o JSON para ser legível
        objectMapper.registerModule(new JavaTimeModule()); // Suporte para datas (LocalDate)
    }

    /**
     * Salva o objeto CulturalData no arquivo JSON.
     * @param culturalData O objeto contendo todos os dados a serem salvos.
     * @throws IOException Se ocorrer um erro durante a escrita do arquivo.
     */
    public void saveCulturalData(CulturalData culturalData) throws IOException {
        objectMapper.writeValue(new File(FILE_PATH), culturalData);
    }

    /**
     * Carrega os dados culturais do arquivo JSON.
     * @return Um objeto CulturalData com os dados carregados ou um novo objeto vazio.
     * @throws IOException Se ocorrer um erro durante a leitura do arquivo.
     */
    public CulturalData loadCulturalData() throws IOException {
        File file = new File(FILE_PATH);
        if (file.exists() && file.length() > 0) {
            return objectMapper.readValue(file, CulturalData.class);
        }
        return new CulturalData(); // Retorna um objeto vazio se o arquivo não existir
    }
}