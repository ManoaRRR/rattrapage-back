package com.example.patrimoine;

import com.example.patrimoine.model.Patrimoine;
import com.example.patrimoine.service.PatrimoineService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PatrimoineApplicationTests {

	private PatrimoineService patrimoineService;

	@BeforeEach
	void setUp() {
		patrimoineService = new PatrimoineService();
	}

	@Test
	void contextLoads() {
		// Test généré automatiquement, peut être laissé pour vérifier le chargement du contexte
	}

	@Test
	void testSaveOrUpdatePatrimoine() throws IOException {
		Patrimoine patrimoine = new Patrimoine();
		patrimoine.setPossesseur("John Doe");

		Patrimoine savedPatrimoine = patrimoineService.saveOrUpdatePatrimoine("123", patrimoine);

		assertNotNull(savedPatrimoine);
		assertEquals("John Doe", savedPatrimoine.getPossesseur());
		assertNotNull(savedPatrimoine.getDerniereModification());

		Path path = Paths.get("patrimoines/123.json");
		assertTrue(Files.exists(path));

		Files.delete(path);
	}

	@Test
	void testReadPatrimoine() throws IOException {
		Path path = Paths.get("patrimoines/456.json");
		Patrimoine patrimoine = new Patrimoine();
		patrimoine.setPossesseur("Jane Doe");
		patrimoine.setDerniereModification(LocalDateTime.now());
		Files.createDirectories(path.getParent());
		Files.write(path, new ObjectMapper().writeValueAsBytes(patrimoine));

		Patrimoine readPatrimoine = patrimoineService.readPatrimoine("456");

		assertNotNull(readPatrimoine);
		assertEquals("Jane Doe", readPatrimoine.getPossesseur());

		Files.delete(path);
	}
}
