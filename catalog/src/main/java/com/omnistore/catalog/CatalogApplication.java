package com.omnistore.catalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.annotation.Id;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@SpringBootApplication
public class CatalogApplication {
    public static void main(String[] args) {
        System.setProperty("server.port", "8081");
        SpringApplication.run(CatalogApplication.class, args);
    }
}

// --- Domain ---
record Product(@Id String id, String name, Double price) {}

interface ProductRepository extends R2dbcRepository<Product, String> {}

