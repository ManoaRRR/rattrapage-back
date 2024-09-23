package com.example.patrimoine.controller;
import com.example.patrimoine.model.Patrimoine;
import com.example.patrimoine.service.PatrimoineService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/patrimoines")
public class PatrimoineController {

    private final PatrimoineService patrimoineService;

    public PatrimoineController(PatrimoineService patrimoineService) {
        this.patrimoineService = patrimoineService;
    }


    @GetMapping("/health") // Nouveau endpoint
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("OK");
    }
    @PutMapping("/{id}")
    public ResponseEntity<Patrimoine> createOrUpdatePatrimoine(@PathVariable String id, @RequestBody Patrimoine patrimoine) {
        try {
            // Vérification simple des données
            if (patrimoine == null || patrimoine.getPossesseur() == null || patrimoine.getPossesseur().isEmpty()) {
                return ResponseEntity.badRequest().build(); // Retourne une 400 Bad Request si les données sont invalides
            }

            Patrimoine savedPatrimoine = patrimoineService.saveOrUpdatePatrimoine(id, patrimoine);
            return ResponseEntity.ok(savedPatrimoine);
        } catch (IOException e) {
            e.printStackTrace(); // Imprime la stack trace pour le débogage
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patrimoine> getPatrimoine(@PathVariable String id) {
        try {
            Optional<Patrimoine> optionalPatrimoine = patrimoineService.readPatrimoine(id);
            if (optionalPatrimoine.isPresent()) {
                return ResponseEntity.ok(optionalPatrimoine.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            return ResponseEntity.status(500).build();
        }
    }
}
