CREATE TABLE adjuster (
                          id BIGSERIAL PRIMARY KEY,
                          employee_code VARCHAR(30) NOT NULL UNIQUE,
                          first_name VARCHAR(50) NOT NULL,
                          last_name VARCHAR(50) NOT NULL,
                          email VARCHAR(100) NOT NULL UNIQUE
);