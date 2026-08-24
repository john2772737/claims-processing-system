package dev.john.claimsprocessingsystem.repository;

import dev.john.claimsprocessingsystem.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long> {

    List<Document> findByClaimId(Long claimId);
}