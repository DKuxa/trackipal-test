
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

## Icebox

The following features and improvements are planned for future development:

1. **Automated Testing Enhancements**
- Complete the automated tests for pagination when retrieving orders from the Shopify API. This will ensure the application can handle large volumes of orders efficiently. 
- Develop automated tests for handling Shopify API errors, including rate limits. This will improve the robustness of the application by ensuring it handles API constraints gracefully.

2. **Additional Order Management Features**
- Implement features such as cancelling orders, modifying orders, and more detailed filtering/sorting options for better order management.

3. **Performance Optimization**
- Investigate and implement performance optimizations, particularly in data handling and API interaction to improve response times and scalability.

4. **User Interface Improvements**
- Enhance the user interface for a more intuitive and responsive user experience, incorporating real-time updates and mobile compatibility.

These items are acknowledged as important but were not addressed in the initial development phase due to time constraints or current priorities.


