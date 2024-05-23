# MovieEnthusiasChallenge
This project is a web application built for the Xpand IT interview process. It features a dynamic movie table that supports infinite scroll and filtering options.

## Tech Stack
Frontend: [React](https://react.dev/)

Backend: [Spring](https://spring.io/)

DataSource: [TMDB API](https://developer.themoviedb.org/reference/intro/getting-started)

## Setup

Follow these steps to set up the project:

### 1. Clone the Repository

```sh
git clone https://github.com/rfcfe/MovieEnthusiastAppChallenge.git
cd MovieEnthusiastAppChallenge
```
### 2. Setup the Backend

Ensure you have the necessary tools for running a [Spring Boot](https://docs.spring.io/spring-boot/installing.html) application (Java, Maven, etc.).

#### 2.1. Environment Variables

You need to set up a `.env` file in the root of the `backend` directory with your API key for accessing The Movie Database (TMDB) API.

2.1.1. **Create a `.env` file in the `backend` directory:**

2.1.2. **Add your TMDB API key to the `.env` file:**

`API_KEY=your_api_key_here`

Replace `your_api_key_here` with your actual API key from TMDB. You can get your API key [here](https://developer.themoviedb.org/reference/intro/getting-started).


#### 2.2. **Navigate to the backend directory:**

```sh
cd backend
```

#### 2.3. **Run Maven to start the backend:**

```sh
mvn spring-boot:run
```

### 3. Setup the Frontend

#### 3.1. **Navigate to the frontend directory:**

```sh
cd frontend
```

#### 3.2. **Install dependencies:**

```sh
npm install
```

#### 3.3. **Start the frontend application:**

```sh
npm start
```

## Usage

Once both the backend and frontend are running, open your browser and navigate to `http://localhost:3000` to start browsing movies.
