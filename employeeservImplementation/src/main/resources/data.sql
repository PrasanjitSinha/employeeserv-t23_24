DROP TABLE IF EXISTS employees;

CREATE TABLE employees (
                              id INT AUTO_INCREMENT  PRIMARY KEY,
                              first_name VARCHAR(255) NOT NULL,
                              last_name VARCHAR(255) NOT NULL,
                              date_of_birth DATE NOT NULL,
                              address_id INT,
                              FOREIGN KEY (address_id) REFERENCES address(id)

);