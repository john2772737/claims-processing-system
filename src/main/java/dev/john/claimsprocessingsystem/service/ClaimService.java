package dev.john.claimsprocessingsystem.service;

import dev.john.claimsprocessingsystem.entity.Claim;

import dev.john.claimsprocessingsystem.entity.Policy;
import dev.john.claimsprocessingsystem.exception.ResourceNotFoundException;
import dev.john.claimsprocessingsystem.repository.ClaimRepository;
import dev.john.claimsprocessingsystem.repository.PolicyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class ClaimService {
    private final ClaimRepository repository;
    private PolicyRepository policyRepository;

    public ClaimService(ClaimRepository repository) {
        this.repository = repository;
    }

    private String generateClaimNumber() {
        int randomNumber = ThreadLocalRandom.current().nextInt(10000);
        return String.format("CLM-%04d", randomNumber);
    }


    public Claim createClaim(Claim claim) {
        // 1. Validate that the associated Policy actually exists in the database
        String policyNumber = claim.getPolicyId().getPolicyNumber();
        if (!policyRepository.existsByPolicyNumber(policyNumber)) {
            throw new ResourceNotFoundException("Cannot create claim: Policy " + policyNumber + " does not exist.");
        }



        String generatedClaimNumber;
        do {
            generatedClaimNumber = generateClaimNumber();
        } while (repository.existsByClaimNumber(generatedClaimNumber)); // Checks the generated string!

        // 3. Set the generated number and save
        claim.setClaimNumber(generatedClaimNumber);
        return repository.save(claim);
    }

    public List<Claim> allClaims() {
        List<Claim> claims = repository.findAll();

        if (claims.isEmpty()) {
            throw new ResourceNotFoundException("No claims found in the system.");
        }

        return claims;
    }

    public Claim.ClaimSummary getClaimByClaimNumber(String claimNumber) {
        return repository.findSummaryByClaimNumber(claimNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Policy not found with number: " + claimNumber));
    }

    public Claim updateClaim(String claimNumber, Claim updatedData) {
        Claim existingClaim = repository.findByClaimNumber(claimNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Policy not found with number: " + claimNumber));

        // Only update fields if new values are provided
        if (updatedData.getPolicyId() != null) {
            existingClaim.setPolicyId(updatedData.getPolicyId());
        }
        if (updatedData.getAdjusterId() != null) {
            existingClaim.setAdjusterId(updatedData.getAdjusterId());
        }
        if (updatedData.getIncidentDate() != null) {
            existingClaim.setIncidentDate(updatedData.getIncidentDate());
        }
        if (updatedData.getAmountRequested() != null) {
            existingClaim.setAmountRequested(updatedData.getAmountRequested());
        }
        if (updatedData.getStatus() != null) {
            existingClaim.setStatus(updatedData.getStatus());
        }
        if (updatedData.getDescription() != null) {
            existingClaim.setDescription(updatedData.getDescription());
        }

        return repository.save(existingClaim);
    }

    public void deleteClaim(Long id) {
        Claim existingClaim = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Claim not found with ID: " + id));

        repository.delete(existingClaim);

    }

}
