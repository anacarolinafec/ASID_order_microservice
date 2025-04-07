# Online-Book-Store-Spring-Boot

This is the backend portion of the Online Bookstore Application, built with Java Spring Boot.

## Table of Contents

- [Overview](#overview)
- [Running the Application](#running-the-application)
- [Features](#features)
- [Dependencies](#dependencies)

## Overview

The backend is responsible for handling authentication, managing books, categories, shopping carts, and order processing. It utilizes Spring Boot, Spring Data JPA, Spring Security, and interacts with a relational database (e.g., MySQL).

## Running the Application

1. Clone the backend repository.
2. Open the project in your preferred Java IDE.
3. Configure the database connection in `application.properties`.
4. Run the application.

## Features

- **Authentication:** User registration, login, and JWT token-based authentication.
- **Book Management:** CRUD operations for books with validation.
- **Category Management:** CRUD operations for book categories and subcategories.
- **Shopping Cart:** Adding books to the cart, viewing the cart, and placing orders.
- **Order Processing:** Calculating the total price and updating book quantities.
- **Database:** Uses a relational database to store book, user, category, and subcategory data.
- **Error Handling:** Implements proper error handling with HTTP status codes.

## Dependencies

- Spring Boot
- Spring Data JPA
- Spring Security
- Lombok
- MySQL Driver
