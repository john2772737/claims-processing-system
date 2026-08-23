package dev.john.claimsprocessingsystem.service;

import dev.john.claimsprocessingsystem.entity.Claim;

import dev.john.claimsprocessingsystem.entity.Policy;
import dev.john.claimsprocessingsystem.repository.ClaimRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class ClaimService {
    @Autowired
    private ClaimRepository repository;

    private String generateClaimNumber(){
        int randomNumber = ThreadLocalRandom.current().nextInt(10000);
        return String.format("CLM-%04d", randomNumber);
    }


    public Claim createClaim(Claim claim){
        String generatedClaimNumber;
        do {
           generatedClaimNumber= generateClaimNumber();
        }while (repository.findByClaimNumber(generateClaimNumber()).isPresent());

        claim.setClaimNumber(generatedClaimNumber);
        return repository.save(claim);
    }

    public List<Claim> allClaims() {
        List<Claim> claims = repository.findAll();

        if (claims.isEmpty()) {
            throw new RuntimeException("No claims found in the system.");
        }

        return claims;
    }

    public Claim.ClaimSummary getClaimByClaimNumber (String claimNumber){
        return repository.findSummaryByClaimNumber(claimNumber)
                .orElseThrow(() -> new RuntimeException("Policy not found with number: " + claimNumber));
    }

    public Claim updateClaim(String claimNumber,Claim updatedData){
        Claim existingClaim = repository.findByClaimNumber(claimNumber)
                .orElseThrow(() -> new RuntimeException("Policy not found with number: " + claimNumber));

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



}
