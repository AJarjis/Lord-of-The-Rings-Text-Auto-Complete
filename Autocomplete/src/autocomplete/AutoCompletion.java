/*****************************************************************************

File        : AutoCompletion.java

Date        : 15-Jan-2018

Description : A class for a prototype word completion program.

Author      : Ali Jarjis

******************************************************************************/

package autocomplete;

import static autocomplete.DictionaryMaker.readWordsFromCSV;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeMap;

/**
 *
 * @author Ali Jarjis
 */
public class AutoCompletion {
    /**
     * Adds a dictionary of words with their frequency to a trie
     * 
     * @param dictionary    dictionary to add
     * @return              trie formed from words in dictionary
     */
    public static Trie addDictionary(TreeMap<String, Integer> dictionary) {
        Trie dictionaryTrie = new Trie();
        
        Set<String> keys = dictionary.keySet();
        
        // Adds each element in dictionary to trie
        for(String k : keys){
            // TODO: change so it sets the frequency, rather than loop
            for (int i = 0; i < dictionary.get(k); i++) {
                dictionaryTrie.add(k);
            }
        }
        
        return dictionaryTrie;
    }

    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<String> lotr = readWordsFromCSV("lotr.csv");
        
        TreeMap dictionary = DictionaryMaker.formDictionary(lotr);
        
        Trie treebeard = addDictionary(dictionary);
        
        ArrayList<String> lotrQueries = readWordsFromCSV("lotrQueries.csv");
        
        for(String q : lotrQueries){
            Trie queryTrie = treebeard.getSubTrie(q);
            //TODO: get all words from subtrie and store in linkedhashmap
            //queryTrie.getAllWords(queryTrie.root, word, lotr);
        }
    }

}
