# AI Service - demo chatbot

## 🎯 Project Goals

- **Conversation memory** – the chatbot remembers context to keep dialogue coherent.
- **Knowledge base** – internal documents can be added, which the chatbot can reference.
- **Employee data access** – the chatbot can:
    - check remaining vacation days,
    - help submit a leave request,
    - provide information stored in the system.

## 🚀 How to Run

1. Register your application: https://platform.openai.com/docs/overview and **generate API key**
2. Set **open-ai.api-key** value in application.yml
3. Run docker-compose to set up DB connection: **docker compose up**
4. Run application: **mvn spring-boot:run**
5. App is running on port 8091: http://localhost:8091