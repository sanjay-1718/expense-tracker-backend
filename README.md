# 💰 Expense Tracker - Backend API

A robust RESTful API for expense tracking built with Spring Boot. Provides secure authentication, expense management, and user data persistence with PostgreSQL.

## 🌐 Live API

**API Base URL:** [https://expense-tracker-backend-mci6.onrender.com](https://expense-tracker-backend-mci6.onrender.com)

## ✨ Features

- 🔐 **JWT Authentication** - Secure user authentication with JSON Web Tokens
- 👤 **User Management** - User registration and login
- 💸 **Expense CRUD Operations** - Create, read, update, and delete expenses
- 🏷️ **Category Filtering** - Filter expenses by category
- 📅 **Date Range Filtering** - Filter expenses by date range
- 🔒 **Spring Security** - Protected endpoints with role-based access
- 🗄️ **PostgreSQL Database** - Reliable data persistence
- 🌐 **CORS Configuration** - Cross-origin resource sharing for frontend integration
- 📝 **Data Validation** - Input validation with Bean Validation

## 🛠️ Tech Stack

- **Framework:** Spring Boot 3.3.2
- **Language:** Java 17
- **Database:** PostgreSQL 15
- **Security:** Spring Security + JWT
- **ORM:** Spring Data JPA (Hibernate)
- **Build Tool:** Maven
- **Deployment:** Render
- **JWT Library:** jjwt 0.11.5

## 📋 Prerequisites

Before running this project, make sure you have:

- Java 17 or higher
- Maven 3.6 or higher
- PostgreSQL 15 or higher (for production)
- MySQL 8.0 (for local development)

## 🚀 Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/sanjay-1718/expense-tracker-backend.git
cd expense-tracker-backend
```

### 2. Configure Database

#### For Local Development (MySQL):

Create a MySQL database:

```sql
CREATE DATABASE expense_db;
CREATE USER 'expense_user'@'localhost' IDENTIFIED BY 'expense_password';
GRANT ALL PRIVILEGES ON expense_db.* TO 'expense_user'@'localhost';
FLUSH PRIVILEGES;
```

Configure `application-local.yml`:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/expense_db
    username: expense_user
    password: expense_password

jwt:
  secret: your-super-secret-jwt-key-at-least-256-bits-long
  expiration: 86400000
```

#### For Production (PostgreSQL on Render):

Set environment variables in Render:

```
DB_URL=jdbc:postgresql://<host>:<port>/<database>
DB_USERNAME=<username>
DB_PASSWORD=<password>
JWT_SECRET=<your-secret-key>
JWT_EXPIRATION=86400000
```

### 3. Install Dependencies

```bash
mvn clean install
```

### 4. Run the Application

**For local development:**

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=local
```

**For production profile:**

```bash
mvn spring-boot:run
```

The API will start at [http://localhost:8080](http://localhost:8080)


## 🔧 Configuration Files

### application.yml (Production)

```yaml
server:
  port: ${PORT:8080}

spring:
  datasource:
    url: ${DB_URL}
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}
    driver-class-name: org.postgresql.Driver

  jpa:
    hibernate:
      ddl-auto: update
    show-sql: false
    properties:
      hibernate:
        format_sql: false
        dialect: org.hibernate.dialect.PostgreSQLDialect
```

### application-local.yml (Development)

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/expense_db
    username: expense_user
    password: expense_password

jwt:
  secret: local-secret-key
  expiration: 86400000
```

## 📡 API Endpoints

### Authentication

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/api/auth/register` | Register new user | ❌ |
| POST | `/api/auth/login` | User login | ❌ |

### Expenses

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| GET | `/api/expenses` | Get all user expenses | ✅ |
| GET | `/api/expenses/{id}` | Get specific expense | ✅ |
| POST | `/api/expenses` | Create new expense | ✅ |
| PUT | `/api/expenses/{id}` | Update expense | ✅ |
| DELETE | `/api/expenses/{id}` | Delete expense | ✅ |

### Query Parameters for GET /api/expenses

- `category` (optional) - Filter by category
- `startDate` (optional) - Filter by start date (format: YYYY-MM-DD)
- `endDate` (optional) - Filter by end date (format: YYYY-MM-DD)

## 📝 API Documentation

### Register User

```http
POST /api/auth/register
Content-Type: application/json

{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "password123"
}
```

**Response:**
```json
{
  "message": "User registered successfully",
  "email": "john@example.com"
}
```

### Login User

```http
POST /api/auth/login
Content-Type: application/json

{
  "email": "john@example.com",
  "password": "password123"
}
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "email": "john@example.com"
}
```

### Create Expense

```http
POST /api/expenses
Authorization: Bearer <token>
Content-Type: application/json

{
  "amount": 50.00,
  "category": "Food",
  "description": "Lunch at restaurant",
  "date": "2026-01-27"
}
```

**Response:**
```json
{
  "id": 1,
  "amount": 50.00,
  "category": "Food",
  "description": "Lunch at restaurant",
  "date": "2026-01-27",
  "userEmail": "john@example.com"
}
```

### Get All Expenses

```http
GET /api/expenses?category=Food&startDate=2026-01-01&endDate=2026-01-31
Authorization: Bearer <token>
```

**Response:**
```json
[
  {
    "id": 1,
    "amount": 50.00,
    "category": "Food",
    "description": "Lunch at restaurant",
    "date": "2026-01-27",
    "userEmail": "john@example.com"
  }
]
```

## 🔐 Security Features

- **Password Encryption:** BCrypt password hashing
- **JWT Authentication:** Stateless token-based authentication
- **CORS Configuration:** Configured for frontend integration
- **CSRF Protection:** Disabled for REST API (stateless)
- **Protected Endpoints:** All expense endpoints require valid JWT token
- **User Isolation:** Users can only access their own expenses

## 🧪 Testing

### Using Postman

1. **Register a user:**
   - POST `http://localhost:8080/api/auth/register`

2. **Login to get token:**
   - POST `http://localhost:8080/api/auth/login`
   - Copy the token from response

3. **Create expense:**
   - POST `http://localhost:8080/api/expenses`
   - Add header: `Authorization: Bearer <token>`

### Using cURL

```bash
# Register
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"name":"John Doe","email":"john@example.com","password":"password123"}'

# Login
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"john@example.com","password":"password123"}'

# Create Expense (replace <token> with actual token)
curl -X POST http://localhost:8080/api/expenses \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <token>" \
  -d '{"amount":50.00,"category":"Food","description":"Lunch","date":"2026-01-27"}'
```


## 👨‍💻 Author

**Sanjay Bhargav**

- GitHub: [@sanjay-1718](https://github.com/sanjay-1718)
- Backend Repo: [expense-tracker-backend](https://github.com/sanjay-1718/expense-tracker-backend)
- Frontend Repo: [expense-tracker-frontend](https://github.com/sanjay-1718/expense-tracker-frontend)

## 🔗 Related Links

- **Frontend Repository:** [https://github.com/sanjay-1718/expense-tracker-frontend](https://github.com/sanjay-1718/expense-tracker-frontend)
- **Live API:** [https://expense-tracker-backend-mci6.onrender.com](https://expense-tracker-backend-mci6.onrender.com)
- **Frontend App:** [https://expense-tracker-frontend-ten-azure.vercel.app](https://expense-tracker-frontend-ten-azure.vercel.app)

## 💡 Future Enhancements

- [ ] Email verification for new users
- [ ] Password reset functionality
- [ ] Expense analytics endpoints
- [ ] Budget management APIs
- [ ] Notification system
- [ ] API rate limiting

---

⭐ If you found this project helpful, please give it a star!
