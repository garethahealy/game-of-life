[![Build, Analyze and Test](https://github.com/garethahealy/game-of-life/actions/workflows/build.yaml/badge.svg)](https://github.com/garethahealy/game-of-life/actions/workflows/build.yaml)

# game-of-life

Conway's Game of Life implemented as a Quarkus REST API with a React + Vite UI.
The backend generates each generation of the board, while the UI polls the API
to render the grid and apply different seed patterns.

More on the rules: http://en.wikipedia.org/wiki/Conway's_Game_of_Life

## Build

Backend (Java 21 / Quarkus):

```bash
./mvnw clean install -Pnative
```

Frontend (Vite):

```bash
cd app
npm install
npm run build
```

## Run (development)

Backend API on `http://localhost:9090`:

```bash
./mvnw quarkus:dev
```

Frontend UI on `http://localhost:5173` (points to the backend):

```bash
cd app
VITE_BACKEND_BASE_URL=http://localhost:9090 npm run dev
```
