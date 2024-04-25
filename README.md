
# Shopify Order Management System

This project is a Java Spring Boot application designed to manage orders from a Shopify store. It allows users to view all orders and update their fulfillment status through a simple web interface. The backend is integrated with the Shopify Admin REST API to fetch and update order data.

## Features

- View a list of all orders with details such as order number, order date, total amount, fulfillment status, and tracking code.
- Update the fulfillment status of orders by adding a tracking code.

## Prerequisites

Before you begin, ensure you have the following installed:
- Java JDK 17 or newer
- Maven
- Git

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Installation

1. **Clone the repository**

   ```bash
   git clone https://github.com/DKuxa/trackipal-test.git
   cd trackipal-test
   ```

2. **Configure environment variables**

   Create a `.env` file in the root directory of the project and add the following:

   ```plaintext
   SHOPIFY_API_KEY=your_shopify_api_key_here
   ```

3. **Build the project**

   Using Maven:

   ```bash
   mvn clean install
   ```
   

   Or using Gradle:

   ```bash
   gradle build
   ```

4. **Run the application**

   Using Maven:

   ```bash
   mvn spring-boot:run
   ```

   Or using Gradle:

   ```bash
   gradle bootRun
   ```

   The application will start running at <http://localhost:8080>.

## Usage

Navigate to <http://localhost:8080> in your web browser to view and manage the orders.

## Running the tests

Explain how to run the automated tests for this system.

### Unit tests

```bash
mvn test
```

### Integration tests

Integration tests can be run with:

```bash
mvn integration-test
```

## Built With

- [Spring Boot](https://spring.io/projects/spring-boot) - The framework used
- [Maven](https://maven.apache.org/) - Dependency Management


