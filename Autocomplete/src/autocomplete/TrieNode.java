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
    private Character character;
    private TrieNode[] offspring;
    private boolean isWord;
    
    /**
     * Constructs a TrieNode with a given character
     *
     * @param character trienode's character
     */
    public TrieNode(Character character) {
        this.character = character;
        this.offspring = new TrieNode[26];
        this.isWord = false;
    }
    
    /**
     * Gets character at this node
     *
     * @return the character at this node
     */
    public Character getCharacter() {
        return character;
    }

    /**
     * Recovers an offspring if present
     *
     * @param x     character of offspring to retrieve
     * @return      the offspring node, if exists else null
     */
    public TrieNode getOffspring(char x) {
        x = Character.toLowerCase(x);   // Ensures trie remains lowercase
        int pos = x - 'a';  // Get character's position in alphabet (minus 1)

        return offspring[pos];
    }

    /**
     * Creates a new child node for a given character
     * 
     * @param x     character to add as offspring
     */
    public void setOffspring(char x) {
        x = Character.toLowerCase(x);   // Ensures trie remains lowercase
        int pos = x - 'a';  // Get character's position in alphabet (minus 1)
        
        offspring[pos] = new TrieNode(x);
    }

    /**
     * Retrieves flag to indicate whether this node represents a complete 
     * word or not
     *
     * @return      true if node is word, else false
     */
    public boolean getIsWord() {
        return isWord;
    }

    /**
     * Changes flag to indicate whether this node represents a complete word 
     * or not
     * 
     * @param isWord  set true if node is word, else false
     */
    public void setIsWord(boolean isWord) {
        this.isWord = isWord;
    }
}
