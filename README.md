# SaleCampaign

A Spring Boot based REST API project for managing products, campaigns, discounts, and price history in an e-commerce sale campaign system.

---

# Features

* Product Management API
* Campaign Management API
* Apply Discounts on Products
* Price History Tracking
* Scheduler Support
* Pagination Support
* MySQL Database Integration
* RESTful APIs
* Layered Architecture

---

# Tech Stack

* Java 17+
* Spring Boot
* Spring Data JPA
* Hibernate
* MySQL
* Maven
* REST API

---

# Project Structure

```text
src/main/java/com/SALE/SaleCampaign
│
├── Controller
├── DTO
├── Model
├── Repository
├── Scheduler
├── Service
└── SaleCampaignApplication.java
```

---

# API Endpoints

## Product APIs

### Add Product

```http
POST /product/add
```

### Get All Products

```http
GET /product/all
```

---

## Campaign APIs

### Add Campaign

```http
POST /campaign/add
```

---

# Database Configuration

Create a MySQL database:

```sql
CREATE DATABASE SaleCampaign_DB;
```

Update your `application.properties` file:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/SaleCampaign_DB
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

---

# Run the Project

## Clone Repository

```bash
git clone https://github.com/saho03/SaleCampaignx.git
```

## Open Project

Open the project in IntelliJ IDEA or any Java IDE.

## Run Application

Run:

```bash
mvn spring-boot:run
```

Application will start at:

```text
http://localhost:8080
```

---

# Sample JSON

## Add Product

```json
{
  "productName": "Laptop",
  "price": 50000,
  "stock": 10
}
```

## Add Campaign

```json
{
  "campaignName": "Summer Sale",
  "discountPercentage": 20
}
```

---

# Future Improvements

* JWT Authentication
* Role Based Authorization
* Swagger Documentation
* Docker Support
* Unit Testing
* Email Notifications

---

# Author

Shubham Saho

GitHub: [https://github.com/saho03](https://github.com/saho03)
