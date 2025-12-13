# Backend Spring Security

Enterprise-Grade Security Backend with Stateful JWT Authentication

[![Live Demo](https://img.shields.io/badge/Live-http%3A%2F%2F51.44.171.166%2F-brightgreen)](http://51.44.171.166/)
[![Docker Hub](https://img.shields.io/badge/Docker%20Hub-paccy%2Fbackend--spring--security-blue)](https://hub.docker.com/r/paccy/backend-spring-security)

## 🛠️ Tech Stack

- **Language:** Java 17
- **Framework:** Spring Boot
- **Build Tool:** Gradle
- **Database:** PostgreSQL
- **CI/CD:** Docker + GitHub Actions

## 🔐 Security Architecture

### Stateful JWT Authentication

This project implements stateful JWT authentication, providing better security and control compared to traditional stateless JWT:

- JWT generated on login
- Token stored in database
- Validated against DB per request
- Revocable sessions
- Enterprise audit capability

**Benefits over Traditional Stateless JWT:**
- Better Control + Security
- Complete session management
- Instant token revocation
- Protection against compromised tokens

### Advanced Security Features

**🛡️ Custom Authentication Filter**
- JWT extraction & validation
- User session loading
- Sets authentication in security context

**🔒 Method-Level Authorization**
- @PreAuthorize / @PostAuthorize
- Fine-grained access control
- @PostFilter for collection filtering

**⚙️ Custom PermissionEvaluator**
- Complex permission logic
- Resource-level authorization

## 🚀 CI/CD Pipeline Flow

```
GitHub → GitHub Actions → Docker Build → Docker Hub → Production
(Source)   (Build & Test)   (Containerize)  (Registry)   (EC2: 51.44.171.166)
```

### Deployment Steps

1. **GitHub** - Source code repository
2. **GitHub Actions** - Automated build and testing (`gradle build`)
3. **Docker Image** - Multi-stage containerization
4. **Docker Hub** - Public registry (`paccy/backend-spring-security`)
5. **Production** - EC2 instance deployment via `docker-compose up`

## 🗄️ Runtime Architecture

```
Spring Boot App → PostgreSQL → JWT Tokens + Users → Every Request Validation
```

The application validates JWT tokens against the database on every request, ensuring complete session control and security.

## 📋 Project Overview

Secure backend application demonstrating enterprise-grade security implementation with stateful JWT authentication, custom permission logic, method-level authorization, and automated CI/CD deployment via Docker.

## 🔐 Security Implementation

### 1. Stateful JWT Authentication

- JWT tokens generated on login
- Stored in database (not just client-side)
- Validated against DB per request
- Tokens can be revoked (logout, security)
- Full audit trail of active sessions

### 2. Custom Authentication Filter

- Extracts JWT from request headers
- Validates token and loads user session
- Sets Authentication in security context

### 3. Method-Level Authorization

- @PreAuthorize - Check before execution
- @PostAuthorize - Validate after execution
- @PostFilter - Filter returned collections

## ✨ Key Benefits

### Security Advantages

✓ Complete session control  
✓ Instant token revocation capability  
✓ Protection against compromised tokens  
✓ Enterprise-grade audit trails

### Development Advantages

✓ Automated CI/CD pipeline  
✓ Multi-stage Docker optimization  
✓ Production-ready deployment  
✓ Integrated API documentation

### vs Traditional Stateless JWT

✓ Better security: Revocable sessions  
✓ Better control: Active session management  
✓ Better audit: Complete activity tracking

## 📚 API Documentation

Full API documentation is available via Swagger UI at the live deployment: [http://51.44.171.166/swagger-ui.html](http://51.44.171.166/swagger-ui.html)

## 🐳 Docker Hub

Pull the image directly from Docker Hub:

```bash
docker pull paccy/backend-spring-security
```

## 🌐 Live Demo

Access the live application at: [http://51.44.171.166/](http://51.44.171.166/)