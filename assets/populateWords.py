import json

def classify_difficulty(word):
    length = len(word)
    if length <= 7 and length>=4:
        return "easy"
    elif 8 <= length <= 11:
        return "medium"
    else:
        return "hard"

def generate_word_data(input_file, output_file):
    with open(input_file, 'r') as file:
        raw_words = [line.strip().lower() for line in file if line.strip().isalpha()]

    # Filter and format
    structured_words = []
    for word in raw_words:
        if 3 <= len(word) <= 17:  # reasonable game bounds
            structured_words.append({
                "word": word,
                "difficulty": classify_difficulty(word),
                "wordLength": len(word)
            })

    with open(output_file, 'w') as json_out:
        json.dump(structured_words, json_out, indent=2)

    print(f"✅ {len(structured_words)} words written to {output_file}")

# Example usage
generate_word_data("words_alpha.txt", "words.json")
