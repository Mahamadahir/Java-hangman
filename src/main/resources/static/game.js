const els = {
  startScreen: document.getElementById("startScreen"),
  gameScreen: document.getElementById("gameScreen"),
  playerName: document.getElementById("playerName"),
  difficulty: document.getElementById("difficulty"),
  startButton: document.getElementById("startButton"),
  startError: document.getElementById("startError"),
  playerLabel: document.getElementById("playerLabel"),
  streakLabel: document.getElementById("streakLabel"),
  livesLabel: document.getElementById("livesLabel"),
  wordDisplay: document.getElementById("wordDisplay"),
  wrongLetters: document.getElementById("wrongLetters"),
  keyboard: document.getElementById("keyboard"),
  parts: document.querySelectorAll("#gallowsSvg .part"),
  roundModal: document.getElementById("roundModal"),
  modalTitle: document.getElementById("modalTitle"),
  modalWord: document.getElementById("modalWord"),
  modalDefinition: document.getElementById("modalDefinition"),
  continueButton: document.getElementById("continueButton"),
  quitButton: document.getElementById("quitButton"),
  leaderboardToggle: document.getElementById("leaderboardToggle"),
  leaderboardPanel: document.getElementById("leaderboardPanel"),
  leaderboardDifficulty: document.getElementById("leaderboardDifficulty"),
  leaderboardList: document.getElementById("leaderboardList"),
  closeLeaderboard: document.getElementById("closeLeaderboard"),
};

const LETTERS = "abcdefghijklmnopqrstuvwxyz".split("");
let state = null;

async function api(method, path, body) {
  const response = await fetch(path, {
    method,
    headers: body ? { "Content-Type": "application/json" } : undefined,
    body: body ? JSON.stringify(body) : undefined,
  });
  if (!response.ok) {
    const payload = await response.json().catch(() => ({}));
    throw new Error(payload.error || "Something went wrong");
  }
  return response.json();
}

function buildKeyboard() {
  els.keyboard.innerHTML = "";
  for (const letter of LETTERS) {
    const button = document.createElement("button");
    button.type = "button";
    button.textContent = letter;
    button.dataset.letter = letter;
    button.addEventListener("click", () => guess(letter));
    els.keyboard.appendChild(button);
  }
}

function resetKeyboard() {
  for (const button of els.keyboard.querySelectorAll("button")) {
    button.disabled = false;
    button.classList.remove("correct", "incorrect");
  }
}

function render() {
  els.playerLabel.textContent = els.playerName.value.trim();
  els.streakLabel.textContent = `Streak: ${state.streak}`;
  els.livesLabel.textContent = `Lives: ${state.remainingLives}`;
  els.wordDisplay.textContent = state.maskedWord.split("").join(" ");
  els.wrongLetters.textContent = state.wrongLetters.length
    ? `Missed: ${state.wrongLetters.join(" ").toUpperCase()}`
    : "";

  const mistakes = state.maxLives - state.remainingLives;
  els.parts.forEach((part) => {
    part.classList.toggle("show", Number(part.dataset.part) < mistakes);
  });

  const wrong = new Set(state.wrongLetters.map((l) => l.toLowerCase()));
  const revealed = new Set(state.maskedWord.toLowerCase().split("").filter((c) => c !== "_"));
  for (const button of els.keyboard.querySelectorAll("button")) {
    const letter = button.dataset.letter;
    if (wrong.has(letter)) {
      button.disabled = true;
      button.classList.add("incorrect");
    } else if (revealed.has(letter)) {
      button.disabled = true;
      button.classList.add("correct");
    }
  }
}

async function startGame() {
  const playerName = els.playerName.value.trim();
  if (!playerName) {
    els.startError.textContent = "Enter a name to play";
    els.startError.hidden = false;
    return;
  }
  els.startError.hidden = true;
  try {
    state = await api("POST", "/api/game", {
      playerName,
      difficulty: els.difficulty.value,
    });
    localStorage.setItem("hangman-player", playerName);
    els.startScreen.hidden = true;
    els.gameScreen.hidden = false;
    resetKeyboard();
    render();
  } catch (err) {
    els.startError.textContent = err.message;
    els.startError.hidden = false;
  }
}

async function guess(letter) {
  if (!state || state.status !== "IN_PROGRESS") {
    return;
  }
  try {
    state = await api("POST", `/api/game/${state.gameId}/guess`, { letter });
    render();
    flagGuess(letter, state.lastGuess);
    if (state.status !== "IN_PROGRESS") {
      showRoundEnd();
    }
  } catch (err) {
    console.error(err);
  }
}

function flagGuess(letter, result) {
  const button = els.keyboard.querySelector(`button[data-letter="${letter}"]`);
  if (!button) {
    return;
  }
  if (result === "CORRECT") {
    button.disabled = true;
    button.classList.add("correct");
  } else if (result === "INCORRECT") {
    button.disabled = true;
    button.classList.add("incorrect");
  } else if (result === "ALREADY_GUESSED" || result === "INVALID") {
    button.classList.remove("shake");
    void button.offsetWidth;
    button.classList.add("shake");
  }
}

function showRoundEnd() {
  els.modalTitle.textContent = state.status === "WON" ? "You got it!" : "Out of lives";
  els.modalWord.textContent = state.word;
  els.modalDefinition.textContent = state.definition || "";
  els.continueButton.textContent = state.status === "WON" ? "Next word" : "Play again";
  els.roundModal.hidden = false;
}

async function nextRound() {
  els.roundModal.hidden = true;
  try {
    state = await api("POST", `/api/game/${state.gameId}/next`);
    resetKeyboard();
    render();
  } catch (err) {
    quitGame();
  }
}

function quitGame() {
  els.roundModal.hidden = true;
  els.gameScreen.hidden = true;
  els.startScreen.hidden = false;
  state = null;
}

async function showLeaderboard() {
  try {
    const rows = await api("GET", `/api/leaderboard?difficulty=${els.leaderboardDifficulty.value}`);
    els.leaderboardList.innerHTML = "";
    if (!rows.length) {
      const li = document.createElement("li");
      li.textContent = "No scores yet";
      els.leaderboardList.appendChild(li);
    }
    for (const row of rows) {
      const li = document.createElement("li");
      const name = document.createElement("span");
      name.textContent = `${row.player} (${row.difficulty})`;
      const score = document.createElement("span");
      score.textContent = `${row.bestStreak}`;
      li.append(name, score);
      els.leaderboardList.appendChild(li);
    }
    els.leaderboardPanel.hidden = false;
  } catch (err) {
    console.error(err);
  }
}

document.addEventListener("keydown", (event) => {
  if (!els.gameScreen.hidden && /^[a-z]$/i.test(event.key)) {
    guess(event.key.toLowerCase());
  }
});

els.startButton.addEventListener("click", startGame);
els.continueButton.addEventListener("click", nextRound);
els.quitButton.addEventListener("click", quitGame);
els.leaderboardToggle.addEventListener("click", showLeaderboard);
els.leaderboardDifficulty.addEventListener("change", showLeaderboard);
els.closeLeaderboard.addEventListener("click", () => (els.leaderboardPanel.hidden = true));

const savedName = localStorage.getItem("hangman-player");
if (savedName) {
  els.playerName.value = savedName;
}
buildKeyboard();
