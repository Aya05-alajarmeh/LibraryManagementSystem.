# Library Management System

## 📌 Project Overview

The **Library Management System** is built as a Java backend project utilizing **Servlets**, **JDBC**, and **REST APIs**, structured across three core development phases: Servlet Web Application, JDBC Database Integration, and REST API Development.

---

## 🗄️ Database Architecture & Schema

The relational database architecture includes the following core tables and relations:

- **`people`**: Stores foundational identity records including `person_id` (Primary Key), `first_name`, `second_name`, `third_name`, `last_name`, `email`, `phone_number`, and `is_deleted`.
- **`users`**: Represents system operators/staff with authentication fields (`person_id` acting as a Foreign Key and Primary Key linked to `people`, `username`, `password`, `role`).
- **`members`**: Represents library patrons extending from `people` via `person_id`, tracking `registration_date` and `is_deleted`.
- **`categories`**: Defines classifications for books containing `category_id` (Primary Key), `category_name`, `description`, and `is_deleted`.
- **`books`**: Manages the catalog using `book_id` (Primary Key), `title`, `author`, `category_id` (Foreign Key referencing `categories`), `isbn`, and `is_deleted`.
- **`book_copies`**: Tracks individual physical copies of books via `copy_id` (Primary Key), `book_id` (Foreign Key referencing `books`), `barcode`, `status`, and `is_deleted`.
- **`borrows`**: Logs book circulation transactions via `borrow_id` (Primary Key), `copy_id` (Foreign Key to `book_copies`), `member_id` (Foreign Key to `members`), `user_id` (Foreign Key to `users`), `borrow_date`, `due_date`, `return_date`, and transaction `status`.
- **`fines`**: Manages financial penalties linked to circulation records via `fine_id` (Primary Key), `borrow_id` (Foreign Key referencing `borrows`), `amount`, and `status`.

---

## 🖥️ System Navigation & Modules

- **Dashboard**: Displays a system overview with statistics such as Total Copies, Total Members, Available Books, Borrowed Books, and Categories.
- **Users**: Accessible for user management functions (restricted to ADMIN roles).
- **Members**: Allows management of library members, including viewing, adding, editing, and deleting member records.
- **Books**: Facilitates book management, tracking individual copies, adding new books, and handling book borrowing actions.
- **Categories**: Provides options to view, add, edit, and delete book categories.
- **Borrowings**: Manages active borrowings and allows returning borrowed books.
- **Fines**: Handles outstanding penalties, validation checks, and payment processing via backend integration.

---

## ⚙️ Project Structure & Execution Guide

### 1. Project Structure

The application code is strictly separated into layered components:

- **Controller Layer**: Servlets handling HTTP requests, response routing, and session management (`HttpSession`).
- **Service Layer**: Business logic, input validation rules, and exception handling.
- **DAO Layer**: JDBC database operations utilizing SQL queries (`SELECT`, `INSERT`, `UPDATE`, `DELETE`).
- **DTO / Model Layer**: Data transfer objects mapped to JSON using Gson.

### 2. Tomcat Configuration & Setup

1. Ensure you have the **Java Development Kit (JDK)** and a servlet container like **Apache Tomcat** installed.
2. Configure your IDE to map the project deployment structure to Tomcat.
3. Set up your database instance and execute the schema initialization scripts based on the provided ER diagram.
4. Update database connection properties within your configuration files.

### 3. How to Run the Application

1. Build the project artifact (WAR package or exploded deployment directory).
2. Start your Apache Tomcat server instance.
3. Open a web browser and navigate to the application context path (e.g., `http://localhost:8080/librarysystem-1.0-SNAPSHOT/login.html`) to initialize the session login workflow.
