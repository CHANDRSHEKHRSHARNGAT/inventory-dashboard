package com.inventory.controller;

import com.inventory.model.Movement;
import com.inventory.service.MovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/movements")
@CrossOrigin(origins = "*")
public class MovementController {

    @Autowired
    private MovementService movementService;

    @GetMapping
    public ResponseEntity<?> getMovements(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @RequestParam(required = false) String type,
            @RequestParam(required = false, defaultValue = "false") boolean export) {

        List<Movement> filtered = movementService.getFilteredMovements(from, to, type);

        if (export) {
            String csv = generateCSV(filtered);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=movements.csv")
                    .contentType(MediaType.parseMediaType("text/csv"))
                    .body(csv);
        }

        return ResponseEntity.ok(filtered);
    }

    private String generateCSV(List<Movement> movements) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        StringBuilder csv = new StringBuilder();
        csv.append("ID,Timestamp,SKU,Movement Type,Quantity\n");

        for (Movement m : movements) {
            csv.append(String.format("%s,%s,%s,%s,%d\n",
                    m.getId(),
                    m.getTimestamp().format(formatter),
                    m.getSku(),
                    m.getMovementType(),
                    m.getQuantity()));
        }

        return csv.toString();
    }
}