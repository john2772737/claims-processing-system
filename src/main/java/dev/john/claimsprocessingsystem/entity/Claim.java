package dev.john.claimsprocessingsystem.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;

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


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "policy_id", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Policy policyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "adjuster_id", nullable = true) // Set to true as it is optional
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Adjuster adjusterId;

    @NotBlank(message = "Claim number is required")
    @Column(name = "claim_number", nullable = false, unique = true, length = 50)
    private String claimNumber;

    @NotNull(message = "Incident date is required")
    @PastOrPresent(message = "Incident date cannot be in the future")
    @Column(name = "incident_date", nullable = false)
    private LocalDate incidentDate;

    @NotNull(message = "Amount requested is required")
    @Positive(message = "Amount requested must be greater than zero")
    @Column(name = "amount_requested", precision = 12, scale = 2, nullable = false)
    private BigDecimal amountRequested;

    @NotBlank(message = "Status is required")
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
    public Claim(Long id, Policy policyId, Adjuster adjusterId, String claimNumber,
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

    public Policy getPolicyId() {
        return policyId;
    }

    public void setPolicyId(Policy policyId) {
        this.policyId = policyId;
    }

    public Adjuster getAdjusterId() {
        return adjusterId;
    }

    public void setAdjusterId(Adjuster adjusterId) {
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