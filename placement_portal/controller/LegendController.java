package com.placement_portal.controller;

import com.placement_portal.model.Legend;
import com.placement_portal.service.LegendService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/legends")
public class LegendController {

    private final LegendService legendService;

    public LegendController(LegendService legendService) {
        this.legendService = legendService;
    }

    // Fetch all legends with optional filters
    @GetMapping
    public ResponseEntity<List<Legend>> getLegends(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String packageDetails,
            @RequestParam(required = false) String batch,
            @RequestParam(required = false) String branch,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String mode,
            @RequestParam(required = false) String company
    ) {
        List<Legend> legends = legendService.getLegends(name, packageDetails, batch, branch, type, mode, company);
        return ResponseEntity.ok(legends);
    }

    // Fetch a legend by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getLegend(@PathVariable Long id) {
        Optional<Legend> legend = legendService.getLegendById(id);
        if (legend.isPresent()) {
            return ResponseEntity.ok(legend.get());
        } else {
            return ResponseEntity.status(404).body("Legend not found");
        }
    }

    // Add a new legend
    @PostMapping
    public ResponseEntity<?> postLegend(@RequestBody Legend legend) {
        if (!legendService.validateLegend(legend)) {
            return ResponseEntity.badRequest().body("Invalid legend data");
        }
        Legend newLegend = legendService.createLegend(legend);
        return ResponseEntity.status(201).body(newLegend);
    }

    // Update a legend
    @PutMapping("/{id}")
    public ResponseEntity<?> updateLegend(@PathVariable Long id, @RequestBody Legend legend) {
        Optional<Legend> updatedLegend = legendService.updateLegend(id, legend);
        if (updatedLegend.isPresent()) {
            return ResponseEntity.ok(updatedLegend.get());
        } else {
            return ResponseEntity.status(404).body("Legend not found");
        }
    }

    // Delete a legend
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLegend(@PathVariable Long id) {
        boolean isDeleted = legendService.deleteLegend(id);
        if (isDeleted) {
            return ResponseEntity.ok("Legend deleted successfully");
        } else {
            return ResponseEntity.status(404).body("Legend not found");
        }
    }
}
