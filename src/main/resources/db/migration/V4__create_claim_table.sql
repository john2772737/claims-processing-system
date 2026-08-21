CREATE TABLE claim (
                       id BIGSERIAL PRIMARY KEY,
                       policy_id BIGINT NOT NULL,
                       adjuster_id BIGINT, -- Nullable until assigned to an adjuster
                       claim_number VARCHAR(50) NOT NULL UNIQUE,
                       incident_date DATE NOT NULL,
                       amount_requested DECIMAL(12, 2) NOT NULL,
                       status VARCHAR(30) NOT NULL DEFAULT 'SUBMITTED',
                       description TEXT,
                       created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,

    -- Foreign Key Checks:
                       CONSTRAINT fk_claim_policy FOREIGN KEY (policy_id)
                           REFERENCES policy(id) ON DELETE CASCADE,
                       CONSTRAINT fk_claim_adjuster FOREIGN KEY (adjuster_id)
                           REFERENCES adjuster(id) ON DELETE SET NULL
);