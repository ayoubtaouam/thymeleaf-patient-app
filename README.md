# Patient Management Application

This is a Spring Boot-based web application for managing patient records. It allows users to add, edit, delete, and search for patients, with role-based access control implemented using Spring Security.

## Features

- **Patient Management**: Add, edit, and delete patient records.
- **Pagination**: Paginate the list of patients.
- **Search**: Search for patients by first name or last name.
- **Security**: Role-based access with the ability to restrict certain actions (e.g., only admins can add or delete patients).

## Project Structure

1. **Entities**: Contains the `Patient` entity class.
2. **Repositories**: Manages the `Patient` data access layer using Spring Data JPA.
3. **Security**: Configures Spring Security for authentication and authorization.
4. **Web**: Contains the main controller (`PatientController`) and view templates.

## Key Components

### 1. `Patient.java` (Entity)

This class represents a `Patient` in the system and is mapped to the `Patients` table in the database.

- **Attributes**:
  - `id`: Unique identifier for each patient.
  - `firstName` & `lastName`: Patient's first and last names.
  - `birthDate`: The birthdate of the patient.
  - `ill`: A boolean indicating if the patient is currently ill.
  - `score`: A score representing the patient's health or condition (must be at least 10).

### 2. `PatientRepository.java` (Repository)

The `PatientRepository` interface extends `JpaRepository` and provides a custom method for searching patients by their first or last names.

- **Method**: `findByFirstNameContainsIgnoreCaseOrLastNameContainsIgnoreCase` - Searches for patients by first or last name using case-insensitive matching.

### 3. `SecurityConfig.java` (Security Configuration)

This class sets up the security configuration for the application, including:
- Custom `UserDetailsService` for managing users.
- Role-based access control, allowing admins to add, edit, or delete patients, and regular users to view them.
- Custom login page, with access control for various URL patterns.

### 4. `PatientController.java` (Controller)

This class manages the HTTP requests related to patients. It provides endpoints for:
- **Listing patients** with pagination and search functionality (`/index`).
- **Adding a new patient** (`/formPatient`, `/savePatient`).
- **Editing patient details** (`/editPatient`).
- **Deleting a patient** (`/deletePatient`).

## Requirements

- **Java 21** or higher
- **Spring Boot 3.x**
- **MySQL** or **MariaDB** database (configuration for MySQL is provided)
- **Maven** for building the application

## Setup Instructions

### 1. Clone the repository

```bash
git clone https://github.com/your-username/patient-management-app.git
cd patient-management-app
```

### 2. Configure the Database

Make sure you have MySQL or MariaDB installed on your local machine. Create a database named `patients_db` if it doesn’t exist.

Edit the `src/main/resources/application.properties` file to set your database credentials:

```properties
spring.application.name=patients-app
server.port=8080
spring.datasource.url=jdbc:mysql://localhost:3306/patients_db?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MariaDBDialect
```

> If you’re using MariaDB, the dialect is `org.hibernate.dialect.MariaDBDialect`. For MySQL, you can use `org.hibernate.dialect.MySQLDialect`.

### 3. Build the Application

You can build the project with Maven by running:

```bash
mvn clean install
```

### 4. Run the Application

Once the project is built, you can run the application using:

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`.

### 5. Database Initialization

The application uses **Spring JPA** to interact with the database. The `ddl-auto=update` setting ensures that the database schema is automatically created and updated based on the entity classes.

> If you prefer to use an external database, ensure that your database is properly configured and running.

### 6. User Authentication and Authorization

- **Admin Role**: Users with the `ADMIN` role can manage patients (add, edit, delete).
- **User Role**: Regular users can only view patients.

The application uses **Spring Security** for authentication and authorization. You can configure user roles within the application or use external authentication providers.

### 7. Access Pages

- **Login Page**: `/login`
- **Home/Patient List**: `/index`
- **Add Patient**: `/formPatient`
- **Edit Patient**: `/editPatient?id=<patientId>`
- **Delete Patient**: `/deletePatient?id=<patientId>`

## Dependencies

This project uses the following dependencies:

- **Spring Boot Starter Data JPA**: For database interactions.
- **Spring Boot Starter Web**: For building the web application.
- **Spring Boot Starter Thymeleaf**: For rendering HTML views.
- **Spring Boot Starter Security**: For implementing user authentication and authorization.
- **MySQL Connector**: For connecting to the MySQL database.
- **Lombok**: For reducing boilerplate code.
- **Bootstrap**: For frontend styling.
- **Thymeleaf Layout Dialect**: For easier layout management in Thymeleaf templates.

### Maven Dependencies

Here’s a quick summary of the important dependencies in the `pom.xml`:

```xml
<dependencies>
    <!-- Spring Boot Starter Data JPA for database interaction -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>

    <!-- Spring Boot Starter Thymeleaf for templating -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-thymeleaf</artifactId>
    </dependency>

    <!-- MySQL Connector -->
    <dependency>
        <groupId>com.mysql</groupId>
        <artifactId>mysql-connector-j</artifactId>
        <scope>runtime</scope>
    </dependency>

    <!-- Spring Boot Starter Security -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>

    <!-- Bootstrap for UI styling -->
    <dependency>
        <groupId>org.webjars</groupId>
        <artifactId>bootstrap</artifactId>
        <version>5.3.3</version>
    </dependency>

    <!-- Thymeleaf Layout Dialect for better templating -->
    <dependency>
        <groupId>nz.net.ultraq.thymeleaf</groupId>
        <artifactId>thymeleaf-layout-dialect</artifactId>
        <version>3.2.1</version>
    </dependency>
</dependencies>
```

## Future Improvements

- **Enhanced Search**: Add more filtering options (e.g., search by date of birth, score).
- **User Management**: Allow admins to create and manage users.
- **Export Patient Data**: Export patient records to formats like CSV, PDF, or Excel.
- **Internationalization (i18n)**: Add support for multiple languages.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
