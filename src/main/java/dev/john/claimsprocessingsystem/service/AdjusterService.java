package dev.john.claimsprocessingsystem.service;

import dev.john.claimsprocessingsystem.entity.Adjuster;
import dev.john.claimsprocessingsystem.exception.DuplicateResourceException;
import dev.john.claimsprocessingsystem.exception.ResourceNotFoundException;
import dev.john.claimsprocessingsystem.repository.AdjusterRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdjusterService {

    private final AdjusterRepository repository;

    public AdjusterService(AdjusterRepository repository) {
        this.repository = repository;
    }

    public List<Adjuster> getAllAdjusters() {
        return repository.findAll();
    }

    public Adjuster getAdjusterById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Adjuster not found with ID: " + id));
    }

    public Adjuster createAdjuster(Adjuster adjuster) {
        // Check duplicate employee code
        if (repository.existsByEmployeeCode(adjuster.getEmployeeCode())) {
            throw new DuplicateResourceException("Employee code already exists: " + adjuster.getEmployeeCode());
        }

        // Check duplicate email
        if (repository.existsByEmail(adjuster.getEmail())) {
            throw new DuplicateResourceException("Email already registered: " + adjuster.getEmail());
        }

        return repository.save(adjuster);
    }

    public Adjuster updateAdjuster(Long id, Adjuster updatedData) {
        Adjuster existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Adjuster not found with ID: " + id));

        // Check employee code collision if updated
        if (updatedData.getEmployeeCode() != null && !updatedData.getEmployeeCode().equals(existing.getEmployeeCode())) {
            if (repository.existsByEmployeeCode(updatedData.getEmployeeCode())) {
                throw new DuplicateResourceException("Employee code already exists: " + updatedData.getEmployeeCode());
            }
            existing.setEmployeeCode(updatedData.getEmployeeCode());
        }

        // Check email collision if updated
        if (updatedData.getEmail() != null && !updatedData.getEmail().equals(existing.getEmail())) {
            if (repository.existsByEmail(updatedData.getEmail())) {
                throw new DuplicateResourceException("Email already registered: " + updatedData.getEmail());
            }
            existing.setEmail(updatedData.getEmail());
        }

        if (updatedData.getFirstName() != null) existing.setFirstName(updatedData.getFirstName());
        if (updatedData.getLastName() != null) existing.setLastName(updatedData.getLastName());

        return repository.save(existing);
    }

    public void deleteAdjuster(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Adjuster not found with ID: " + id);
        }
        repository.deleteById(id);
    }
}