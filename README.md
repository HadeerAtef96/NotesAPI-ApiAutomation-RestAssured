# Notes API Automation Framework

API Test Automation Framework developed using Java and Rest Assured to automate testing of Notes API endpoints.

## Project Overview

This project automates API testing for the Notes application using Rest Assured. It covers different API operations and validates request and response behavior.

## Technologies Used

- Rest Assured with Java
- Maven Project
- TestNG as Testing Framework
- Request/Response Object Model Design Pattern
- Test Reporting using Allure
- Logging using Log4j2
- Remote Execution on CI Pipeline using GitHub Actions
- Test Data Management using Json
- Test Data Generation using JavaFaker

## Endpoints
- Register new User
- Login & Logout
- Update Profile
- Get Profile
- Create New Note
- Update Note
- Get All Notes
- Get Note by ID
- Delete Note

## Features
- Set Request Headers
- Set Request Body for Post/Put/Delete
- Set Request Parameters for Get
- Create Abstracted Method for Sending all types of API Requests
- Status Code Validation
- Response Body Validation using JsonPath and TestNG Assertions
- Allure Report for Reporting All Test Results & Logging All Test Steps & Uploding all Requests/Response Sent

## Test Scenarios

### Positive Tests

- Register new User
- Login with Valid Data
- Create note with valid data
- Get existing note
- Update existing note
- Delete existing note

### Negative Tests

- Create note with invalid data
- Get non-existing note
- Update non-existing note
- Delete non-existing note

## Project Structure

```
src
├── main
│   └── java
├── test
│   └── java
├── resources
│   └── testng.xml
└── pom.xml
```

## Running Tests

Run all tests using Maven:

```bash
mvn test
```

Run specific groups:

```bash
mvn test -Dgroups=positive
```

```bash
mvn test -Dgroups=negative
```

## Generate Allure Report

Generate report:

```bash
allure generate target/allure-results --clean -o target/allure-report
```

Open report:

```bash
allure open target/allure-report
```

## Author

Hadeer Atef

Software Testing Engineer

GitHub:
https://github.com/HadeerAtef96
