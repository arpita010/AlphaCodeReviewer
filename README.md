# 🧠 AlphaCodeReviewer

> **AI-powered GitHub Pull Request Reviewer** built with **Spring Boot**, **Kafka**, and the **Ollama Mistral model**.

AlphaCodeReviewer automates code reviews by analyzing GitHub Pull Requests (PRs) and posting intelligent review comments directly on the PR.  
It leverages **Apache Kafka** for event-driven processing and integrates with **GitHub APIs** to streamline the review workflow.

---

## 🚀 Features

- 🔄 **Automatic PR Review** – Listens to GitHub webhooks for new or updated PRs and reviews them automatically.
- 🤖 **AI-Powered Analysis** – Uses the **Ollama Mistral** model to provide contextual, high-quality feedback on code changes.
- 📩 **Event-Driven Architecture** – Built on **Apache Kafka** for scalable and reliable message handling.
- 🧩 **Spring Boot Backend** – Handles business logic, GitHub integration, and review orchestration.
- 💬 **Automated Comments & Summaries** – Posts AI-generated comments and summaries directly to the GitHub PR.

---

## 🏗️ Architecture Overview

       ┌──────────────────┐
       │   GitHub Repo    │
       └──────┬───────────┘
              │  (Webhook Event)
              ▼
    ┌───────────────────────┐
    │  AlphaCodeReviewer    │
    │  (Spring Boot App)    │
    ├───────────────────────┤
    │ - Webhook Controller  │
    │ - PR Event Processor  │
    │ - GitHub API Client   │
    └──────┬───────────────┘
           │  Publishes Events
           ▼
      ┌──────────────┐
      │   Kafka Bus  │
      └──────────────┘
           │  Consumed by
           ▼
    ┌──────────────────────┐
    │  AI Review Service   │
    │ (Ollama Mistral)     │
    └──────────────────────┘
           │  Sends Review
           ▼
       ┌────────────┐
       │ GitHub PR  │
       └────────────┘


---

## 🧰 Tech Stack

- **Backend:** Spring Boot
- **Messaging Queue:** Apache Kafka
- **AI Model:** Ollama Mistral
- **Build Tool:** Maven
- **Language:** Java 21

---

## ⚙️ Setup & Installation

### Prerequisites
- Java 17 or higher
- Maven
- Running Kafka instance
- GitHub Personal Access Token (with `repo` and `pull_request` scopes)
- Ollama running locally with the **Mistral** model installed

### Steps

1. **Clone the repository**
   ```bash
   git clone https://github.com/arpita010/AlphaCodeReviewer.git
   cd AlphaCodeReviewer

2. **Set Environment Variables in application.properties file**
   ```properties
   github.token=<your-github-token>
   ```
3. **Run the application**
   ```bash
   mvn spring-boot:run
   ```
   
//TODO: Will update the entire installation setup in a while

## 🧠 How It Works
1. A pull request is opened or updated on GitHub.

2. GitHub sends a webhook event to AlphaCodeReviewer.

3. The event is published to a Kafka topic (e.g., PULL_REQUEST_CREATED).

4. The AI Review Service consumes the message, fetches PR details, and sends the diff to Ollama Mistral.

5. Mistral analyzes the code and generates a review summary and inline comments.

6. The comments are posted back to the GitHub PR using the GitHub API.

## 📸 Screenshots/Work Recording

// TODO: Will attach soon...