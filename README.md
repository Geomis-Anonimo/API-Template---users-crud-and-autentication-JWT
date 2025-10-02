# User CRUD & JWT Authentication API

A Spring Boot template with complete user management and JWT authentication.

## ✨ Features

- **🔐 JWT Authentication** - Secure token-based auth
- **👥 User CRUD** - Full user management
- **📧 Flexible Login** - Use email OR username
- **🐘 PostgreSQL Ready** - Production database setup
- **🚀 Flyway Migrations** - Database version control

## 🔑 Authentication

Login with either email or username:

```http
POST /api/login
{
  "emailOrUsername": "user@email.com",  # or username
  "password": "your_password"
}
```

## 🔑 CRUD Users

These data are mandatories

```http
POST /api/users
{
	"name": "Jhon Doe",
	"username": "jd",
	"email": "jhondoe123@gmail.com",
	"password": "secret",
	"cpf": "123.456.789-10"
}
```