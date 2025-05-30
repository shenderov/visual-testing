# Visual Testing Server – Experimental Playground

**Visual Testing** is an experimental Java-based server project designed to enable robust, automated visual regression testing.  
It stores screenshots, test metadata, and build data, allowing users to compare “base” and “current” screenshots and detect UI changes.

> **Project Vision:**  
> Build a flexible, language-agnostic visual testing platform:  
> - This Java Spring Boot server acts as a backend API and image-comparison engine.  
> - Client connectors in different languages (JavaScript, Java, Python, etc.) will interact with the API, enabling teams to integrate visual testing into any workflow.

---

## 🚀 Features

- **Store & manage screenshots** for multiple builds and tests
- **Compare images** and highlight visual differences using [image-comparison](https://github.com/romankh3/image-comparison)
- **RESTful API** for uploading, retrieving, and comparing screenshots
- **Track test and build data** for regression analysis
- **Pluggable connectors:** (Planned) multi-language SDKs to talk to the server
- **Built-in H2 database** for local/dev use; supports MySQL for production

---

## 🛠️ Tech Stack

- **Backend:** Java 8+, Spring Boot
- **Database:** H2 (development/testing), MySQL (production/scale)
- **Image Comparison:** [com.github.romankh3:image-comparison](https://github.com/romankh3/image-comparison)
- **App Server:** Apache Tomcat (embedded with Spring Boot)
- **API:** REST/JSON for integration with external clients

---

## 📁 Project Structure

<pre>
visual-testing/
├── src/
│   ├── main/
│   │   ├── java/               # Spring Boot application and controllers
│   │   └── resources/          # Application configs, SQL, static files
│   └── test/                   # Unit and integration tests
├── README.md                   # Project documentation
├── pom.xml                     # Maven build configuration
└── ...
</pre>

- **src/main/java/** – Backend logic, REST API, image comparison
- **src/main/resources/** – Spring Boot configs, SQL migrations
- **src/test/** – Automated tests
- **pom.xml** – Maven project descriptor
- **README.md** – Project documentation

---

## ⚡ Getting Started

### Prerequisites

- Java 8 or higher
- Maven
- (Optional) MySQL, if not using H2 for dev

### Setup

1. **Clone the repository:**
    ```bash
    git clone https://github.com/shenderov/visual-testing.git
    cd visual-testing
    ```

2. **Configure the database:**  
    - By default, uses embedded H2 (no setup needed for dev).
    - For MySQL, set DB connection in `application.properties`:
      ```
      spring.datasource.url=jdbc:mysql://localhost:3306/visualtesting
      spring.datasource.username=your_mysql_user
      spring.datasource.password=your_mysql_password
      ```

3. **Build and run:**
    ```bash
    mvn clean install
    mvn spring-boot:run
    ```
    By default, the API server will run on [http://localhost:8080](http://localhost:8080).

---

## 🌐 API & Connectors

- The server exposes a REST API for screenshot upload, test data storage, and image comparison.
- (Planned) Client libraries/connectors for JS, Java, Python, etc. to enable easy integration into any test suite or CI pipeline.

---

## 💡 Example Use Cases

- Automate UI screenshot testing from any language or tool
- Store visual baselines for components or pages
- Compare new screenshots to baselines and highlight changes
- Track builds and test results historically

---

## ⚠️ Disclaimer

This is an experimental project developed for learning and demonstration purposes.  
Functionality, APIs, and structure may change as development continues.  
Comes **without any warranties** – use at your own risk.

---

## 🤝 Contributing

Ideas, issues, and pull requests are always welcome!  
Feel free to open an issue or contribute via PR.

---

## 📝 License

This project is licensed under the [MIT License](LICENSE).
