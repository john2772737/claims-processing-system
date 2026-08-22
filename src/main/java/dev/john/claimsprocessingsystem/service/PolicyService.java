package dev.john.claimsprocessingsystem.service;


import dev.john.claimsprocessingsystem.entity.Policy;
import dev.john.claimsprocessingsystem.repository.PolicyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class PolicyService {
    @Autowired
    private PolicyRepository repository;

    private String generatePolicyNumber (){
        int randomNumber = ThreadLocalRandom.current().nextInt(10000);
        return String.format("POL-%04d", randomNumber);
    }
    public Policy registerNewPolicy(Policy policy){
        String generatedPolicyNumber;
        do {
            generatedPolicyNumber = generatePolicyNumber();
        } while (repository.findByPolicyNumber(generatedPolicyNumber).isPresent());

        policy.setPolicyNumber(generatedPolicyNumber);
        return repository.save(policy);

    }

    public void deletePolicy(long id){
        Policy existingPolicy = repository.findById(id)
           .orElseThrow(() -> new RuntimeException("Policy holder not found with ID: " + id));

        repository.delete(existingPolicy);


    }

    public List<Policy> allPolicy(){
        return repository.findAll();
    }

    public Policy.PolicySummary getSummaryByPolicyNumber(String policyNumber) {
        return repository.findSummaryByPolicyNumber(policyNumber)
                .orElseThrow(() -> new RuntimeException("Policy not found with number: " + policyNumber));
    }

    public Policy editPolicy(String policyNumber,Policy updatedData){
        Policy existingPolicy = repository.findByPolicyNumber(policyNumber)
                .orElseThrow(() -> new RuntimeException("Policy not found with number: " + policyNumber));

        // Only update fields if new values are provided
        if (updatedData.getPolicyHolderId() != null) {
            existingPolicy.setPolicyHolderId(updatedData.getPolicyHolderId());
        }
        if (updatedData.getPolicyNumber() != null) {
            existingPolicy.setPolicyNumber(updatedData.getPolicyNumber());
        }
        if (updatedData.getCoverageAmount() != null) {
            existingPolicy.setCoverageAmount(updatedData.getCoverageAmount());
        }
        if (updatedData.getStartDate() != null) {
            existingPolicy.setStartDate(updatedData.getStartDate());
        }
        if (updatedData.getStatus() != null) {
            existingPolicy.setStatus(updatedData.getStatus());
        }

        return repository.save(existingPolicy);

    }
}
