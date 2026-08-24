package dev.john.claimsprocessingsystem.service;

import dev.john.claimsprocessingsystem.entity.Document;
import dev.john.claimsprocessingsystem.exception.ResourceNotFoundException;
import dev.john.claimsprocessingsystem.repository.ClaimRepository;
import dev.john.claimsprocessingsystem.repository.DocumentRepository;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class DocumentService {

    private final DocumentRepository repository;
    private final ClaimRepository claimRepository;

    public DocumentService(DocumentRepository repository, ClaimRepository claimRepository) {
        this.repository = repository;
        this.claimRepository = claimRepository;
    }

    public List<Document> getAllDocuments() {
        return repository.findAll();
    }

    public Document getDocumentById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Document not found with ID: " + id));
    }

  //  public List<Document> getDocumentsByClaimId(Long claimId) {
       // if (!claimRepository.existsById(claimId)) {
         //   throw new ResourceNotFoundException("Claim not found with ID: " + claimId);
        //}
        //return repository.findById(claimId);
    //}

    public Document createDocument(Document document) {
        // Validate that the associated claim exists
        if (!claimRepository.existsById(document.getClaimId().getId())) {
            throw new ResourceNotFoundException("Cannot create document. Claim not found with ID: " + document.getClaimId());
        }

        if (document.getUploadedAt() == null) {
            document.setUploadedAt(OffsetDateTime.now());
        }
        return repository.save(document);
    }

    public Document updateDocument(Long id, Document updatedData) {
        Document existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Document not found with ID: " + id));

        // Validate claim existence if claimId is being updated
        if (updatedData.getClaimId() != null && !updatedData.getClaimId().equals(existing.getClaimId())) {
            if (!claimRepository.existsByClaimNumber(updatedData.getClaimId().getClaimNumber())) {
                throw new ResourceNotFoundException("Cannot update document. Claim not found with ID: " + updatedData.getClaimId());
            }
            existing.setClaimId(updatedData.getClaimId());
        }

        if (updatedData.getFileName() != null) existing.setFileName(updatedData.getFileName());
        if (updatedData.getFileType() != null) existing.setFileType(updatedData.getFileType());
        if (updatedData.getStorageUrl() != null) existing.setStorageUrl(updatedData.getStorageUrl());
        if (updatedData.getUploadedAt() != null) existing.setUploadedAt(updatedData.getUploadedAt());

        return repository.save(existing);
    }

    public void deleteDocument(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Document not found with ID: " + id);
        }
        repository.deleteById(id);
    }
}