# Task Management App

## Description
This is a task management application for team projects built with **Java 11**, **Spring Boot**, **Hibernate**, and **H2 Database**. It allows managing users and tasks, including adding, editing, searching, assigning users to tasks, and deleting users and tasks.

## Technologies Used
- Java 11
- Spring Boot
- Spring Data JPA
- Hibernate
- H2 Database (in-memory)
- Lombok
- JUnit 5
- Mockito

## Features
### Users:
- **Add Users** – Create new users.
- **Search Users** – Ability to search users by last name.
- **Delete Users** – Allows deleting users, but only those not assigned to tasks.
- **Custom Exception Handling**:
  - Throws a custom exception when trying to add a user with an email that already exists in the database.
  - Throws a custom exception when trying to delete a user assigned to a task.

### Tasks:
- **Add Tasks** – Create new tasks, with the ability to assign multiple users.
- **Edit Tasks** – Supports updates of tasks.
- **Search Tasks**:
  - Ability to filter tasks by status.
  - Search tasks assigned to users based on their last name or email.
- **Delete Tasks** – Remove existing tasks.
- **Assign and Unassign Users to Tasks** – Functions to assign users to existing tasks and unassign them.
- **Custom Exception Handling**:
  - Throws a custom exception if there are no users in the database when trying to create a task.

## Postman Collection
In the `postman-collection/` directory, you will find the `TaskManagementApp Requests.postman_collection.json` file, which contains the Postman request collection for testing the API.

### How to import the collection into Postman:
1. Download the `TaskManagementApp Requests.postman_collection.json` file.
2. Open **Postman**.
3. Click **Import** in the top menu.
4. Select the downloaded JSON file and import it into Postman.

The collection contains all available API endpoints along with sample requests.

## How to Build and Run the Application

### Step 1: Clone the repository
Clone the repository locally using the following command:
```bash
git clone <https://github.com/arekBucholc/taskManagementApp.git>
```
### Step 2: Build the project with Maven
```bash
mvn clean install
```
### Step 3: Run the application
```bash
mvn spring-boot:run
```
### Step 4: Testing the application
Once the application is running, the API will be accessible at:
```bash
http://localhost:8080
```
You can test the application using the provided Postman collection

### Running Unit Tests
The application includes unit tests written using JUnit 5 and Mockito. To run the tests, use the following Maven command:
```bash
mvn test
```


### Created by Arkadiusz Bucholc, 2024






