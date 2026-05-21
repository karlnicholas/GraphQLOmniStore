drop table if exists inventory;
CREATE TABLE inventory (
                           id INT IDENTITY PRIMARY KEY,
                           product_id VARCHAR(255),
                           quantity INT,
                           location VARCHAR(255)
);