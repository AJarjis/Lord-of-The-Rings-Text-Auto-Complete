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
        StringBuilder searchResult = new StringBuilder();

        Queue<TrieNode> queue = new LinkedList<>();
        queue.add(root);

        // Continuiously add each node to queue in breadth first order
        while (!queue.isEmpty()) {
            TrieNode currentNode = queue.remove();

            // Appends valid character to string
            if (currentNode.value != null) {
                searchResult.append(currentNode.value);
            }

            // Add all offspring of current node to queue
            for (int i = 0; i < currentNode.offspring.length; i++) {
                if (currentNode.offspring[i] != null) {
                    queue.add(currentNode.offspring[i]);
                }
            }
        }

        return searchResult.toString();
    }

    /**
     * Traverses through trie in depth first traversal
     *
     * @return string representing a depth first traversal
     */
    public String outputDepthFirstSearch() {
        StringBuilder searchResult = new StringBuilder();
        Stack<TrieNode> s = new Stack<>();

        HashSet<TrieNode> seen = new HashSet<>();

        s.push(root);

        while (!s.isEmpty()) {
            TrieNode currentNode = s.peek();

            if (currentNode.value != null) {
                searchResult.append(currentNode.value);
            }

            seen.add(currentNode);
            int pos = 0;
            boolean found = false;
            while (!found && pos < currentNode.offspring.length) {
                if (seen.contains(currentNode.offspring[pos])) {
                    pos++;
                } else {
                    found = true;
                }
            }
            if (!found) {
                s.pop();
            } else {
                s.push(currentNode.offspring[pos]);
            }
        }
        return searchResult.toString();
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
    public List getAllWords() {
        return null;
    }

    public static void main(String[] args) {
        Trie myTrie = new Trie();

        myTrie.add("cheers");
        myTrie.add("cheese");
        myTrie.add("chat");
        myTrie.add("cat");
        myTrie.add("bat");

        System.out.println(myTrie.contains("cheers"));
        System.out.println(myTrie.contains("bat"));
        System.out.println(myTrie.contains("chat"));
        System.out.println(myTrie.contains("cat"));
        System.out.println(myTrie.contains("ca"));

        Trie newTrie = myTrie.getSubTrie("ch");

        System.out.println(myTrie.outputBreadthFirstSearch());
        System.out.println(myTrie.outputDepthFirstSearch());

    }
}
