package com.example.patrimoine.model;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patrimoine {
    private String possesseur;
    private LocalDateTime derniereModification;
}