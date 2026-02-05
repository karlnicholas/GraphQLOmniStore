package com.omnistore.catalog;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class CatalogController {

  private final ProductRepository repository;

  public CatalogController(ProductRepository repository) {
    this.repository = repository;
  }

  @QueryMapping
  public Flux<Product> products() {
    return repository.findAll();
  }

  @QueryMapping
  public Mono<Product> product(@Argument String id) {
    return repository.findById(id);
  }
}