CREATE TABLE review (
                        id IDENTITY PRIMARY KEY,
                        product_id VARCHAR(255),
                        author VARCHAR(255),
                        comment VARCHAR(1000),
                        star_rating INT
);