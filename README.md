

---

# 📚 Word Search System

This project implements an efficient **Word Search System** using a **Trie (Prefix Tree)** to support operations like word insertion, prefix-based auto-completion, and ranking based on word usage frequency.

---

## 🚀 Features

- **Load Words from File**: Load up to 100,000 words (one per line) from a file.
- **Auto-Completion**: Given a prefix, suggests all matching words ranked by usage frequency.
- **Rank Tracking**: Every word has a rank (default 0), which increases with every search.
- **Efficient Search**: Quickly checks if a word exists in the dictionary.
- **User-Friendly CLI**: Simple command-line interface to interact with the system.

---

## 🧠 Why Trie?

The **Trie** data structure was chosen because of its:

- **Efficient Prefix Lookups**: Time complexity for prefix matching is `O(L)`, where `L` is the length of the prefix.
- **Scalability**: Supports large word datasets efficiently.
- **Compact Storage**: Uses shared prefixes to reduce memory usage.

---

## 🏗️ Project Structure

```
WordSearchSystem.java
words.txt              # Sample word list file (one word per line)
README.md
```

---

## 🔧 How to Use

### 1. Compile the Code

```bash
javac WordSearchSystem.java
```

### 2. Run the Program

```bash
java WordSearchSystem
```

### 3. Follow the CLI Menu

```
📂 Enter the path to the file containing words: words.txt
```

Then choose one of the following options:

| Option | Description |
|--------|-------------|
| 1️⃣    | Search a word (also increments rank) |
| 2️⃣    | Get auto-complete suggestions for a prefix (sorted by rank) |
| 3️⃣    | Manually increment a word’s rank |
| 4️⃣    | Check the current rank of a word |
| 5️⃣    | Exit the system |

---

## 🔍 Sample Input File (`words.txt`)

```
hello
help
helicopter
hero
heat
heap
hell
helmet
```

---

## 🧪 How to Test

After running the program:

1. **Load the `words.txt`** file.
2. Use the menu to:
   - Search for a word like `hello`.
   - Check the rank of `hello` (should be incremented).
   - Try prefix `"he"` and see auto-complete results like `hello`, `help`, `hero`, etc.
   - Use option 3 to manually increment rank and see it reflected in auto-complete ordering.

---

## 📊 Ranking Logic

- Words are stored with a default rank of `0`.
- When searched (via option 1), their rank increases by `1`.
- Auto-complete suggestions are **sorted by rank descending**, and then alphabetically for ties.

---

## 🧮 How Ranking Works (Using HashMap)

To track how often each word is used or searched, we use a `HashMap<String, Integer>` where:

- **Key** = the word (e.g., `"hello"`)
- **Value** = the rank or frequency (e.g., `5` means it has been searched 5 times)

### ✅ Why HashMap?
- 💡 **Fast Lookup**: Retrieving or updating the rank of a word is done in constant time — **O(1)**.
- 🧱 **Simple to Implement**: Works well alongside the Trie structure for ranking logic.
- 🔄 **Dynamic Updates**: Easily increment the rank each time a word is used or manually increased.

### 🧪 Example:
```java
HashMap<String, Integer> wordRanks = new HashMap<>();

// When loading words
wordRanks.put("hello", 0);  // default rank

// When a word is searched or used
wordRanks.put("hello", wordRanks.get("hello") + 1);  // increment rank
```

### 🔍 During Auto-complete:
- We collect all words matching the prefix from the Trie.
- For each word, we fetch its rank from the HashMap.
- Then, we **sort all suggestions by rank (descending)** to show the most relevant results first.

---

## 🛠 Future Enhancements

- Add support for **deleting** words.
- Provide **case-insensitive** search.
- Implement a **persistent ranking database**.

---

## 🙌 Author

Sumukh Vinayak Bendre  
Indian Institute of Technology Roorkee

