# HMCTS Task Manager Backend

## Overview

This project is the backend for a task management application developed as part of the HMCTS Developer Technical Test.

It provides REST API endpoints to create, view, update, and delete tasks. Tasks are stored in a PostgreSQL database.

## Features

- Create a task with a title, description, status, and due date/time.
- View all tasks.
- View a task by its ID.
- Update task details.
- Update a task's status.
- Delete a task.
- Validate request data and handle errors.
- Test API endpoints using Swagger UI.

## Technology Stack

- Java 21
- Spring Boot
- Spring Web
- Spring Data JPA
- PostgreSQL
- Jakarta Validation
- Gradle
- Lombok
- Springdoc OpenAPI / Swagger UI

## Task Structure

- `id`: Automatically generated task ID
- `title`: Required, maximum 200 characters
- `description`: Optional, maximum 5,000 characters
- `status`: `TODO`, `IN_PROGRESS`, or `COMPLETED`
- `dueDateTime`: Required due date and time

## API Endpoints

Base URL: `http://localhost:4000/api/tasks`

- `POST /api/tasks` — Create a task
- `GET /api/tasks` — Retrieve all tasks
- `GET /api/tasks/{id}` — Retrieve a task by ID
- `PUT /api/tasks/{id}` — Update task details
- `PATCH /api/tasks/{id}/status` — Update task status
- `DELETE /api/tasks/{id}` — Delete a task

### 1. Create a Task

`POST /api/tasks`

Request body:

```json
{
  "title": "Complete technical test",
  "description": "Finish the task manager implementation",
  "status": "TODO",
  "dueDateTime": "2026-11-05T14:30:00"
}
```

Returns `201 Created` with the created task and its location.

### 2. Retrieve All Tasks

`GET /api/tasks`

Returns `200 OK` with the list of tasks.

### 3. Retrieve a Task by ID

`GET /api/tasks/1`

Returns `200 OK` if the task exists, or `404 Not Found` if it does not.

### 4. Update a Task

`PUT /api/tasks/1`

Request body:

```json
{
  "title": "Complete technical test",
  "description": "Review and test the application",
  "status": "IN_PROGRESS",
  "dueDateTime": "2026-11-06T16:00:00"
}
```

Updates the task details and returns `200 OK`.

### 5. Update Task Status

`PATCH /api/tasks/1/status`

Request body:

```json
{
  "status": "COMPLETED"
}
```

Updates the task status and returns `200 OK`.

### 6. Delete a Task

`DELETE /api/tasks/1`

Deletes the task and returns `204 No Content` when successful.

## API Documentation and Testing

Swagger UI provides an interactive way to view and test the API endpoints.

Start the backend and open:

[http://localhost:4000/swagger-ui/index.html](http://localhost:4000/swagger-ui/index.html)

To test an endpoint:

1. Expand the endpoint you want to test.
2. Select `Try it out`.
3. Enter the required parameters or request body.
4. Select `Execute`.
5. Review the response status code and response body.

### Suggested Test Workflow

1. Create a task using `POST`.
2. Retrieve all tasks using `GET`.
3. Retrieve the created task using its ID.
4. Update the task using `PUT`.
5. Change its status using `PATCH`.
6. Delete the task using `DELETE`.
7. Try retrieving the deleted task to check the `404 Not Found` response.

You can also test validation by submitting a blank title, an overly long title, or a request with missing required fields.

## Validation and Error Handling

The API validates incoming requests and returns appropriate error responses.

- `400 Bad Request` – Invalid request data or a supported request parsing error.
- `404 Not Found` – The requested task does not exist.
- `201 Created` – Task created successfully.
- `200 OK` – Request completed successfully.
- `204 No Content` – Task deleted successfully.

Validation includes a required, non-blank title, a maximum title length of 200 characters, a maximum description length of 5,000 characters, and required status and due date/time fields.

## Prerequisites

Before running the application, make sure you have:

- Java 21 installed.
- PostgreSQL installed and running.
- Git installed.
- A terminal or an IDE such as Eclipse.

## Environment Configuration

The application uses environment variables to connect to PostgreSQL.

Create a `.env` file in the backend project root with the following settings:

```dotenv
SERVER_PORT=4000
DB_HOST=localhost
DB_PORT=5432
DB_NAME=hmcts_task_manager_db
DB_USER_NAME=your_database_username
DB_PASSWORD=your_database_password
```

Replace the example username and password with your local PostgreSQL credentials.


Make sure PostgreSQL is running and the specified database and user exist before starting the application.

Load the variables into your terminal before running the application:

```bash
set -a
source .env
set +a
```

These commands make the variables available to the application process. Run them again when using a new terminal session.

The application also has an optional Spring configuration import from `/mnt/secrets/test/`, which can be used when configuration values are supplied through that mounted directory.

## Running the Application

Clone the backend repository:

```bash
git clone https://github.com/anukjose/hmcts-task-manager-backend.git
cd hmcts-task-manager-backend
```

Configure the environment variables as described above, then use the following commands.

### Build the Project

```bash
./gradlew build
```

### Run the Tests

```bash
./gradlew test
```

### Run Checkstyle

```bash
./gradlew checkstyleMain
```

### Clean and Build

```bash
./gradlew clean build
```

### Start the Application

```bash
./gradlew bootRun
```

Once the application starts, the API is available at:

`http://localhost:4000/api/tasks`

Swagger UI is available at:

`http://localhost:4000/swagger-ui/index.html`

## Project Structure

```text
src/
├── main/
│   ├── java/
│   │   └── uk/gov/hmcts/reform/dev/
│   │       ├── controllers/
│   │       ├── dto/
│   │       ├── exceptions/
│   │       ├── models/
│   │       ├── repositories/
│   │       └── services/
│   └── resources/
│       └── application.yaml
└── test/
    └── java/
```

The main components are:

- Controllers – Handle HTTP requests and responses.
- DTOs – Define the data accepted by the API.
- Models – Define the task entity and task status.
- Repositories – Interact with the database through Spring Data JPA.
- Services – Contain the task management logic.
- Exceptions – Handle errors such as tasks that cannot be found.
- Tests – Verify application behaviour.

## Frontend

The frontend is maintained in a separate repository:

[HMCTS Task Manager Frontend](https://github.com/anukjose/hmcts-task-manager-frontend)

It communicates with the backend through the REST API. The frontend and backend must both be running for the complete application to work.

## Notes

- The application uses PostgreSQL for persistent storage.
- Task IDs are generated by the database.
- The due date and time use the ISO date-time format, for example `2026-11-05T14:30:00`.
