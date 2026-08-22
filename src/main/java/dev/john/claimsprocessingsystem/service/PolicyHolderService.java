package dev.john.claimsprocessingsystem.service;

import dev.john.claimsprocessingsystem.entity.PolicyHolder;
import dev.john.claimsprocessingsystem.repository.PolicyHolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PolicyHolderService {
    @Autowired
    private PolicyHolderRepository repository;

    public PolicyHolder registerPolicyHolder(PolicyHolder holder) {
        if (repository.findByEmail(holder.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered: " + holder.getEmail());
        }
        return repository.save(holder);
    }

    public void deletePolicyHolder(Long id) {
        PolicyHolder existingHolder = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Policy holder not found with ID: " + id));

        repository.delete(existingHolder);
    }

    public PolicyHolder editPolicyHolder(Long id, PolicyHolder updatedData) {

        PolicyHolder holderId = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Policy holder not found with ID: " + id));

        // Only update fields if new values are provided
        if (updatedData.getFirstName() != null) {
            holderId.setFirstName(updatedData.getFirstName());
        }
        if (updatedData.getLastName() != null) {
            holderId.setLastName(updatedData.getLastName());
        }
        if (updatedData.getEmail() != null) {
            holderId.setEmail(updatedData.getEmail());
        }
        if (updatedData.getPhoneNumber() != null) {
            holderId.setPhoneNumber(updatedData.getPhoneNumber());
        }

        return repository.save(holderId);
    }

    public PolicyHolder.PolicyHolderSummary getSummaryById(Long id) {
        return repository.findSummaryById(id)
                .orElseThrow(() -> new RuntimeException("Policy holder not found with ID: " + id));
    }

    public List<PolicyHolder> allPolicyHolder(){
        return repository.findAll();
    }

}

