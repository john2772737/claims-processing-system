ALTER TABLE policy_holders
    ADD COLUMN IF NOT EXISTS phone_number VARCHAR(20);
-- Remove the single 'name' column
ALTER TABLE policy_holders DROP COLUMN IF EXISTS name;

-- Safely add columns only if they do not already exist
ALTER TABLE policy_holders
    ADD COLUMN IF NOT EXISTS phone_number VARCHAR(20),
    ADD COLUMN IF NOT EXISTS first_name VARCHAR(50),
    ADD COLUMN IF NOT EXISTS last_name VARCHAR(50);