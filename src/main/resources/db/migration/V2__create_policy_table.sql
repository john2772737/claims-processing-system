CREATE TABLE policy (
                        id BIGSERIAL PRIMARY KEY,
                        policy_holder_id BIGINT NOT NULL,
                        policy_number VARCHAR(50) NOT NULL UNIQUE,
                        coverage_amount DECIMAL(12, 2) NOT NULL,
                        start_date DATE NOT NULL,
                        status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',

                        CONSTRAINT fk_policy_holder FOREIGN KEY (policy_holder_id)
                            REFERENCES policy_holder(id) ON DELETE CASCADE
);