CREATE TABLE IF NOT EXISTS student (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         email VARCHAR(255) NOT NULL,
                         school VARCHAR(255),
                         age INT
);
