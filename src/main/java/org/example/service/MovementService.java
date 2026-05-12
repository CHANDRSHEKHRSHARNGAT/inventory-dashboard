package com.inventory.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.inventory.model.Movement;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovementService {
    private List<Movement> movements = new ArrayList<>();

    @PostConstruct
    public void loadData() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            movements = mapper.readValue(
                    new ClassPathResource("movements.json").getInputStream(),
                    new TypeReference<List<Movement>>() {}
            );
        } catch (IOException e) {
            throw new RuntimeException("Failed to load movement data", e);
        }
    }

    public List<Movement> getFilteredMovements(LocalDate from, LocalDate to, String type) {
        return movements.stream()
                .filter(m -> !m.getTimestamp().toLocalDate().isBefore(from))
                .filter(m -> !m.getTimestamp().toLocalDate().isAfter(to))
                .filter(m -> type == null || m.getMovementType().equalsIgnoreCase(type))
                .sorted((a, b) -> b.getTimestamp().compareTo(a.getTimestamp()))
                .collect(Collectors.toList());
    }
}