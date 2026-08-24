package dev.john.claimsprocessingsystem.repository;

import dev.john.claimsprocessingsystem.entity.Adjuster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdjusterRepository extends JpaRepository<Adjuster, Long> {
    boolean existsByEmployeeCode(String employeeCode);
    boolean existsByEmail(String email);
}