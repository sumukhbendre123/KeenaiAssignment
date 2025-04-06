import java.io.*;
import java.util.*;

class TrieNode {
    Map<Character, TrieNode> children = new HashMap<>();
    boolean isEndOfWord = false;
}

class Trie {
    private final TrieNode root = new TrieNode();

    public void insert(String word) {
        TrieNode curr = root;
        for (char c : word.toCharArray()) {
            curr.children.putIfAbsent(c, new TrieNode());
            curr = curr.children.get(c);
        }
        curr.isEndOfWord = true;
    }

    public boolean search(String word) {
        TrieNode curr = root;
        for (char c : word.toCharArray()) {
            if (!curr.children.containsKey(c)) return false;
            curr = curr.children.get(c);
        }
        return curr.isEndOfWord;
    }

    private void collectWords(TrieNode node, StringBuilder current, List<String> results) {
        if (node.isEndOfWord) results.add(current.toString());
        for (Map.Entry<Character, TrieNode> entry : node.children.entrySet()) {
            current.append(entry.getKey());
            collectWords(entry.getValue(), current, results);
            current.deleteCharAt(current.length() - 1);
        }
    }

    public List<String> getPrefixMatches(String prefix) {
        TrieNode curr = root;
        for (char c : prefix.toCharArray()) {
            if (!curr.children.containsKey(c)) return new ArrayList<>();
            curr = curr.children.get(c);
        }
        List<String> results = new ArrayList<>();
        StringBuilder current = new StringBuilder(prefix);
        collectWords(curr, current, results);
        return results;
    }
}

public class WordSearchSystem {
    private final Trie trie = new Trie();
    private final Map<String, Integer> wordRanks = new HashMap<>();

    public void loadWords(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String word;
            while ((word = reader.readLine()) != null) {
                if (!word.trim().isEmpty() && !wordRanks.containsKey(word)) {
                    trie.insert(word);
                    wordRanks.put(word, 0);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    public boolean searchWord(String word) {
        boolean exists = trie.search(word);
        if (exists) wordRanks.put(word, wordRanks.get(word) + 1);
        return exists;
    }

    public List<String> autoComplete(String prefix) {
        List<String> matches = trie.getPrefixMatches(prefix);
        Collections.sort(matches, new Comparator<String>() {
            public int compare(String a, String b) {
                int rankA = wordRanks.getOrDefault(a, 0);
                int rankB = wordRanks.getOrDefault(b, 0);
                return rankA != rankB ? Integer.compare(rankB, rankA) : a.compareTo(b);
            }
        });
        return matches;
    }

    public void incrementRank(String word) {
        if (wordRanks.containsKey(word)) {
            wordRanks.put(word, wordRanks.get(word) + 1);
        }
    }

    public int getRank(String word) {
        return wordRanks.getOrDefault(word, -1);
    }

public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    WordSearchSystem wss = new WordSearchSystem();

    System.out.print("📂 Enter the path to the file containing words: ");
    String path = scanner.nextLine();
    wss.loadWords(path);

    int choice = -1;
    do {
        System.out.println("\n========================================");
        System.out.println("🧭 MAIN MENU:");
        System.out.println("1️⃣  Search Word");
        System.out.println("2️⃣  Auto-complete with Ranking");
        System.out.println("3️⃣  Increment Word Rank");
        System.out.println("4️⃣  Get Word Rank");
        System.out.println("5️⃣  Exit");
        System.out.println("========================================");
        System.out.print("👉 Enter your choice (1-5): ");

        String input = scanner.nextLine();
        System.out.println();  // blank line before response

        try {
            choice = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("⚠️ Please enter a valid number between 1 and 5.\n");
            continue;
        }

        switch (choice) {
            case 1:
                System.out.print("🔍 Enter word to search: ");
                String searchWord = scanner.nextLine();
                boolean found = wss.searchWord(searchWord);
                System.out.println(found ? "✅ Word found!" : "❌ Word not found.");
                break;

            case 2:
                System.out.print("💡 Enter prefix for auto-complete: ");
                String prefix = scanner.nextLine();
                List<String> suggestions = wss.autoComplete(prefix);
                if (suggestions.isEmpty()) {
                    System.out.println("❌ No suggestions found.");
                } else {
                    System.out.println("📋 Suggestions:");
                    for (String suggestion : suggestions) {
                        System.out.println("   • " + suggestion);
                    }
                }
                break;

            case 3:
                System.out.print("⬆️  Enter word to increment rank: ");
                String incWord = scanner.nextLine();
                wss.incrementRank(incWord);
                System.out.println("🔼 Rank incremented for '" + incWord + "'");
                break;

            case 4:
                System.out.print("📊 Enter word to get rank: ");
                String rankWord = scanner.nextLine();
                int rank = wss.getRank(rankWord);
                if (rank != -1) {
                    System.out.println("🏅 Rank of '" + rankWord + "': " + rank);
                } else {
                    System.out.println("❌ Word not found.");
                }
                break;

            case 5:
                System.out.println("👋 Exiting... Goodbye!");
                break;

            default:
                System.out.println("⚠️ Invalid choice. Please enter a number between 1 and 5.");
        }

        System.out.println("\n----------------------------------------\n");

    } while (choice != 5);

    scanner.close();
}
}
