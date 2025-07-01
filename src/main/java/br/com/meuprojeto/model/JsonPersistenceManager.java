package br.com.meuprojeto.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;

/**
 * Gerencia a persistência de dados culturais em formato JSON.
 */
public class JsonPersistenceManager {

    private static final String FILE_PATH = "diario_cultural_data.json";
    private final ObjectMapper objectMapper;

    public JsonPersistenceManager() {
        objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.registerModule(new JavaTimeModule());
    }

    /**
     * Salva os dados culturais em um arquivo JSON.
     * @param culturalData Os dados culturais a serem salvos.
     * @throws IOException Se ocorrer um erro durante a escrita do arquivo.
     */
    public void saveCulturalData(CulturalData culturalData) throws IOException {
        objectMapper.writeValue(new File(FILE_PATH), culturalData);
    }

    /**
     * Carrega os dados culturais de um arquivo JSON.
     * @return Os dados culturais carregados, ou um novo objeto CulturalData se o arquivo não existir ou estiver vazio.
     * @throws IOException Se ocorrer um erro durante a leitura do arquivo.
     */
    public CulturalData loadCulturalData() throws IOException {
        File file = new File(FILE_PATH);
        if (file.exists() && file.length() > 0) {
            return objectMapper.readValue(file, CulturalData.class);
        } else {
            return new CulturalData();
        }
    }
}


