package com.omnistore.inventory;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Controller
public class InventoryController {

  private final InventoryRepository repository;

  public InventoryController(InventoryRepository repository) {
    this.repository = repository;
  }

  @QueryMapping
  public Mono<Inventory> inventory(@Argument String productId) {
    return repository.findByProductId(productId);
  }
}