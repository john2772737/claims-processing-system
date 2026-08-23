package dev.john.claimsprocessingsystem.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

@Entity
@Table(
        name = "claim",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_claim_number", columnNames = "claim_number")
        }
)
public class Claim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "policy_id", nullable = false)
    private Long policyId;

    @Column(name = "adjuster_id")
    private Long adjusterId;

    @Column(name = "claim_number", nullable = false, unique = true, length = 50)
    private String claimNumber;

    @Column(name = "incident_date", nullable = false)
    private LocalDate incidentDate;

    @Column(name = "amount_requested", precision = 12, scale = 2, nullable = false)
    private BigDecimal amountRequested;

    @Column(name = "status", nullable = false, length = 30)
    private String status = "SUBMITTED";

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "created_at", updatable = false)
    private OffsetDateTime createdAt;

    // Default Constructor
    public Claim() {
    }

    // All-Arguments Constructor
    public Claim(Long id, Long policyId, Long adjusterId, String claimNumber,
                 LocalDate incidentDate, BigDecimal amountRequested,
                 String status, String description, OffsetDateTime createdAt) {
        this.id = id;
        this.policyId = policyId;
        this.adjusterId = adjusterId;
        this.claimNumber = claimNumber;
        this.incidentDate = incidentDate;
        this.amountRequested = amountRequested;
        this.status = status;
        this.description = description;
        this.createdAt = createdAt;
    }


    // PrePersist lifecycle callback to ensure default values
    @PrePersist
    protected void onCreate() {
        if (this.createdAt == null) {
            this.createdAt = OffsetDateTime.now();
        }
        if (this.status == null) {
            this.status = "SUBMITTED";
        }
    }
    public interface ClaimSummary {
        String getClaimNumber();
        BigDecimal getAmountRequested();
        LocalDate getIncidentDate();
        String getStatus();
    }


    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPolicyId() {
        return policyId;
    }

    public void setPolicyId(Long policyId) {
        this.policyId = policyId;
    }

    public Long getAdjusterId() {
        return adjusterId;
    }

    public void setAdjusterId(Long adjusterId) {
        this.adjusterId = adjusterId;
    }

    public String getClaimNumber() {
        return claimNumber;
    }

    public void setClaimNumber(String claimNumber) {
        this.claimNumber = claimNumber;
    }

    public LocalDate getIncidentDate() {
        return incidentDate;
    }

    public void setIncidentDate(LocalDate incidentDate) {
        this.incidentDate = incidentDate;
    }

    public BigDecimal getAmountRequested() {
        return amountRequested;
    }

    public void setAmountRequested(BigDecimal amountRequested) {
        this.amountRequested = amountRequested;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }
}