package dev.john.claimsprocessingsystem.repository;

import dev.john.claimsprocessingsystem.entity.PolicyHolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PolicyHolderRepository extends JpaRepository<PolicyHolder, Long> {

    // Custom query method generated automatically by Spring Data JPA
    Optional<PolicyHolder> findByEmail(String email);

}