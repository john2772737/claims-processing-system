package dev.john.claimsprocessingsystem.repository;

import dev.john.claimsprocessingsystem.entity.Policy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface PolicyRepository extends JpaRepository<Policy, Long> {
    Optional<Policy> findByPolicyNumber(String policyNumber);
    Optional<Policy.PolicySummary> findSummaryByPolicyNumber (String policyNumber);
    boolean existsByPolicyNumber(String policyNumber);


}
