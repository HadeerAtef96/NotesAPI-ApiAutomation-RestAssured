# Notes API Automation Framework

API Test Automation Framework developed using Java and Rest Assured to automate testing of Notes API endpoints.

## Project Overview

This project automates API testing for the Notes application using Rest Assured. It covers different API operations and validates request and response behavior.

## Technologies Used

- Java
- Rest Assured
- TestNG
- Maven
- Allure Report
- Git & GitHub

## Features

- Create Note API Testing
- Get Note API Testing
- Update Note API Testing
- Delete Note API Testing
- Request and Response Validation
- Status Code Validation
- Response Body Validation
- Test Reporting with Allure

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

## Test Scenarios

### Positive Tests

- Create note with valid data
- Get existing note
- Update existing note
- Delete existing note

### Negative Tests

- Create note with invalid data
- Get non-existing note
- Update non-existing note
- Delete non-existing note

## Author

Hadeer Atef

Software Testing Engineer

GitHub:
https://github.com/HadeerAtef96
