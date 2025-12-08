# Pismo Challenge

This repository contains the solution to the Pismo coding challenge. The challenge involves building a simple application that demonstrates proficiency in software development, including coding, testing, and documentation.
## Table of Contents
- [Installation](#installation)
- [Usage](#usage)
- [Features](#features)
- [Technologies Used](#technologies-used)


## Installation
To install the application, follow these steps:
1. Clone repository
2. Navigate to the project directory:
    ```bash
    cd pismo-challenge
    ```
3. Run the docker-compose to start the Postgres database:
   ```bash
    docker-compose up -d
    ```
4. Build the project and run unit / E2E tests using Maven:
   ```bash
    mvn clean install
    ```
## Usage

Use the following command to run the application and start migrations:
```bash
java -jar target/pismo-1.0.jar
```

## API Documentation
To access endpoints please use the following base URL:  
[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

## Technologies Used
- Java 21
- Maven for dependency management
- Docker
- Flyway for database migrations
- Postgres Database
- Spring Boot


## Contact
For any questions or inquiries, please contact my [linkedin](https://linkedin.com/in/marcellocordeiro) 
