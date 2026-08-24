package dev.john.claimsprocessingsystem.repository;

import dev.john.claimsprocessingsystem.entity.Claim;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClaimRepository extends JpaRepository <Claim, Long> {
    Optional<Claim> findByClaimNumber(String claimNumber);
    Optional<Claim.ClaimSummary> findSummaryByClaimNumber(String claimNumber);

    boolean existsById(Long id);
    boolean existsByClaimNumber(String generatedClaimNumber);
}
