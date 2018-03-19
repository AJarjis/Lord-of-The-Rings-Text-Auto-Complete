/** ***************************************************************************
 *
 * File        : AutoCompletionTrieNode.java
 *
 * Date        : 15-Jan-2018
 *
 * Description : A class to model a trie node in a trie data structure.
 *
 * Author      : Ali Jarjis
 *
 ******************************************************************************/
package autocomplete;

/**
 *
 * @author Ali Jarjis
 */
public class AutoCompletionTrieNode {
    Character value;
    AutoCompletionTrieNode[] offspring;
    boolean isWord;
    int frequency;
    
    /**
     * Constructs a TrieNode with a given value
     *
     * @param character trienode's value
     */
    public AutoCompletionTrieNode(Character character) {
        this.value = character;
        this.offspring = new AutoCompletionTrieNode[26];
        this.isWord = false;
        this.frequency = 0;
    }

    /**
     * Recovers an offspring if present
     *
     * @param c     value of offspring to retrieve
     * @return      the offspring node, if exists else null
     */
    public AutoCompletionTrieNode getOffspring(char c) {
        c = Character.toLowerCase(c);   // Ensures trie remains lowercase
        int pos = c - 'a';  // Get value's position in alphabet (minus 1)

        return offspring[pos];
    }

    /**
     * Creates a new child node for a given value
     * 
     * @param c     value to add as offspring
     * @return      true if newly created offspring, false if already existed
     */
    public boolean setOffspring(char c) {
        c = Character.toLowerCase(c);   // Ensures trie remains lowercase
        int pos = c - 'a';  // Get value's position in alphabet (minus 1)
        
        if (offspring[pos] == null) {
            offspring[pos] = new AutoCompletionTrieNode(c);
            return true;
        } else {
            return false;
        }
        
    }
}
