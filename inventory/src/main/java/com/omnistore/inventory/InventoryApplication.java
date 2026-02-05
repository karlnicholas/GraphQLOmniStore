package com.omnistore.inventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.annotation.Id;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

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

