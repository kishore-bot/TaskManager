# Task Manager App

This is a Full Stack Task Manager application that allows users to manage their tasks efficiently. The app uses Jetpack Compose for the frontend and Node.js with JWT authentication for the backend.

## Features

- **User Authentication**: Users can sign up and log in to the app securely using JWT authentication.
- **Task Management**: Create, delete, and update tasks based on priority and time.
- **Task Progress**: Track the remaining end time of each task and view a progress bar indicating the completion status.
- **Subtasks**: Users can add subtasks to each task for better organization.
- **Upcoming Subtasks**: The app displays upcoming subtasks for each task.
- **Rewards**: Users receive stars upon completing tasks, which are showcased on the home screen.

## Tech Stack

### Backend

- Node.js
- Express.js
- MongoDB with Mongoose for data storage
- JSON Web Token (JWT) for user authentication
- bcryptjs for password hashing

### Frontend

- Jetpack Compose for UI
- DataStore for storing user tokens
- Retrofit for network requests
- MVVM architecture for clean separation of concerns
- Compose Navigation for app navigation

## Setup Instructions

### Backend

1. Navigate to the `backend` directory.
2. Install dependencies using `npm install`.
3. Set up the MongoDB connection by configuring the environment variables.
4. Start the server using `npm start` or `nodemon`.

### Frontend

1. Open the `frontend` directory in Android Studio or your preferred IDE.
2. Set up the required Android SDK and dependencies.
3. Run the app on an emulator or physical device.

## How to Use

1. Sign up or log in using your credentials.
2. Create tasks with priority and time details.
3. Manage tasks by marking them as completed or deleting them.
4. Add and manage subtasks within each task.
5. Track task completion progress on the home screen.

## Images

### Backend

## Frontend Screenshots

### Login Screen

![Login Screen](https://i.postimg.cc/C5zBbjcq/Log.png)

Description or additional information about the Login Screen...

### Sign In Screen

![Sign In Screen](https://i.postimg.cc/8c9F4jYn/Sign.png)

Description or additional information about the Sign In Screen...

### Home Screen

![Home Screen](https://i.postimg.cc/nLJDN9hy/Home.png)

Description or additional information about the Home Screen...

### Add Task Screen

![Add Task Screen](https://i.postimg.cc/kM1WD5TS/Add.png)

Description or additional information about the Add Task Screen...

### Details Screen

![Details Screen](https://i.postimg.cc/j58nW84T/Details.png)

Description or additional information about the Details Screen...

### Stars Screen

![Stars Screen](https://i.postimg.cc/KcHgxHYr/Stars.png)

Description or additional information about the Stars Screen...

## Backend Architecture Screenshots

### Backend Image 1

![Backend Image 1](https://i.postimg.cc/L6JLPMWb/B1.png)

Description or additional information about Backend Image 1...

### Backend Image 2

![Backend Image 2](https://i.postimg.cc/DyRsdMky/B2.png)

Description or additional information about Backend Image 2...

### Backend Image 3

![Backend Image 3](https://i.postimg.cc/yNyZK6Jk/B3.png)

Description or additional information about Backend Image 3...

### Backend Image 4

![Backend Image 4](https://i.postimg.cc/26jBSpvT/B4.png)

Description or additional information about Backend Image 4...
