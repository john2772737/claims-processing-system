package dev.john.claimsprocessingsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "policy")
public class Policy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "policy_holder_id", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private PolicyHolder policyHolder;

    @NotBlank(message = "Policy number is required")
    @Column(name = "policy_number", length = 50, nullable = false, unique = true)
    private String policyNumber;

    @NotNull(message = "Coverage amount is required")
    @Positive(message = "Coverage amount must be greater than zero")
    @Column(name = "coverage_amount", precision = 12, scale = 2, nullable = false)
    private BigDecimal coverageAmount;

    @NotNull(message = "Start date is required")
    @FutureOrPresent(message = "Start date cannot be in the past")
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @NotBlank(message = "Status is required")
    @Column(name = "status", length = 20, nullable = false)
    private String status;

    // 1. Default Empty Constructor (Required by JPA)
    public Policy() {}

    // 2. Parameterized Constructor
    public Policy(PolicyHolder policyHolder, String policyNumber, BigDecimal coverageAmount, LocalDate startDate, String status) {
        this.policyHolder = policyHolder;
        this.policyNumber = policyNumber;
        this.coverageAmount = coverageAmount;
        this.startDate = startDate;
        this.status = status;
    }

    public interface PolicySummary {
        String getPolicyNumber();
        BigDecimal getCoverageAmount();
        LocalDate getStartDate();
        String getStatus();
    }

    // 3. Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public PolicyHolder getPolicyHolder() { return policyHolder; }
    public void setPolicyHolder(PolicyHolder policyHolder) { this.policyHolder = policyHolder; }

    public String getPolicyNumber() { return policyNumber; }
    public void setPolicyNumber(String policyNumber) { this.policyNumber = policyNumber; }

    public BigDecimal getCoverageAmount() { return coverageAmount; }
    public void setCoverageAmount(BigDecimal coverageAmount) { this.coverageAmount = coverageAmount; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}