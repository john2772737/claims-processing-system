
CREATE TABLE payout (
                        id BIGSERIAL PRIMARY KEY,
                        claim_id BIGINT NOT NULL UNIQUE, -- 1-to-1 relationship with claim
                        payout_amount DECIMAL(12, 2) NOT NULL,
                        payout_status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
                        reference_number VARCHAR(100) UNIQUE,
                        payment_date TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,

    -- Foreign Key Check:
                        CONSTRAINT fk_payout_claim FOREIGN KEY (claim_id)
                            REFERENCES claim(id) ON DELETE CASCADE
);