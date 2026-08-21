CREATE TABLE policy_holder (
                               id BIGSERIAL PRIMARY KEY,
                               name VARCHAR(100) NOT NULL,
                               email VARCHAR(100) UNIQUE NOT NULL,
                               password VARCHAR(255) NOT NULL,
                               date_of_birth DATE
);