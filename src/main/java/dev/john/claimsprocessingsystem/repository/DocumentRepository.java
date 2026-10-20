package dev.john.claimsprocessingsystem.repository;

import dev.john.claimsprocessingsystem.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long> {

    // Finds all documents associated with a specific Claim ID
    List<Document> findByClaimId(Long claimId);
}