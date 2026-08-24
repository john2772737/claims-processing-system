package dev.john.claimsprocessingsystem.service;

import dev.john.claimsprocessingsystem.entity.Policy;
import dev.john.claimsprocessingsystem.entity.PolicyHolder;
import dev.john.claimsprocessingsystem.exception.ResourceNotFoundException;
import dev.john.claimsprocessingsystem.repository.PolicyHolderRepository;
import dev.john.claimsprocessingsystem.repository.PolicyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class PolicyService {

    private final PolicyRepository repository;
    private final PolicyHolderRepository policyHolderRepository;

    public PolicyService(PolicyRepository repository, PolicyHolderRepository policyHolderRepository) {
        this.repository = repository;
        this.policyHolderRepository = policyHolderRepository;
    }

    private String generatePolicyNumber() {
        int randomNumber = ThreadLocalRandom.current().nextInt(10000);
        return String.format("POL-%04d", randomNumber);
    }

    public Policy registerNewPolicy(Policy policy) {
        // 1. Verify policyHolder is provided and exists in the database
        if (policy.getPolicyHolder() == null || policy.getPolicyHolder().getId() == null) {
            throw new IllegalArgumentException("PolicyHolder ID must be provided.");
        }

        Long holderId = policy.getPolicyHolder().getId();
        PolicyHolder holder = policyHolderRepository.findById(holderId)
                .orElseThrow(() -> new ResourceNotFoundException("PolicyHolder not found with ID: " + holderId));

        // 2. Attach the fully managed PolicyHolder entity
        policy.setPolicyHolder(holder);

        // 3. Generate unique policy number
        String generatedPolicyNumber;
        do {
            generatedPolicyNumber = generatePolicyNumber();
        } while (repository.findByPolicyNumber(generatedPolicyNumber).isPresent());

        policy.setPolicyNumber(generatedPolicyNumber);

        // 4. Save and return
        return repository.save(policy);
    }

    public void deletePolicy(long id) {
        Policy existingPolicy = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Policy not found with ID: " + id));

        repository.delete(existingPolicy);
    }

    public List<Policy> allPolicy() {
        return repository.findAll();
    }

    public Policy.PolicySummary getSummaryByPolicyNumber(String policyNumber) {
        return repository.findSummaryByPolicyNumber(policyNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Policy not found with number: " + policyNumber));
    }

    public Policy editPolicy(String policyNumber, Policy updatedData) {
        Policy existingPolicy = repository.findByPolicyNumber(policyNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Policy not found with number: " + policyNumber));

        // If updatedData includes a new policyHolder ID, fetch and validate the new entity
        if (updatedData.getPolicyHolder() != null && updatedData.getPolicyHolder().getId() != null) {
            Long holderId = updatedData.getPolicyHolder().getId();
            PolicyHolder newHolder = policyHolderRepository.findById(holderId)
                    .orElseThrow(() -> new ResourceNotFoundException("PolicyHolder not found with ID: " + holderId));
            existingPolicy.setPolicyHolder(newHolder);
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