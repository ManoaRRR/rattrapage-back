package com.example.patrimoine.service;
import com.example.patrimoine.model.Patrimoine;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PatrimoineService {
    private static final String DIRECTORY = "patrimoines/";

    private final ObjectMapper objectMapper = new ObjectMapper();

    public Optional<Patrimoine> readPatrimoine(String id) throws IOException {
        Path path = Paths.get(DIRECTORY + id + ".json");
        if (Files.exists(path)) {
            return Optional.of(objectMapper.readValue(Files.readAllBytes(path), Patrimoine.class));
        } else {
            return Optional.empty();
        }
    }

    public Patrimoine saveOrUpdatePatrimoine(String id, Patrimoine patrimoine) throws IOException {
        if (patrimoine == null || patrimoine.getPossesseur() == null || patrimoine.getPossesseur().isEmpty()) {
            throw new IllegalArgumentException("Le patrimoine ou le possesseur ne peut pas être nul ou vide");
        }

        patrimoine.setDerniereModification(LocalDateTime.now());
        Path path = Paths.get(DIRECTORY + id + ".json");
        Files.createDirectories(path.getParent());
        Files.write(path, objectMapper.writeValueAsBytes(patrimoine), StandardOpenOption.CREATE);
        return patrimoine;
    }
}


