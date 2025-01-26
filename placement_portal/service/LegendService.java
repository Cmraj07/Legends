package com.placement_portal.service;

import com.placement_portal.model.Legend;
import com.placement_portal.repository.LegendRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LegendService {

    private final LegendRepository legendRepository;

    public LegendService(LegendRepository legendRepository) {
        this.legendRepository = legendRepository;
    }

    // Get legends with filters
    public List<Legend> getLegends(String name,String packageDetails,String batch, String branch, String type, String mode, String company) {
        return legendRepository.findLegendsByFilters(name, packageDetails, batch, branch, type, mode, company);
    }

    // Get legend by ID
    public Optional<Legend> getLegendById(Long id) {
        return legendRepository.findById(id);
    }

    // Create a new legend
    public Legend createLegend(Legend legend) {
        validateLegendOrThrow(legend);
        return legendRepository.save(legend);
    }

    // Update an existing legend
    public Optional<Legend> updateLegend(Long id, Legend updatedLegend) {
        validateLegendOrThrow(updatedLegend);
        Optional<Legend> existingLegend = legendRepository.findById(id);

        if (existingLegend.isPresent()) {
            Legend legend = existingLegend.get();
            legend.setName(updatedLegend.getName());
            legend.setPackageDetails(updatedLegend.getPackageDetails());
            legend.setBatch(updatedLegend.getBatch());
            legend.setBranch(updatedLegend.getBranch());
            legend.setType(updatedLegend.getType());
            legend.setMode(updatedLegend.getMode());
            legend.setCompany(updatedLegend.getCompany());
            return Optional.of(legendRepository.save(legend));
        }
        return Optional.empty();
    }

    // Delete a legend by ID
    public boolean deleteLegend(Long id) {
        if (legendRepository.existsById(id)) {
            legendRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Validate legend object
    public boolean validateLegend(Legend legend) {
        return legend != null &&
                legend.getName() != null &&
                legend.getPackageDetails() != null &&
                legend.getBatch() != null &&
                legend.getBranch() != null &&
                legend.getType() != null &&
                legend.getMode() != null &&
                legend.getCompany() != null;
    }

    // Validate legend and throw an exception if invalid
    private void validateLegendOrThrow(Legend legend) {
        if (!validateLegend(legend)) {
            throw new IllegalArgumentException("Invalid Legend data provided.");
        }
    }
}
