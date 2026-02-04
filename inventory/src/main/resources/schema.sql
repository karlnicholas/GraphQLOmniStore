CREATE TABLE inventory (
                           id IDENTITY PRIMARY KEY,
                           product_id VARCHAR(255),
                           quantity INT,
                           location VARCHAR(255)
);