package dev.john.claimsprocessingsystem.controller;

import dev.john.claimsprocessingsystem.entity.Adjuster;
import dev.john.claimsprocessingsystem.service.AdjusterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/adjusters")
public class AdjusterController {

    private final AdjusterService service;

    public AdjusterController(AdjusterService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Adjuster>> getAllAdjusters() {
        return ResponseEntity.ok(service.getAllAdjusters());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Adjuster> getAdjusterById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getAdjusterById(id));
    }

    @PostMapping
    public ResponseEntity<Adjuster> createAdjuster(@RequestBody Adjuster adjuster) {
        return ResponseEntity.ok(service.createAdjuster(adjuster));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Adjuster> updateAdjuster(@PathVariable Long id, @RequestBody Adjuster adjuster) {
        return ResponseEntity.ok(service.updateAdjuster(id, adjuster));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdjuster(@PathVariable Long id) {
        service.deleteAdjuster(id);
        return ResponseEntity.noContent().build();
    }
}