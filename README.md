# Log Analyzer API - Spring Boot

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![Java](https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=java&logoColor=white)
![HTML5](https://img.shields.io/badge/Frontend-HTML5-E34F26?style=for-the-badge&logo=html5&logoColor=white)
![REST API](https://img.shields.io/badge/API-RESTful-blue?style=for-the-badge&logo=swagger)
![CSS3](https://img.shields.io/badge/Frontend-CSS3-1572B6?style=for-the-badge&logo=css3&logoColor=white)
![JavaScript](https://img.shields.io/badge/Frontend-JavaScript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black)


A RESTful API built with **Spring Boot** to analyze system log files (`kpsaOrder.log`) and extract structured information related to **Product Orders (PO)**, **Work Orders (WO)**, **Service Orders (SO)**, and associated **Request IDs**. It also detects rollback events and provides a full overview of the request lifecycle.


(As part of my internship at Atos)
<img width="130" height="125" alt="image" src="https://github.com/user-attachments/assets/f4046b47-09db-4650-b2b5-da9a94f34968" />


---

## ✨ Features

- Analyze logs by a specific `Request ID`.
- Extract:
- **Calling Account** and **Operation Type**
- Details of **Service Orders (SO)** with IDs, names, statuses (STARTED/ENDED), and execution time.
- Details of **Product Orders (PO)** and **Work Orders (WO)**, including their instances and statuses.
- **Rollback detection** in the request lifecycle.
- 🌐 **RESTful API** + simple **web interface** for easy testing
- Console logs provide additional trace information for debugging.
- CORS configuration included for frontend communication.

---

## 🛠️ Technologies Used

- **Java 17+**
- **Spring Boot 3.x**
- **Maven**
- **Regex (Regular Expressions)** for parsing raw logs
- **HTML + JavaScript for frontend**
- **CORS enabled for frontend/backend integration**

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

## 🧪 REST API Endpoints

| Method | Endpoint                               | Description                                |
| ------ | -------------------------------------- | ------------------------------------------ |
| `POST` | `/api/log-analyzer/analyze-default`    | Analyze default log file (`kpsaOrder.log`) |
| `POST` | `/api/log-analyzer/upload-and-analyze` | Upload and analyze a custom log file       |
| `GET`  | `/api/log-analyzer/test` *(optional)*  | Simple test endpoint to verify API         |

---

### Example

```
GET http://localhost:8080/api/log-analyzer/1808027630
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

## 🌐 Web Interface (`index.html`)

A minimal web interface is included in `src/main/resources/static/index.html`.
It allows users to:

1. Enter a **Request ID**
2. Analyze the **default log file**
3. Upload and analyze a **custom log file**

---
## 🌍 CORS Configuration

The `WebConfig.java` class allows frontend apps (e.g. running on `http://localhost:3000`) to interact with the backend.

Update `allowedOrigins` in `WebConfig` based on your frontend environment if needed.

---
## 📸 Example Response 
<img width="1506" height="846" alt="Screenshot 2025-07-16 at 15 13 48" src="https://github.com/user-attachments/assets/fc25b3db-6cf9-4089-9cb2-a0a835490163" />
<img width="1506" height="846" alt="Screenshot 2025-07-16 at 15 13 57" src="https://github.com/user-attachments/assets/a36fc845-711c-428c-b5e3-077300e4d517" />
<img width="1506" height="846" alt="Screenshot 2025-07-16 at 15 14 01" src="https://github.com/user-attachments/assets/165aad49-521f-4c59-bc74-e40b2c1b8a61" />

---

## 🤝 Contributions

Contributions are welcome!
Feel free to open issues or submit pull requests with enhancements or bug fixes.

---

## 📄 License

This project is licensed under the 
[MIT License](https://opensource.org/licenses/MIT).

![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg?style=for-the-badge)


```







