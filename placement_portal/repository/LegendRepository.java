package com.placement_portal.repository;

import com.placement_portal.model.Legend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LegendRepository extends JpaRepository<Legend, Long> {

    @Query("SELECT l FROM Legend l WHERE " +
            "(:name IS NULL OR l.name LIKE %:name%) AND " +
            "(:packageDetails IS NULL OR l.packageDetails LIKE %:packageDetails%) AND " +
            "(:batch IS NULL OR l.batch = :batch) AND " +
            "(:branch IS NULL OR l.branch = :branch) AND " +
            "(:type IS NULL OR l.type = :type) AND " +
            "(:mode IS NULL OR l.mode = :mode) AND " +
            "(:company IS NULL OR l.company = :company)")
    List<Legend> findLegendsByFilters(String name, String packageDetails, String batch, String branch, String type, String mode, String company);
}
