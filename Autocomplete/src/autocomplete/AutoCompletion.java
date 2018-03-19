/** ***************************************************************************
 *
 * File        : AutoCompletion.java
 *
 * Date        : 15-Jan-2018
 *
 * Description : A class for a prototype word completion program.
 *
 * Author      : Ali Jarjis
 *
 ***************************************************************************** */
package autocomplete;

import static autocomplete.DictionaryMaker.readWordsFromCSV;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
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
     * @param dictionary dictionary to add
     * @return trie formed from words in dictionary
     */
    public static AutoCompletionTrie addDictionary(TreeMap<String, 
            Integer> dictionary) {
        AutoCompletionTrie dictionaryTrie = new AutoCompletionTrie();

        Set<String> keys = dictionary.keySet();

        // Adds each element in dictionary to trie
        for (String k : keys) {
            dictionaryTrie.add(k, dictionary.get(k));
        }

        return dictionaryTrie;
    }

    /**
     * Retrieves top three words from trie that match given prefix
     *
     * @param prefix prefix queries to search for
     * @param trie trie to search for most frequent words
     * @return list of most frequent words matching the prefix from the trie
     */
    public static LinkedHashMap getTopThreeWords(String prefix, 
            AutoCompletionTrie trie) {
        // Create sub trie from query
        AutoCompletionTrie queryTrie = trie.getSubTrie(prefix);

        // Get all words from queried tree
        StringBuilder word = new StringBuilder();
        HashMap<String, Integer> allWords = new HashMap();

        allWords = queryTrie.getAllWords(queryTrie.root, word, allWords);

        double totalFrequency = 0;

        Map.Entry<String, Integer>[] topThreeWords = new Map.Entry[3];

        // Loops through words retrieved, storing the three most frequent in an 
        // array, ordered by frequency (decreasing).
        for (Map.Entry<String, Integer> pair : allWords.entrySet()) {
            String key = pair.getKey();
            int value = pair.getValue();

            totalFrequency += value;

            // Finds three most frequent words, by comparing a word's frequency
            // with the current three maximums
            if (topThreeWords[0] == null 
                    || value > topThreeWords[0].getValue()) {
                // New word becomes highest frequent, pushing word that was most 
                // frequent to second place, which in turn moves to third place
                topThreeWords[2] = topThreeWords[1];
                topThreeWords[1] = topThreeWords[0];
                topThreeWords[0] = pair;
            } else if (topThreeWords[1] == null
                    || value > topThreeWords[1].getValue()) {
                topThreeWords[2] = topThreeWords[1];
                topThreeWords[1] = pair;
            } else if (topThreeWords[2] == null
                    || value > topThreeWords[2].getValue()) {
                topThreeWords[2] = pair;
            }
        }

        LinkedHashMap topThree = new LinkedHashMap();

        // Ensures words in array are formatted properly, i.e have attached 
        // prefix and display probability rather than frequency
        for (int i = 0; i < topThreeWords.length; i++) {
            if (topThreeWords[i] != null) {
                String autoWord = prefix + topThreeWords[i].getKey();
                Double probability
                        = (double) topThreeWords[i].getValue() / totalFrequency;

                topThree.put(autoWord, probability);

            }
        }

        return topThree;

    }

    /**
     * Prints a hashmap to standard output
     * 
     * @param map   hashmap to print to standard output 
     */
    public static void printToStandard(LinkedHashMap<String, Double> map) {
        for (Map.Entry<String, Double> pair : map.entrySet()) {
            DecimalFormat decimalFormat = new DecimalFormat("#.####");

            System.out.println(pair.getKey()
                    + "(probability "
                    + decimalFormat.format(pair.getValue()) + ")");
        }

        System.out.println("");
    }

    public static void main(String[] args) throws FileNotFoundException, 
            IOException {
        // Create dictionary from file and add to trie
        ArrayList<String> lotr = readWordsFromCSV("lotr.csv");
        ArrayList<String> lotrQueries = readWordsFromCSV("lotrQueries.csv");

        TreeMap dictionary = DictionaryMaker.formDictionary(lotr);

        AutoCompletionTrie treebeard = addDictionary(dictionary);

        String file = "lotrMatches.csv";

        FileWriter fileWriter = new FileWriter(file);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        DecimalFormat decimalFormat = new DecimalFormat("#.####");

        // For each query print the most frequent matches to standard output 
        // as well as a file
        for (String q : lotrQueries) {
            LinkedHashMap topThree = getTopThreeWords(q, treebeard);

            printToStandard(topThree);
            
            // Code below just prints the matches to file
            Set<String> keys = topThree.keySet();

            printWriter.print(q);

            for (String k : keys) {
                printWriter.print(", " + k + ", " 
                        + decimalFormat.format(topThree.get(k)));
            }
            
            printWriter.println("");
        }
        
        printWriter.close();
    }
}
