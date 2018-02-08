/*****************************************************************************

File        : Trie.java

Date        : 15-Jan-2018

Description : A class to model a trie data structure.

Author      : Ali Jarjis

******************************************************************************/

package autocomplete;

import java.util.List;

/**
 *
 * @author Ali Jarjis
 */
public class Trie {
    private TrieNode root;

    /**
     * Constructs a Trie with a null root
     */
    public Trie() {
        this.root = new TrieNode(null);
    }
    
    /**
     * Adds a key to the trie
     * 
     * @param key   key to add to trie
     * @return      true if successful, else false
     */
    public boolean add(String key) {
        
    }
    
    /**
     * Checks if key exists in trie
     * 
     * @param key   key to search for
     * @return      true if key exists, else false
     */
    public boolean contains(String key) {
        
    }
    
    /**
     * Traverses through trie in breadth first traversal
     * 
     * @return      string representing a breadth first traversal
     */
    public String outputBreadthFirstSearch() {
        
    }
    
    /**
     * Traverses through trie in depth first traversal
     * 
     * @return      string representing a depth first traversal
     */
    public String outputDepthFirstSearch() {
        
    }
    
    /**
     * Retrieves a new trie rooted at a given prefix
     * 
     * @param prefix    prefix to root trie at
     * @return          sub-trie rooted at given prefix if exists, else null
     */
    public Trie getSubTrie(String prefix) {
        
    }
    
    /**
     * Retrieves all words in the trie
     * 
     * @return          a list containing all words in the trie
     */
    public List getAllWords() {
        
    }
}
