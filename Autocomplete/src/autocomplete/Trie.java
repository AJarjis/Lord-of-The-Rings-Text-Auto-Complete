/** ***************************************************************************
 *
 * File        : Trie.java
 *
 * Date        : 15-Jan-2018
 *
 * Description : A class to model a trie data structure.
 *
 * Author      : Ali Jarjis
 *
 ***************************************************************************** */
package autocomplete;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 *
 * @author Ali Jarjis
 */
public class Trie {

    TrieNode root;

    /**
     * Constructs a Trie with a null root
     */
    public Trie() {
        this.root = new TrieNode(null);
    }

    /**
     * Constructs a Trie with a given trienode as the root
     *
     * @param root trienode to set as root
     */
    public Trie(TrieNode root) {
        this.root = root;
    }

    /**
     * Adds a key to the trie
     *
     * @param key key to add to trie
     * @return true if successful, else false
     */
    public boolean add(String key) {
        boolean success = false;
        TrieNode currentNode = this.root;

        //Loops through each char in key adding to Trie if it doesn't exist
        for (int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);

            TrieNode nextNode = currentNode.getOffspring(c);

            // Adds char to Trie if it doesn't exist
            if (nextNode == null) {
                currentNode.setOffspring(c);
                nextNode = currentNode.getOffspring(c);
                success = true;
            }

            currentNode = nextNode;
        }

        currentNode.isWord = true;

        return success;
    }

    /**
     * Checks if key exists in trie
     *
     * @param key key to search for
     * @return true if key exists, else false
     */
    public boolean contains(String key) {
        TrieNode currentNode = this.root;

        // Loops through each cahr in key checking if it exists in the Trie
        for (int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);

            TrieNode nextNode = currentNode.getOffspring(c);

            if (nextNode == null) {
                return false;
            }

            currentNode = nextNode;
        }
        // Returns true if key is a whole word in Trie        
        return currentNode.isWord;
    }

    /**
     * Traverses through trie in breadth first traversal
     *
     * @return string representing a breadth first traversal
     */
    public String outputBreadthFirstSearch() {
        StringBuilder bfsResult = new StringBuilder();

        Queue<TrieNode> queue = new LinkedList<>();
        queue.add(root);

        // Continuiously add each node to queue in breadth first order
        while (!queue.isEmpty()) {
            TrieNode currentNode = queue.remove();

            // Appends valid character to string
            if (currentNode.value != null) {
                bfsResult.append(currentNode.value);
            }

            // Add all offspring of current node to queue
            for (int i = 0; i < currentNode.offspring.length; i++) {
                if (currentNode.offspring[i] != null) {
                    queue.add(currentNode.offspring[i]);
                }
            }
        }

        return bfsResult.toString();
    }

    /**
     * Traverses through trie in depth first traversal (recursively)
     *
     * @param currentNode current node being traversed
     * @param dfsResult string representing current depth first traversal
     * @return string representing a depth first traversal
     */
    public String outputDepthFirstSearch(TrieNode currentNode, StringBuilder dfsResult) {
        // For each offspring, recursively call this method
        for (int i = 0; i < currentNode.offspring.length; i++) {
            if (currentNode.offspring[i] != null) {
                outputDepthFirstSearch(currentNode.offspring[i], dfsResult);
            }
        }

        // Append valid character to string
        if (currentNode.value != null) {
            dfsResult.append(currentNode.value);
        }

        return dfsResult.toString();
    }

    /**
     * Retrieves a new trie rooted at a given prefix
     *
     * @param prefix prefix to root trie at
     * @return sub-trie rooted at given prefix if exists, else null
     */
    public Trie getSubTrie(String prefix) {
        TrieNode currentNode = this.root;

        //Loops through each char in prefix, checking they exist in the trie
        for (int i = 0; i < prefix.length(); i++) {
            char c = prefix.charAt(i);

            TrieNode nextNode = currentNode.getOffspring(c);

            // Return null if prefix does not exist in trie
            if (nextNode == null) {
                return null;
            }

            currentNode = nextNode;
        }

        return new Trie(currentNode);
    }

    /**
     * Retrieves all words in the trie
     *
     * @return a list containing all words in the trie
     */
    public List getAllWords(TrieNode currentNode, StringBuilder word, List allWords) {
        if (currentNode.value != null) {
            word.append(currentNode.value);
        }

        if (currentNode.isWord) {
            allWords.add(new StringBuilder(word));
            word.setLength(0);
        } else {
            // For each offspring, recursively call this method
            for (int i = 0; i < currentNode.offspring.length; i++) {
                if (currentNode.offspring[i] != null) {
                    allWords = getAllWords(currentNode.offspring[i], word, allWords);
                }
            }
        }

        return allWords;
    }

    public static void main(String[] args) {
        Trie myTrie = new Trie();

        // Tests for adding to trie
        System.out.println("Added cheers successfully? "
                + myTrie.add("cheers"));
        System.out.println("Added chat successfully? "
                + myTrie.add("cheese"));
        System.out.println("Added chat successfully? "
                + myTrie.add("chat"));
        System.out.println("Added cat successfully? "
                + myTrie.add("cat"));
        System.out.println("Added bat successfully? "
                + myTrie.add("bat"));
        System.out.println("Added chat successfully? "
                + myTrie.add("chat"));

        // Tests for trie contains
        System.out.println("\nContains cheers? "
                + myTrie.contains("cheers"));
        System.out.println("Contains bat? "
                + myTrie.contains("bat"));
        System.out.println("Contains chat? "
                + myTrie.contains("chat"));
        System.out.println("Contains cat? "
                + myTrie.contains("cat"));
        System.out.println("Contains ca? "
                + myTrie.contains("ca"));
        System.out.println("Contains batman? "
                + myTrie.contains("ca"));

        // Test for bfs trie traversal
        String bfs = myTrie.outputBreadthFirstSearch();
        System.out.println("\nBreadth First Search: " + bfs);
        System.out.println("BFS: " + bfs.equals("bcaahttaetersse"));

        // Test for dfs trie traversal
        StringBuilder dfsResult = new StringBuilder();
        String dfs = myTrie.outputDepthFirstSearch(myTrie.root, dfsResult);
        System.out.println("\nDepth First Search: " + dfs);
        System.out.println("DFS: " + dfs.equals("tabtatasreseehc"));

        // Test for sub-trie
        Trie newTrie = myTrie.getSubTrie("ch");
        System.out.println("\nBreadth First Search (sub-trie 'ch'): "
                + newTrie.outputBreadthFirstSearch());

        // Test for getting all words from trie
        StringBuilder word = new StringBuilder();
        List allWords = new ArrayList();
        System.out.println("\nAll Words: "
                + myTrie.getAllWords(myTrie.root, word, allWords).toString());
    }
}
