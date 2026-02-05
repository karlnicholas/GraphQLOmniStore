package com.omnistore.review;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

@Controller
public class ReviewController {

  private final ReviewRepository repository;

  public ReviewController(ReviewRepository repository) {
    this.repository = repository;
  }

  @QueryMapping
  public Flux<Review> reviews(@Argument String productId) {
    return repository.findByProductId(productId);
  }
}