package com.omnistore.review;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.annotation.Id;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@SpringBootApplication
public class ReviewApplication {
    public static void main(String[] args) {
        System.setProperty("server.port", "8083");
        SpringApplication.run(ReviewApplication.class, args);
    }
}

// --- Domain ---
record Review(@Id Long id, String productId, String author, String comment, Integer starRating) {}

interface ReviewRepository extends R2dbcRepository<Review, Long> {
    Flux<Review> findByProductId(String productId);
}

