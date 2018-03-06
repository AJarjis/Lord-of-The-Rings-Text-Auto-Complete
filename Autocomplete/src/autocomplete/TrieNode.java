/** ***************************************************************************
 *
 * File        : TrieNode.java
 *
 * Date        : 15-Jan-2018
 *
 * Description : A class to model a trie node in a trie data structure.
 *
 * Author      : Ali Jarjis
 *
 ***************************************************************************** */
package autocomplete;

/**
 *
 * @author Ali Jarjis
 */
public class TrieNode {
    Character value;
    TrieNode[] offspring;
    boolean isWord;
    
    /**
     * Constructs a TrieNode with a given value
     *
     * @param character trienode's value
     */
    public TrieNode(Character character) {
        this.value = character;
        this.offspring = new TrieNode[26];
        this.isWord = false;
    }

    /**
     * Recovers an offspring if present
     *
     * @param c     value of offspring to retrieve
     * @return      the offspring node, if exists else null
     */
    public TrieNode getOffspring(char c) {
        c = Character.toLowerCase(c);   // Ensures trie remains lowercase
        int pos = c - 'a';  // Get value's position in alphabet (minus 1)

        return offspring[pos];
    }

    /**
     * Creates a new child node for a given value
     * 
     * @param c     value to add as offspring
     */
    public void setOffspring(char c) {
        c = Character.toLowerCase(c);   // Ensures trie remains lowercase
        int pos = c - 'a';  // Get value's position in alphabet (minus 1)
        
        offspring[pos] = new TrieNode(c);
    }
}
