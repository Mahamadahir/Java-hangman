window.progressData = {
  "generatedAt": "2026-05-31T00:00:00Z",
  "features": [
    {
      "id": "feature-foundations",
      "title": "Project Foundations",
      "start": "2025-05-21",
      "due": "2025-05-24",
      "state": "complete",
      "methods": [
        {
          "id": "method-scaffold",
          "title": "Initial Maven + Swing scaffolding",
          "start": "2025-05-21",
          "due": "2025-05-21",
          "state": "complete",
          "tests": [
            {
              "id": "test-hangman-bootstrap",
              "title": "Manual HangmanLauncher bootstrap smoke",
              "start": "2025-05-21",
              "due": "2025-05-21",
              "state": "complete"
            }
          ]
        },
        {
          "id": "method-wordbank",
          "title": "WordBank + asset pipeline",
          "start": "2025-05-24",
          "due": "2025-05-24",
          "state": "complete",
          "tests": [
            {
              "id": "test-wordbank-loading",
              "title": "WordBank loads unique words from assets/words.json",
              "start": "2025-05-24",
              "due": "2025-05-24",
              "state": "complete"
            }
          ]
        }
      ]
    },
    {
      "id": "feature-modern-gameplay",
      "title": "Modern Gameplay Loop & UI",
      "start": "2025-06-10",
      "due": "2025-06-13",
      "state": "complete",
      "methods": [
        {
          "id": "method-keyboard-panel",
          "title": "KeyboardPanel + HUD revamp",
          "start": "2025-06-10",
          "due": "2025-06-13",
          "state": "complete",
          "tests": [
            {
              "id": "test-keyboard-ui",
              "title": "Manual UI snapshot review vs Amin Figma",
              "start": "2025-06-13",
              "due": "2025-06-13",
              "state": "complete"
            }
          ]
        },
        {
          "id": "method-round-flow",
          "title": "Round flow polish + failure reveal",
          "start": "2025-06-13",
          "due": "2025-06-13",
          "state": "complete",
          "tests": [
            {
              "id": "test-round-reveal",
              "title": "Losing a round surfaces the secret word",
              "start": "2025-06-13",
              "due": "2025-06-13",
              "state": "complete"
            }
          ]
        }
      ]
    },
    {
      "id": "feature-multi-sessions",
      "title": "Multi-Game Sessions & Profiles",
      "start": "2025-06-13",
      "due": "2025-06-20",
      "state": "complete",
      "methods": [
        {
          "id": "method-multi-game-controller",
          "title": "GameController multi-game orchestration",
          "start": "2025-06-13",
          "due": "2025-06-18",
          "state": "complete",
          "tests": [
            {
              "id": "test-multi-game-loop",
              "title": "Manual multi-game smoke (win/lose mix)",
              "start": "2025-06-18",
              "due": "2025-06-18",
              "state": "complete"
            }
          ]
        },
        {
          "id": "method-scoretracker-streaks",
          "title": "ScoreTracker streak-aware persistence",
          "start": "2025-06-18",
          "due": "2025-06-20",
          "state": "complete",
          "tests": [
            {
              "id": "test-scoretracker-json",
              "title": "scores.json retains wins/losses per user",
              "start": "2025-06-20",
              "due": "2025-06-20",
              "state": "complete"
            }
          ]
        }
      ]
    },
    {
      "id": "feature-leaderboard",
      "title": "Leaderboard + Word Rotation",
      "start": "2025-06-20",
      "due": "2025-06-22",
      "state": "complete",
      "methods": [
        {
          "id": "method-leaderboard-panel",
          "title": "Leaderboard panel and stats CTA",
          "start": "2025-06-20",
          "due": "2025-06-22",
          "state": "complete",
          "tests": [
            {
              "id": "test-leaderboard-refresh",
              "title": "Leaderboard refresh reflects saved scores",
              "start": "2025-06-22",
              "due": "2025-06-22",
              "state": "complete"
            }
          ]
        },
        {
          "id": "method-word-rotation",
          "title": "WordBank no-repeat selection",
          "start": "2025-06-20",
          "due": "2025-06-22",
          "state": "complete",
          "tests": [
            {
              "id": "test-word-rotation",
              "title": "Word deck exhausts before reusing entries",
              "start": "2025-06-22",
              "due": "2025-06-22",
              "state": "complete"
            }
          ]
        }
      ]
    },
    {
      "id": "feature-architecture-refresh",
      "title": "Architecture Refresh",
      "start": "2025-10-18",
      "due": "2025-10-21",
      "state": "complete",
      "methods": [
        {
          "id": "method-controller-refactor",
          "title": "GameController + services refactor",
          "start": "2025-10-18",
          "due": "2025-10-21",
          "state": "complete",
          "tests": [
            {
              "id": "test-controller-refactor",
              "title": "Regression sweep for refactored controller",
              "start": "2025-10-21",
              "due": "2025-10-21",
              "state": "complete"
            }
          ]
        },
        {
          "id": "method-ui-modules",
          "title": "Modular screens (User, Difficulty, Game)",
          "start": "2025-10-18",
          "due": "2025-10-21",
          "state": "complete",
          "tests": [
            {
              "id": "test-ui-modules",
              "title": "User/Difficulty screen swaps stay stable",
              "start": "2025-10-21",
              "due": "2025-10-21",
              "state": "complete"
            }
          ]
        }
      ]
    },
    {
      "id": "feature-stability-polish",
      "title": "Stability Polishing & Docs",
      "start": "2025-10-24",
      "due": "2025-10-26",
      "state": "complete",
      "methods": [
        {
          "id": "method-word-loading-fix",
          "title": "Word loading + user UX fixes",
          "start": "2025-10-24",
          "due": "2025-10-26",
          "state": "complete",
          "tests": [
            {
              "id": "test-word-loading",
              "title": "Regression on ScoreTracker + WordBank load",
              "start": "2025-10-26",
              "due": "2025-10-26",
              "state": "complete"
            }
          ]
        },
        {
          "id": "method-readme-roadmap",
          "title": "README roadmap + docs refresh",
          "start": "2025-10-24",
          "due": "2025-10-26",
          "state": "complete",
          "tests": [
            {
              "id": "test-docs-review",
              "title": "Docs reviewed with latest features",
              "start": "2025-10-26",
              "due": "2025-10-26",
              "state": "complete"
            }
          ]
        }
      ]
    },
    {
      "id": "feature-word-definitions",
      "title": "Post-Round Word Definitions",
      "start": "2025-11-03",
      "due": "2025-11-12",
      "state": "complete",
      "methods": [
        {
          "id": "method-definition-modal",
          "title": "Definition modal + lock screen",
          "start": "2025-11-03",
          "due": "2025-11-10",
          "state": "complete",
          "tests": [
            {
              "id": "test-definition-modal",
              "title": "Modal blocks inputs until continue",
              "start": "2025-11-03",
              "due": "2025-11-10",
              "state": "complete"
            }
          ]
        },
        {
          "id": "method-dictionary-provider",
          "title": "Dictionary provider + caching",
          "start": "2025-11-04",
          "due": "2025-11-12",
          "state": "complete",
          "tests": [
            {
              "id": "test-dictionary-cache",
              "title": "Dictionary lookups cached per word",
              "start": "2025-11-04",
              "due": "2025-11-12",
              "state": "complete"
            }
          ]
        }
      ]
    },
    {
      "id": "feature-rematch-flow",
      "title": "Seamless Rematch Flow",
      "start": "2025-11-10",
      "due": "2025-11-19",
      "state": "complete",
      "methods": [
        {
          "id": "method-rematch-controller",
          "title": "Rematch controller keep-alive",
          "start": "2025-11-10",
          "due": "2025-11-16",
          "state": "complete",
          "tests": [
            {
              "id": "test-rematch-flow",
              "title": "Rematch reuses user roster without relaunch",
              "start": "2025-11-10",
              "due": "2025-11-16",
              "state": "complete"
            }
          ]
        },
        {
          "id": "method-rematch-state",
          "title": "Scoreboard carry-over + streak reset logic",
          "start": "2025-11-12",
          "due": "2025-11-19",
          "state": "complete",
          "tests": [
            {
              "id": "test-rematch-scores",
              "title": "Score validation across rematches",
              "start": "2025-11-12",
              "due": "2025-11-19",
              "state": "complete"
            }
          ]
        }
      ]
    },
    {
      "id": "feature-daily-challenge",
      "title": "Daily Challenge Mode",
      "start": "2025-11-17",
      "due": "2025-11-30",
      "state": "pending",
      "methods": [
        {
          "id": "method-daily-seeding",
          "title": "Daily seed + curated word sets",
          "start": "2025-11-17",
          "due": "2025-11-26",
          "state": "pending",
          "tests": [
            {
              "id": "test-daily-seed",
              "title": "Daily seed stays constant for 24h",
              "start": "2025-11-17",
              "due": "2025-11-26",
              "state": "pending"
            }
          ]
        },
        {
          "id": "method-daily-leaderboard",
          "title": "Daily leaderboard and streak badge",
          "start": "2025-11-19",
          "due": "2025-11-30",
          "state": "pending",
          "tests": [
            {
              "id": "test-daily-leaderboard",
              "title": "Daily board calculates streak bonuses",
              "start": "2025-11-19",
              "due": "2025-11-30",
              "state": "pending"
            }
          ]
        }
      ]
    },
    {
      "id": "feature-localization",
      "title": "Localization & Accessibility",
      "start": "2025-11-24",
      "due": "2025-12-10",
      "state": "pending",
      "methods": [
        {
          "id": "method-i18n-framework",
          "title": "i18n resource bundle + formatter",
          "start": "2025-11-24",
          "due": "2025-12-05",
          "state": "pending",
          "tests": [
            {
              "id": "test-i18n-switch",
              "title": "Language switch toggles runtime strings",
              "start": "2025-11-24",
              "due": "2025-12-05",
              "state": "pending"
            }
          ]
        },
        {
          "id": "method-accessibility-suite",
          "title": "Accessibility + high-contrast suite",
          "start": "2025-11-24",
          "due": "2025-12-10",
          "state": "pending",
          "tests": [
            {
              "id": "test-accessibility",
              "title": "Accessibility review + keyboard nav",
              "start": "2025-11-24",
              "due": "2025-12-10",
              "state": "pending"
            }
          ]
        }
      ]
    }
  ]
};
