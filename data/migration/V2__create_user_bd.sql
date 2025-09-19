CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    name VARCHAR(255),
    password VARCHAR(255) NOT NULL,
    failed_attempts INT NOT NULL DEFAULT 0,
    lock_time TIMESTAMP NULL
);

INSERT INTO users (email, name, password, failed_attempts, lock_time)
SELECT 'admin@example.com', 'admin', '$2a$10$RZQC/CbDRTZDJnLbL3m55.xSCVL7blCTrSejjQSAut/JXJmeXyk9K', 0, NULL
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'admin@example.com');
);
