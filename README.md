# PlanGo

AI-powered travel planning application built with a native Android client and a Spring Boot backend.

---

## About This Repository

This repository is the **top-level hub** for the PlanGo project.

The actual source code for the application is managed in the following repositories:

- **Server**: [plango-server](https://github.com/hyunseop622/plango-server)
- **Android App**: [plango-app](https://github.com/chldnjswo/plango-app)

Use this repository as an entry point to understand what PlanGo is, how it is structured, and who is involved in the project.

---

## Project Overview

PlanGo is an AI-assisted travel planner.  
Users can select destinations, dates, and preferences, and PlanGo helps them generate and manage itineraries directly on an Android app.

The project is built with:

- A native Android app for the user interface and interaction
- A Spring Boot backend for business logic, AI orchestration, and data management
- Cloud infrastructure on AWS for deployment and security

---

## Architecture Overview

At a high level, PlanGo is composed of:

- **Android App (plango-app)**
    - Native Android client built in Kotlin
    - Handles UI/UX and user interactions
    - Communicates with the backend via REST APIs


- **Backend Server (plango-server)**
    - Spring Boot application
    - Provides REST APIs for authentication, trip management, and itinerary generation
    - Integrates with AI services to generate or refine travel plans
    - Manages database access and DTO/API contracts


- **Database**
    - Stores users, trips, itineraries, and related travel data


- **Cloud & Security**
    - Deployed on AWS
    - Includes security configuration, access control, and environment management

---

## Team

### [Hyunseop Kim](https://github.com/hyunseop622) – Backend / Architecture
- Backend development
- UI/UX participation
- Documentation and overall project organization
- AWS deployment and security configuration
- AI service configuration and integration
- Database management
- DTO contracts and API contracts
- Backend–App communication testing

### [Wonjae Choi](https://github.com/chldnjswo) – Main Frontend
- Majority of frontend (Android) development
- UI/UX design and implementation
- Communication testing with backend
- API configuration (e.g., Google Maps integration)
- Documentation

### [Gunwoo Kim](https://github.com/kgw2611) – Frontend
- Frontend development
- Documentation
- UI/UX participation

### [Hanseong Kim](https://github.com/rlagkstjd03) – Frontend
- Frontend development
- UI/UX participation
- Documentation

### [Onejun Jang](https://github.com/chldnjswo) – Frontend
- Frontend development
- Documentation
- UI/UX participation  
