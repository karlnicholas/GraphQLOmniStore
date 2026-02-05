package com.omnistore.gateway;

import graphql.schema.DataFetchingEnvironment;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.Map;

@Component
class GatewayDataFetcher {

  private final HttpGraphQlClient catalogClient;
  private final HttpGraphQlClient inventoryClient;
  private final HttpGraphQlClient reviewClient;

  public GatewayDataFetcher(HttpGraphQlClient catalogClient, HttpGraphQlClient inventoryClient, HttpGraphQlClient reviewClient) {
    this.catalogClient = catalogClient;
    this.inventoryClient = inventoryClient;
    this.reviewClient = reviewClient;
  }

  // 1. Fetch Basic Product Info from Catalog
  // Changed return type to explicit Map
  public CompletableFuture<Map> getProduct(DataFetchingEnvironment env) {
    String id = env.getArgument("id");
    String document = "{ product(id: \"" + id + "\") { id name price } }";

    return catalogClient.document(document)
        .retrieve("product")
        .toEntity(Map.class)
        .toFuture();
  }

  // 2. Fetch Inventory for that Product
  // Changed return type to explicit Map
  public CompletableFuture<Map> getInventoryForProduct(DataFetchingEnvironment env) {
    // Ideally use Map<String, Object> to avoid warnings, but raw Map works for now
    Map product = env.getSource();
    String productId = (String) product.get("id");

    String document = "{ inventory(productId: \"" + productId + "\") { quantity location } }";

    return inventoryClient.document(document)
        .retrieve("inventory")
        .toEntity(Map.class)
        .toFuture();
  }

  // 3. Fetch Reviews for that Product
  // FIX: Changed return type from CompletableFuture<List> to CompletableFuture<List<Map>>
  public CompletableFuture<List<Map>> getReviewsForProduct(DataFetchingEnvironment env) {
    Map product = env.getSource();
    String productId = (String) product.get("id");

    String document = "{ reviews(productId: \"" + productId + "\") { author comment starRating } }";

    return reviewClient.document(document)
        .retrieve("reviews")
        .toEntityList(Map.class)
        .toFuture();
  }
}