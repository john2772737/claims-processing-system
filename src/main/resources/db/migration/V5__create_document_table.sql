CREATE TABLE document (
                          id BIGSERIAL PRIMARY KEY,
                          claim_id BIGINT NOT NULL,
                          file_name VARCHAR(255) NOT NULL,
                          file_type VARCHAR(50) NOT NULL,
                          storage_url VARCHAR(500) NOT NULL,
                          uploaded_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,

    -- Foreign Key Check:
                          CONSTRAINT fk_document_claim FOREIGN KEY (claim_id)
                              REFERENCES claim(id) ON DELETE CASCADE
);