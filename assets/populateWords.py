"""Generate assets/words.json from a curated list of common English words.

Difficulty is assigned by word length:
  - easy:   3-5 letters
  - medium: 6-8 letters
  - hard:   9+ letters

Using a hand-picked list (rather than a raw dictionary dump) keeps the game
fun: every word is a recognisable, guessable English word.
"""

import json

# Curated, common English words. Difficulty is derived from length below.
CURATED_WORDS = [
    # short / easy (3-5 letters)
    "cat", "dog", "sun", "moon", "star", "tree", "fish", "bird", "frog", "lion",
    "book", "door", "milk", "rain", "snow", "fire", "wind", "leaf", "rock", "sand",
    "cake", "rice", "soup", "bread", "apple", "lemon", "grape", "peach", "mango",
    "house", "chair", "table", "clock", "phone", "music", "dance", "smile", "happy",
    "water", "river", "ocean", "beach", "cloud", "storm", "light", "night", "green",
    "horse", "tiger", "zebra", "panda", "sheep", "mouse", "snake", "eagle", "shark",
    "plant", "grass", "field", "stone", "glass", "paper", "pencil",

    # medium (6-8 letters)
    "garden", "flower", "forest", "island", "desert", "valley", "meadow", "jungle",
    "rabbit", "monkey", "turtle", "donkey", "parrot", "spider", "dragon", "dolphin",
    "guitar", "violin", "piano", "trumpet", "camera", "window", "pillow", "blanket",
    "kitchen", "bedroom", "library", "station", "airport", "harbour", "village",
    "morning", "evening", "weather", "thunder", "rainbow", "sunrise", "sunset",
    "biscuit", "popcorn", "cabbage", "carrot", "pumpkin", "spinach", "avocado",
    "journey", "holiday", "weekend", "balloon", "lantern", "compass", "anchor",

    # hard (9+ letters)
    "adventure", "butterfly", "chocolate", "dangerous", "education", "furniture",
    "geography", "happiness", "important", "knowledge", "landscape", "mountain",
    "newspaper", "orchestra", "playground", "raspberry", "telephone", "umbrella",
    "vegetable", "waterfall", "xylophone", "yesterday", "astronaut", "basketball",
    "celebration", "dictionary", "electricity", "friendship", "helicopter",
    "imagination", "kangaroo", "lighthouse", "microscope", "neighbour",
    "pineapple", "restaurant", "strawberry", "television", "understand",
    "watermelon", "alligator", "binoculars", "caterpillar", "dinosaur",
]


def classify_difficulty(word):
    length = len(word)
    if length <= 5:
        return "easy"
    elif length <= 8:
        return "medium"
    else:
        return "hard"


def generate_word_data(output_file):
    seen = set()
    structured_words = []
    for raw in CURATED_WORDS:
        word = raw.strip().lower()
        if not word.isalpha() or word in seen or len(word) < 3:
            continue
        seen.add(word)
        structured_words.append({
            "word": word,
            "difficulty": classify_difficulty(word),
            "wordLength": len(word),
        })

    structured_words.sort(key=lambda w: (w["difficulty"], w["word"]))

    with open(output_file, "w") as json_out:
        json.dump(structured_words, json_out, indent=2)

    print(f"✅ {len(structured_words)} words written to {output_file}")


if __name__ == "__main__":
    generate_word_data("words.json")
