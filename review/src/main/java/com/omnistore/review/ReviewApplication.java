package com.omnistore.review;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.annotation.Id;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

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

// --- Handler ---
@Configuration
class ReviewHandler {
    private final ReviewRepository repository;

    public ReviewHandler(ReviewRepository repository) {
        this.repository = repository;
    }

    public Mono<ServerResponse> getReviewsForProduct(ServerRequest request) {
        // We use query param here for variety (e.g., /reviews?productId=p-101)
        // This is a common pattern for "Get by Foreign Key"
        String productId = request.queryParam("productId").orElse("");

        if (productId.isEmpty()) {
            return ServerResponse.badRequest().bodyValue("Missing productId query param");
        }

        return ok().body(repository.findByProductId(productId), Review.class);
    }
}

// --- Router ---
@Configuration
class ReviewRouter {
    @Bean
    public RouterFunction<ServerResponse> reviewRoutes(ReviewHandler handler) {
        return route()
            .GET("/reviews", handler::getReviewsForProduct)
            .build();
    }
}