# Library Management System 

##  Project Overview

The **Library Management System** is built as a Java backend project utilizing **Servlets**, **JDBC**, and **REST APIs**, structured across three core development phases: Servlet Web Application, JDBC Database Integration, and REST API Development.

---

##  Database Architecture & Schema

The relational database architecture includes the following core tables and relations (Total of 8 Tables & 30 Stored Procedures):

- **`people`**: Stores foundational identity records including `person_id` (Primary Key), `first_name`, `second_name`, `third_name`, `last_name`, `email`, `phone_number`, and `is_deleted`.
- **`users`**: Represents system operators/staff with authentication fields (`person_id` acting as a Foreign Key and Primary Key linked to `people`, `username`, `password`, `role`).
- **`members`**: Represents library patrons extending from `people` via `person_id`, tracking `registration_date` and `is_deleted`.
- **`categories`**: Defines classifications for books containing `category_id` (Primary Key), `category_name`, `description`, and `is_deleted`.
- **`books`**: Manages the catalog using `book_id` (Primary Key), `title`, `author`, `category_id` (Foreign Key referencing `categories`), `isbn`, and `is_deleted`.
- **`book_copies`**: Tracks individual physical copies of books via `copy_id` (Primary Key), `book_id` (Foreign Key referencing `books`), `barcode`, `status`, and `is_deleted`.
- **`borrows`**: Logs book circulation transactions via `borrow_id` (Primary Key), `copy_id` (Foreign Key to `book_copies`), `member_id` (Foreign Key to `members`), `user_id` (Foreign Key to `users`), `borrow_date`, `due_date`, `return_date`, and transaction `status`.
- **`fines`**: Manages financial penalties linked to circulation records via `fine_id` (Primary Key), `borrow_id` (Foreign Key referencing `borrows`), `amount`, and `status`.

---

##  System Navigation & Modules

- **Dashboard**: Displays a system overview with statistics such as Total Copies, Total Members, Available Books, Borrowed Books, and Categories.
- **Users**: Accessible for user management functions (restricted to ADMIN roles).
- **Members**: Allows management of library members, including viewing, adding, editing, and deleting member records.
- **Books**: Facilitates book management, tracking individual copies, adding new books, and handling book borrowing actions.
- **Categories**: Provides options to view, add, edit, and delete book categories.
- **Borrowings**: Manages active borrowings and allows returning borrowed books.
- **Fines**: Handles outstanding penalties, validation checks, and payment processing via backend integration.

---

##  Project Structure & Execution Guide

### 1. Project Structure

The application code is strictly separated into layered components:

- **Controller Layer**: Servlets handling HTTP requests, response routing, and session management (`HttpSession`).
- **Service Layer**: Business logic, input validation rules, and exception handling.
- **DAO Layer**: JDBC database operations utilizing SQL queries and Stored Procedures.
- **DTO / Model Layer**: Data transfer objects mapped to JSON using Gson.

### 2. Database Setup & Restoration (SQL Server)
To get the database up and running with pre-configured dummy data and test users:
1. Open **SQL Server Management Studio (SSMS)**.
2. Right-click on **Databases** and select **Restore Database...**
3. Choose **Device**, click the `...` button, and select the backup file (`.bak`) located inside the `database` folder of this repository.
4. Click **OK** to complete the restoration process.
5. *Pre-configured Login Account:* You can sign in directly using:
   - **Username:** `alia`
   - **Password:** `123456`

### 3. Tomcat Configuration & Setup
1. Ensure you have the **Java Development Kit (JDK 17+)** and **Apache Tomcat (v10+)** installed.
2. Configure your IDE (IntelliJ / Eclipse) to map the project deployment structure to Tomcat.
3. Update your database connection URL, username, and password inside your Java project's connection configuration class (`DBConnection.java`).

### 4. How to Run the Application
1. Build the project artifact (Run Maven clean package to generate the WAR file):
   ```bash
   mvn clean package
   
### Appendix: Apache Tomcat Installation from Scratch (If not installed)
If you don't have Apache Tomcat set up on your machine yet, follow these steps to configure it from scratch:

1. **Download Tomcat:**
   * Go to the official website and download **Tomcat 10** (or higher) to ensure compatibility with Jakarta EE: [tomcat.apache.org](https://tomcat.apache.org/)
   * Download the **Core** package in **ZIP** format (under the *Binary Distributions* section).

2. **Extract the Files:**
   * Extract the downloaded ZIP file to a preferred directory on your local machine (e.g., `C:\tomcat` or on your Desktop).

3. **Configure Tomcat in your IDE (IntelliJ / Eclipse):**
   * Open your IDE and navigate to your application server configurations (Application Servers / Server Configurations).
   * Add a new server and select **Apache Tomcat**.
   * Point the server directory to the folder where you extracted Tomcat in step 2.
   * Make sure your JDK path (JDK 17+) is correctly linked to the server configuration.

4. **Run the Application & Access Link:**
   * Build your project artifact using Maven to generate the `.war` file:
     ```bash
     mvn clean package
     ```
   * Deploy the generated `.war` file to your configured Tomcat server instance.
   * Start your Apache Tomcat server.
   * Open your web browser and navigate to the default login path:
     ```text
     http://localhost:8080/librarysystem-1.0-SNAPSHOT/login.html
     ```

> **Note on Application Link:** 
> The context path `librarysystem-1.0-SNAPSHOT` is the default name generated from the WAR file. If your deployed context path or WAR file name differs on your Tomcat server, make sure to adjust the URL accordingly.

