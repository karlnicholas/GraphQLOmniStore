# GraphQLOmniStore

GraphQLOmniStore is a multi-module Spring Boot application designed to demonstrate the use of GraphQL in a reactive microservices architecture. It leverages Spring WebFlux and R2DBC for non-blocking I/O and database interactions.

## Modules

The project is divided into the following modules:

- **catalog**: Manages the product catalog information.
- **inventory**: Handles product inventory levels and stock management.
- **review**: Manages customer reviews and ratings for products.

## Technology Stack

- **Java**: 21
- **Framework**: Spring Boot
- **API**: Spring GraphQL
- **Reactive Stack**: Spring WebFlux, Project Reactor
- **Database Access**: Spring Data R2DBC
- **Database**: H2 (In-memory)

## Getting Started

### Prerequisites

- JDK 21 installed
- Maven installed

### Building the Project

To build the entire project, run the following command from the root directory:

```bash
mvn clean install
```

### Running the Modules

Each module is a standalone Spring Boot application. You can run them individually.

For example, to run the `catalog` service:

```bash
cd catalog
mvn spring-boot:run
```

Repeat for `inventory` and `review` as needed.

## License

[MIT](LICENSE)
