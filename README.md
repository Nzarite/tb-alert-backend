#   🖥️ Backend - TBAlert
***
This is the TBAlert backend API built with Spring Boot

![img.png](img.png)
---

## 🚀 Tech Stack

- **Framework**: Spring Boot
- **Database**:  MySQL
- **Authentication and Authorization**: Keycloak
- **Build Tool**: Maven

---

## ⚙️ Installation

```bash
# Clone the repository
git clone https://github.com/Nzarite/tb-alert-backend.git

# Navigate into the directory
cd tb-alert-backend

# Install the dependencies
mvn clean install
```

## 🗄️ Connecting to the database
>Create a database named `tb-alert`

```bash
# Open application properties
cd src/main/resources
vim application.properties
```
> Replace the values for `spring.datasource.username` and `spring.datasource.password` with your login credentials

## 🏃‍♂ Running the server
```bash
# Run the server
mvn spring-boot:run
```

The API will be available at http://localhost:8080
