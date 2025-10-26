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
- On-screen keyboard mirrors physical keyboard input with visual feedback

## Roadmap
- [x] Upgrade gameplay loop with persistent score tracking per user
- [x] Add difficulty selection and streak-aware scoring
- [x] Refresh UI with modular panels and physical/on-screen keyboard sync
- [ ] Surface word definitions after each round (locks screen until the player chooses `Continue` or `Play Again`)
- [ ] Offer rematch flow that keeps current users in-app without relaunching
- [ ] Add daily challenge mode with rotating curated word sets
- [ ] Localise UI strings and support multiple languages

## Additional Feature Ideas
- Word definition modal that appears after a round and stays visible until the player confirms whether they will continue their streak or start a new game.
- Persistent word history so users can review previously seen words and definitions.
- Configurable accessibility options (high-contrast palette, adjustable fonts, screen reader hints).
- Optional sound cues for correct and incorrect guesses with a mute toggle.

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
