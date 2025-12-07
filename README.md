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

