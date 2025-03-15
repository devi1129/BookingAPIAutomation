# Hotel Booking API Automation Testing

## Overview

This project automates the testing of **Hotel Booking API** endpoints to ensure proper implementation of **CRUD operations**. It is built using **Rest Assured** for API testing, **Cucumber** for behavior-driven development (BDD), **TestNG** for managing and running the tests, **Log4j** for detailed logging, and **ExtentReports** for generating visually rich reports. The framework supports **data-driven testing** using **Excel** and **JSON** files and validates the responses against **JSON schemas** to ensure API correctness.

### Key Features:

- **Create a New Booking**: Supports creating bookings using **Cucumber Data Tables**, **Excel**, and **JSON** files.
- **View Booking Details**: Retrieves booking details based on **Booking IDs**.
- **Update Booking Details**: Updates booking information like **Checkout Date** and **Total Price** using **PUT** and **PATCH** methods.
- **Delete Booking**: Tests the deletion of booking details via the **DELETE** API endpoint.
- **Health Check**: Ensures that the API is up and running by sending a request to the `/ping` endpoint.

## Tools and Technologies Used

- **Java 8+**: The programming language used for the automation framework.
- **Rest Assured**: A Java library for testing RESTful APIs.
- **Cucumber**: A BDD framework for writing test scenarios in natural language.
- **TestNG**: A testing framework used for managing and running tests.
- **Excel (Apache POI)**: Used for data-driven testing through Excel files.
- **ExtentReports**: For generating rich, interactive HTML test reports.
- **Log4j**: For detailed logging to capture test execution details.
- **Maven**: For managing dependencies and building the project.
- **Git**: For version control.

## Prerequisites

Make sure you have the following installed:

- **Java JDK 8+**: [Download Java](https://www.oracle.com/java/technologies/javase-jdk8-downloads.html)
- **Apache Maven**: [Download Maven](https://maven.apache.org/download.cgi)
- **IDE (IntelliJ IDEA/Eclipse)**: For writing and running the tests.
- **ChromeDriver (or WebDriver)**: For HTTP requests and tests.
- **Log4j**: Logging configuration is already included in the `log4j.properties` file.
- **Test Data**: The project requires Excel and JSON files for data-driven tests.

## Getting Started

### 1. Clone the Repository

Clone the repository to your local machine:

```bash
git clone https://github.com/your-username/hotel-booking-api-automation.git
cd hotel-booking-api-automation
