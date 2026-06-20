# Notes API Automation Framework

API Test Automation Framework developed using Java and Rest Assured to automate testing of Notes API endpoints.

## Project Overview

This project automates Notes API with Positive and Negative test cases for each Endpoint and Validate E2E cenarios for CRUD operations on Notes, within an Automation Framework

## Allure Report
### Generate and Open Allure Report easily in one step by running Open_Allure_Report.bat file
<img width="1888" height="1053" alt="image" src="https://github.com/user-attachments/assets/ea9dc18d-105d-4625-8621-fbc4313dbf71" />

### Positive Tests & Negative Tests
<img width="1910" height="1076" alt="image" src="https://github.com/user-attachments/assets/dfb92a40-d62b-4fe4-abf7-86db3d2f32e6" />

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
- Local Execution using TestNG xml file
- Remote Execution using CI Pipeline on GitHub Actions
- Auto Generation of Allure Report after Test Run

## Utilities
- API Manager for sending all API Requests and different validations on Responses
- Data Generator for generating different Test Data
- Json Reader for reading Test Data from Json Files using JsonPath
- Properties Reader for reading Project Configurations from Properties Files
- LogHelper for Logging Info , Warning and Error Steps with Log4j2

## Project Structure
NotesAPI-ApiAutomation-RestAssured
│
├── .github
│   └── workflows
│       ├── RunApiTests.yml
│       └── Open_Allure_Report.bat
│
├── src
│   │
│   ├── main
│   │   ├── java
│   │   │   └── utils
│   │   │       ├── APIsManager.java
│   │   │       ├── AllureReportHelper.java
│   │   │       ├── DataGenerator.java
│   │   │       ├── JsonReader.java
│   │   │       ├── LogHelper.java
│   │   │       └── PropertiesReader.java
│   │   │
│   │   └── resources
│   │       ├── allure.properties
│   │       ├── log4j2.properties
│   │       └── settings.properties
│   │
│   └── test
│       ├── java
│       │
│       │   ├── apiObjectModels
│       │   │   ├── Register_RequestModel.java
│       │   │   ├── Register_ResponseModel.java
│       │   │   ├── Login_RequestModel.java
│       │   │   ├── Login_ResponseModel.java
│       │   │   ├── CreateNote_RequestModel.java
│       │   │   ├── CreateNote_ResponseModel.java
│       │   │   ├── GetAllNotes_RequestModel.java
│       │   │   ├── GetAllNotes_ResponseModel.java
│       │   │   ├── GetNote_RequestModel.java
│       │   │   ├── GetNote_ResponseModel.java
│       │   │   ├── UpdateNote_RequestModel.java
│       │   │   ├── UpdateNote_ResponseModel.java
│       │   │   ├── DeleteNote_RequestModel.java
│       │   │   ├── DeleteNote_ResponseModel.java
│       │   │   ├── GetProfile_RequestModel.java
│       │   │   ├── GetProfile_ResponseModel.java
│       │   │   ├── UpdateProfile_RequestModel.java
│       │   │   ├── UpdateProfile_ResponseModel.java
│       │   │   ├── Logout_RequestModel.java
│       │   │   └── Logout_ResponseModel.java
│       │   │
│       │   └── testCases
│       │       ├── BaseTest.java
│       │       ├── RegisterTests.java
│       │       ├── LoginTests.java
│       │       ├── CreateNoteTests.java
│       │       ├── GetAllNotesTests.java
│       │       ├── GetNoteTests.java
│       │       ├── UpdateNoteTests.java
│       │       ├── DeleteNoteTests.java
│       │       ├── GetProfileTests.java
│       │       ├── UpdateProfileTests.java
│       │       └── LogoutTests.java
│       │
│       └── resources
│           ├── TestData.json
│           │
│           └── TestNG_Suites
│               ├── PositiveTestCases.xml
│               ├── NegativeTestCases.xml
│               ├── RunAllTests.xml
│               ├── RunAllTests_2.xml
│               └── RunSingleTest.xml
│
├── Open_Allure_Report.bat
├── pom.xml
├── README.md
└── .gitignore

## Running Tests

### Run all tests using Maven:
```bash
mvn clean test
```
### Run all tests using TestNG Xml
- RunAllTests.xml

### Run positive and negative groups using Maven
```bash
mvn clean test -Dgroups=positive
```

```bash
mvn test -Dgroups=negative
```
### Run positive and negative groups using Maven
- PositiveTestCases.xml
- NegativeTestCases.xml 

## Author

Hadeer Atef

Software Testing Engineer

GitHub:
https://github.com/HadeerAtef96
