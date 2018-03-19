/** ***************************************************************************
 *
 * File        : AutoCompletionTrie.java
 *
 * Date        : 15-Jan-2018
 *
 * Description : A class to model a trie data structure.
 *
 * Author      : Ali Jarjis
 *
 ******************************************************************************/
package autocomplete;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Ali Jarjis
 */
public class AutoCompletionTrie {

    AutoCompletionTrieNode root;

    /**
     * Constructs a Trie with a null root
     */
    public AutoCompletionTrie() {
        this.root = new AutoCompletionTrieNode(null);
    }

    /**
     * Constructs a Trie with a given trienode as the root
     *
     * @param root trienode to set as root
     */
    public AutoCompletionTrie(AutoCompletionTrieNode root) {
        this.root = root;
        this.root.value = null;
    }

    /**
     * Adds a key to the trie
     *
     * @param key           key to add to trie
     * @param frequency     amount of times to add this key to trie
     * @return              true if successful, else false
     */
    public boolean add(String key, int frequency) {
        boolean success = false;
        AutoCompletionTrieNode currentNode = this.root;

        //Loops through each char in key adding to Trie if it doesn't exist
        for (int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);

            // Adds char to Trie if it doesn't exist
            if (currentNode.setOffspring(c)) {
                success = true;
            }

            currentNode = currentNode.getOffspring(c);;
        }

        currentNode.isWord = true;
        currentNode.frequency = frequency;

        return success;
    }

    /**
     * Checks if key exists in trie
     *
     * @param key key to search for
     * @return true if key exists, else false
     */
    public boolean contains(String key) {
        AutoCompletionTrieNode currentNode = this.root;

        // Loops through each cahr in key checking if it exists in the Trie
        for (int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);

            AutoCompletionTrieNode nextNode = currentNode.getOffspring(c);

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

        Queue<AutoCompletionTrieNode> queue = new LinkedList<>();
        queue.add(root);

        // Continuiously add each node to queue in breadth first order
        while (!queue.isEmpty()) {
            AutoCompletionTrieNode currentNode = queue.remove();

            // Appends valid character to result
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
    public String outputDepthFirstSearch(AutoCompletionTrieNode currentNode,
            StringBuilder dfsResult) {
        // For each offspring, recursively call this method
        for (int i = 0; i < currentNode.offspring.length; i++) {
            if (currentNode.offspring[i] != null) {
                outputDepthFirstSearch(currentNode.offspring[i], dfsResult);
            }
        }

        // Append valid character to result
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
    public AutoCompletionTrie getSubTrie(String prefix) {
        AutoCompletionTrieNode currentNode = this.root;

        //Loops through each char in prefix, checking they exist in the trie
        for (int i = 0; i < prefix.length(); i++) {
            char c = prefix.charAt(i);

            AutoCompletionTrieNode nextNode = currentNode.getOffspring(c);

            // Return null if prefix does not exist in trie
            if (nextNode == null) {
                return null;
            }

            currentNode = nextNode;
        }

        return new AutoCompletionTrie(currentNode);
    }

    /**
     * Retrieves all words in the trie (recursively)
     *
     * @param currentNode   node currently being traversed
     * @param word          word currently being built
     * @param allWords      all words currently found within trie
     * @return              a list containing all words in the trie
     */
    public HashMap getAllWords(AutoCompletionTrieNode currentNode, 
            StringBuilder word, HashMap allWords) {
        // Appends current node character to word (if valid)
        if (currentNode.value != null) {
            word.append(currentNode.value);
        }

        // Adds word to list once found
        if (currentNode.isWord) {
            allWords.put(word.toString(), currentNode.frequency);
        }
        
        // For each offspring, recursively call this method
        for (int i = 0; i < currentNode.offspring.length; i++) {
            if (currentNode.offspring[i] != null) {
                getAllWords(currentNode.offspring[i], word, allWords);
            }
        }
        
        // Removes current node character from word (if valid)
        if (currentNode.value != null) {
            word.deleteCharAt(word.length() - 1);
        }

        return allWords;
    }
}
