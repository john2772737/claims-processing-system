package dev.john.claimsprocessingsystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "policy")
public class Policy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "policy_holder_id")
    private Long policyHolderId;

    @NotBlank(message = "Policy number is required")
    @Column(name = "policy_number", length = 50)
    private String policyNumber;

    @NotNull(message = "Coverage amount is required")
    @Column(name = "coverage_amount", precision = 12, scale = 2)
    private BigDecimal coverageAmount;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "status", length = 20)
    private String status;

    // 1. Default Empty Constructor (Required by JPA)
    public Policy() {}

    // 2. Parameterized Constructor
    public Policy(Long policyHolderId, String policyNumber, BigDecimal coverageAmount, LocalDate startDate, String status) {
        this.policyHolderId = policyHolderId;
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

    public Long getPolicyHolderId() { return policyHolderId; }
    public void setPolicyHolderId(Long policyHolderId) { this.policyHolderId = policyHolderId; }

    public String getPolicyNumber() { return policyNumber; }
    public void setPolicyNumber(String policyNumber) { this.policyNumber = policyNumber; }

    public BigDecimal getCoverageAmount() { return coverageAmount; }
    public void setCoverageAmount(BigDecimal coverageAmount) { this.coverageAmount = coverageAmount; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}