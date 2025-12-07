# 🚖 RideShare Backend API

A robust RESTful backend service for a ride-sharing application (similar to Uber/Ola). This project manages user authentication, ride booking, driver acceptance, and ride status tracking using **Spring Boot**, **MongoDB**, and **JWT Security**.

## 🚀 Tech Stack

* **Framework:** Spring Boot 3.x
* **Language:** Java 17+
* **Database:** MongoDB (Atlas Cloud or Local)
* **Security:** Spring Security + JWT (JSON Web Tokens)
* **Build Tool:** Maven

## ✨ Features

* **User Authentication:**
    * Register as `ROLE_USER` (Passenger) or `ROLE_DRIVER`.
    * Secure Login returning a JWT Bearer token.
* **Passenger Features:**
    * Request a new ride (Pickup/Drop location).
    * View own ride history.
* **Driver Features:**
    * View all pending ride requests (`REQUESTED` status).
    * Accept a ride (updates status to `ACCEPTED`).
* **Ride Management:**
    * Complete a ride (updates status to `COMPLETED`).
    * Status lifecycle: `REQUESTED` -> `ACCEPTED` -> `COMPLETED`.
* **Security:**
    * Role-based access control (RBAC) protecting endpoints.
    * BCrypt password encoding.
 
## 📂 Project Structure

```text
src/main/java/org/example/rideshare
├── config/          # Security & JWT Config
├── controller/      # API Controllers (Auth, Ride, Driver)
├── dto/             # Data Transfer Objects
├── exception/       # Global Exception Handling
├── model/           # Database Entities (User, Ride)
├── repository/      # MongoDB Repositories
├── service/         # Business Logic
└── util/            # Helper classes (JwtUtil)
```


## 🔌 API Endpoints

### 1️⃣ Authentication (Public)

| Method | Endpoint | Description | Body Required |
| :--- | :--- | :--- | :--- |
| `POST` | `/api/auth/register` | Register new user | `{"username": "john", "password": "123", "role": "ROLE_USER"}` |
| `POST` | `/api/auth/login` | Login & get Token | `{"username": "john", "password": "123"}` |

### 2️⃣ Passenger Operations (Requires `ROLE_USER`)

| Method | Endpoint | Description | Body Required |
| :--- | :--- | :--- | :--- |
| `POST` | `/api/v1/rides` | Request a ride | `{"pickupLocation": "Home", "dropLocation": "Office"}` |
| `GET` | `/api/v1/user/rides` | View my history | *None* |

### 3️⃣ Driver Operations (Requires `ROLE_DRIVER`)

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `GET` | `/api/v1/driver/rides/requests` | See all pending rides |
| `POST` | `/api/v1/driver/rides/{id}/accept` | Accept a specific ride |

### 4️⃣ Shared Operations (Authenticated)

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `POST` | `/api/v1/rides/{id}/complete` | Mark ride as COMPLETED |

