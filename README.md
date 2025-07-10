# Log Analyzer API - Spring Boot

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![Java](https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=java&logoColor=white)
![HTML5](https://img.shields.io/badge/Frontend-HTML5-E34F26?style=for-the-badge&logo=html5&logoColor=white)
![REST API](https://img.shields.io/badge/API-RESTful-blue?style=for-the-badge&logo=swagger)
![CSS3](https://img.shields.io/badge/Frontend-CSS3-1572B6?style=for-the-badge&logo=css3&logoColor=white)
![JavaScript](https://img.shields.io/badge/Frontend-JavaScript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black)




A RESTful API built with **Spring Boot** to analyze system log files (`kpsaOrder.log`) and extract structured information related to **Product Orders (PO)**, **Work Orders (WO)**, **Service Orders (SO)**, and associated **Request IDs**. It also detects rollback events and provides a full overview of the request lifecycle.

---

## ✨ Features

- Analyze logs by a specific `Request ID`.
- Extract:
  - **Calling Account** and **Operation Type**
  - Details of **Service Orders (SO)** with IDs, names, statuses (STARTED/ENDED), and execution time.
  - Details of **Product Orders (PO)** and **Work Orders (WO)**, including their instances and statuses.
  - **Rollback detection** in the request lifecycle.
- REST API returns a structured JSON response.
- Console logs provide additional trace information for debugging.
- CORS configuration included for frontend communication.

---

## 🛠️ Technologies Used

- **Java 17+**
- **Spring Boot 3.x**
- **Maven**
- **Regex (Regular Expressions)** for parsing raw logs

---

## 🚀 Getting Started

### Prerequisites

- Java JDK 17 or later  
- Apache Maven  
- IDE (e.g., IntelliJ IDEA)

### Clone the repository

```bash
git clone https://github.com/Saadix-1/log-analyzer-api.git
cd log-analyzer-api
````

### Prepare log file

Ensure the `kpsaOrder.log` file is placed in:

```
src/main/resources/kpsaOrder.log
```

### Run the application

**Using Maven:**

```bash
mvn spring-boot:run
```

**Using IntelliJ IDEA:**
Run the `LogAnalyzerApiApplication.java` class.

The API will be available at [http://localhost:8080](http://localhost:8080)

---

## 🌐 API Usage

### Endpoint

```
GET /api/log-analyzer/{requestId}
```

Replace `{requestId}` with a valid ID from the log file.

### Example

```
GET http://localhost:8080/api/log-analyzer/1808027630
```

### Example JSON Response

```json
{
  "requestId": "1808027630",
  "accountCaller": "YourAccount",
  "operationType": "YourOperation",
  "rollbackDetected": false,
  "serviceOrders": [
    {
      "id": "1808027635",
      "name": "GSM:ContractDeactivation",
      "status": "Ended",
      "executionTimeMillis": 1234
    }
  ],
  "productOrders": [ /* ... */ ],
  "workOrders": { /* ... */ }
}
```

---

## 📂 Project Structure

```
src/main/java/com/example/loganalyzer/
├── LogAnalyzerApiApplication.java    # Main class
├── controller/
│   └── LogAnalyzerController.java    # REST controller
├── service/
│   └── LogAnalysisService.java       # Business logic
├── model/
│   ├── LogAnalysisResult.java
│   ├── ServiceOrder.java
│   ├── ProductOrder.java
│   └── WorkOrder.java
└── config/
    └── WebConfig.java                # CORS configuration
src/main/resources/
└── kpsaOrder.log                     # Sample log file
```

---
---

## 📸 Example Response 
<img width="1503" alt="Screenshot 2025-07-10 at 09 37 06" src="https://github.com/user-attachments/assets/9e6130b8-03b8-4d7f-a976-b3943b89f041" />
<img width="1503" alt="Screenshot 2025-07-10 at 09 37 38" src="https://github.com/user-attachments/assets/c7a944a5-dad4-49c1-871e-af4be9b99a14" />

---

## 🌍 CORS Configuration

The `WebConfig.java` class allows frontend apps (e.g. running on `http://localhost:3000`) to interact with the backend.

Update `allowedOrigins` in `WebConfig` based on your frontend environment if needed.

---

## 🤝 Contributions

Contributions are welcome!
Feel free to open issues or submit pull requests with enhancements or bug fixes.

---

## 📄 License

This project is licensed under the [MIT License](https://opensource.org/licenses/MIT).
![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg?style=for-the-badge)


```

---






