# Hangman Game

Desktop Hangman built with Java Swing and Maven. The application supports multiple players, difficulty levels, persistent scoring, and a modernised UI layout.

## Requirements
- Java 21 or newer
- Maven 3.9+ (or another build tool capable of reading `pom.xml`)

## Running the App
```bash
mvn clean package
java -cp target/hangman-game-1.0-SNAPSHOT.jar com.hangman.app.HangmanLauncher
```
If Maven is not installed, open the project in an IDE (IntelliJ IDEA, Eclipse, VS Code with Maven extension) and run the `com.hangman.app.HangmanLauncher` class.

## Features
- Player profiles with persistent statistics (wins, losses, longest streak per difficulty)
- Difficulty selector before each game
- In-game status panel with streak and score summary
- Re-usable leaderboard view for quick stat checks
- Improved word selection to avoid repeats until all options are used

## Project Layout
```
src/main/java/com/hangman/
├── app/            # Entry point and launcher logic
├── controller/     # Game orchestration
├── logic/          # Core game logic
├── model/          # Domain objects
├── persistence/    # JSON-based storage
└── ui/             # Swing components and dialogs
```

## Data Files
- `assets/words.json`: master word list bundled with the project
- `assets/scores.json`: player statistics saved between sessions

Back up the `assets` directory if you want to keep historical scores while experimenting.
