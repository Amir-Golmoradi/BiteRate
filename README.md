# BiteRate

**BiteRate** is a next-generation, enterprise-grade restaurant review and photo management platform, engineered for the European market. Built with Java 21 and Spring Boot, BiteRate is designed for scalability, security, and seamless integration with modern cloud-native ecosystems.

---

## 🚀 Key Features

- **Photo Management**: Upload, retrieve, and manage restaurant photos using MinIO (S3-compatible object storage).
- **Restaurant Reviews**: Comprehensive domain for restaurants, users, reviews, and cuisine types.
- **Advanced Search**: Elasticsearch integration for high-performance, full-text search.
- **Security & Identity**: OAuth2 and Spring Security, with Keycloak integration for robust identity and access management.
- **API-First**: OpenAPI (Swagger) documentation for frictionless integration and developer onboarding.
- **Observability**: Kibana-ready for advanced log analytics and monitoring.
- **Cloud-Ready**: Dockerized for effortless deployment and scalability.
- **Future-Proof**: Roadmap includes migration to Hexagonal (Ports & Adapters) Architecture for maximum maintainability and testability.

---

## 🏗️ Technology Stack

- **Java 21**
- **Spring Boot 3.5**
- **Elasticsearch**
- **MinIO**
- **Keycloak** (IAM)
- **MapStruct**
- **Lombok**
- **OpenAPI/Swagger**
- **Kibana** (Observability)
- **Docker**

---

## 📦 Getting Started

### Prerequisites
- Java 21+
- Maven 3.8+
- Docker (for MinIO, Elasticsearch, Keycloak, Kibana)

### Quick Start

1. **Clone the repository:**
   ```sh
   git clone <your-repo-url>
   cd BiteRate
   ```
2. **Start dependencies:**
   ```sh
   docker compose up -d
   ```
3. **Run the application:**
   ```sh
   ./mvnw spring-boot:run
   ```
4. **Access API documentation:**
   - [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
5. **Access Keycloak:**
   - [http://localhost:8081/](http://localhost:8081/) *(default port, adjust as needed)*
6. **Access Kibana:**
   - [http://localhost:5601/](http://localhost:5601/)

---

## 🛡️ Security & Compliance
- GDPR-compliant architecture
- OAuth2 resource server with Keycloak
- Fine-grained access control

---

## 🧩 Project Structure

- `src/main/java/dev/amirgol/biterate/`  
  Core application code
  - `controller/` – REST API endpoints
  - `service/` – Business logic & integrations
  - `repository/` – Data access (Elasticsearch)
  - `domain/` – Entities, DTOs, enums
  - `config/` – Security, storage, OpenAPI, Keycloak configs
  - `exception/` – Global error handling
- `src/main/resources/` – Configuration & static assets

---

## 🌍 Why BiteRate?
- **European Data Sovereignty**: Self-host or deploy in your preferred EU cloud.
- **API-Driven**: Built for integration with mobile, web, and partner platforms.
- **Enterprise Observability**: Kibana integration for actionable insights.
- **Modern Dev Experience**: Clean code, strong typing, and full documentation.
- **Future-Proof**: Planned migration to Hexagonal Architecture for ultimate flexibility and maintainability.

---

## 🤝 Contributing
We welcome contributions from the European tech community! Please open issues or submit pull requests.

---

## 📄 License
This project is licensed under the MIT License.

---

## 🏢 Crafted for European Enterprises
BiteRate is engineered with European business needs in mind: privacy, scalability, observability, and seamless integration. Elevate your food-tech solutions with BiteRate.
