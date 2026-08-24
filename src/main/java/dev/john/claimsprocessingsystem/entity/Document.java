package dev.john.claimsprocessingsystem.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.OffsetDateTime;

@Entity
@Table(name = "document")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "claim_id", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Claim claimId;

    @NotBlank(message = "File name is required")
    @Column(name = "file_name", nullable = false, length = 255)
    private String fileName;

    @NotBlank(message = "File type is required")
    @Column(name = "file_type", nullable = false, length = 50)
    private String fileType;

    @NotBlank(message = "Storage URL is required")
    @Column(name = "storage_url", nullable = false, length = 500)
    private String storageUrl;

    @NotNull(message = "Uploaded timestamp is required")
    @PastOrPresent(message = "Uploaded timestamp cannot be in the future")
    @Column(name = "uploaded_at", nullable = false)
    private OffsetDateTime uploadedAt;

    public Document() {}

    public Document(Claim claimId, String fileName, String fileType, String storageUrl, OffsetDateTime uploadedAt) {
        this.claimId = claimId;
        this.fileName = fileName;
        this.fileType = fileType;
        this.storageUrl = storageUrl;
        this.uploadedAt = uploadedAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Claim getClaimId() { return claimId; }
    public void setClaimId(Claim claimId) { this.claimId = claimId; }

    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }

    public String getFileType() { return fileType; }
    public void setFileType(String fileType) { this.fileType = fileType; }

    public String getStorageUrl() { return storageUrl; }
    public void setStorageUrl(String storageUrl) { this.storageUrl = storageUrl; }

    public OffsetDateTime getUploadedAt() { return uploadedAt; }
    public void setUploadedAt(OffsetDateTime uploadedAt) { this.uploadedAt = uploadedAt; }
}