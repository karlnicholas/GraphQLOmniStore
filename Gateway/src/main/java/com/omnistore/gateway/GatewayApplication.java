package com.omnistore.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@SpringBootApplication
public class GatewayApplication {

  public static void main(String[] args) {
    System.setProperty("server.port", "8080");
    SpringApplication.run(GatewayApplication.class, args);
  }

  // 1. Create Clients for downstream services
  @Bean
  public HttpGraphQlClient catalogClient() {
    WebClient webClient = WebClient.builder().baseUrl("http://localhost:8081/graphql").build();
    return HttpGraphQlClient.builder(webClient).build();
  }

  @Bean
  public HttpGraphQlClient inventoryClient() {
    WebClient webClient = WebClient.builder().baseUrl("http://localhost:8082/graphql").build();
    return HttpGraphQlClient.builder(webClient).build();
  }

  @Bean
  public HttpGraphQlClient reviewClient() {
    WebClient webClient = WebClient.builder().baseUrl("http://localhost:8083/graphql").build();
    return HttpGraphQlClient.builder(webClient).build();
  }

  // 2. Wire the logic together
  @Bean
  public RuntimeWiringConfigurer runtimeWiringConfigurer(GatewayDataFetcher dataFetcher) {
    return wiringBuilder -> wiringBuilder
        .type("Query", type -> type
            .dataFetcher("product", dataFetcher::getProduct)
            // 2. ADD THIS LINE:
            .dataFetcher("products", env -> dataFetcher.getProducts()))
        .type("Product", type -> type
            .dataFetcher("inventory", dataFetcher::getInventoryForProduct)
            .dataFetcher("reviews", dataFetcher::getReviewsForProduct));
  }
}