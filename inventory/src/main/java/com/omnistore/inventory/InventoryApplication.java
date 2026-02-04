package com.omnistore.inventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.annotation.Id;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@SpringBootApplication
public class InventoryApplication {
    public static void main(String[] args) {
        System.setProperty("server.port", "8082");
        SpringApplication.run(InventoryApplication.class, args);
    }
}

// --- Domain ---
record Inventory(@Id Long id, String productId, Integer quantity, String location) {}

interface InventoryRepository extends R2dbcRepository<Inventory, Long> {
    Mono<Inventory> findByProductId(String productId);
}

// --- Handler ---
@Configuration
class InventoryHandler {
    private final InventoryRepository repository;

    public InventoryHandler(InventoryRepository repository) {
        this.repository = repository;
    }

    public Mono<ServerResponse> getStock(ServerRequest request) {
        String productId = request.pathVariable("productId");
        return repository.findByProductId(productId)
            .flatMap(inv -> ok().bodyValue(inv))
            .switchIfEmpty(ServerResponse.notFound().build());
    }
}

// --- Router ---
@Configuration
class InventoryRouter {
    @Bean
    public RouterFunction<ServerResponse> inventoryRoutes(InventoryHandler handler) {
        return route()
            .GET("/inventory/{productId}", handler::getStock)
            .build();
    }
}