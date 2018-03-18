/** ***************************************************************************
 *
 * File        : DictionaryMaker.java
 *
 * Date        : 15-Jan-2018
 *
 * Description : A class that forms a dictionary from a set of words.
 *
 * Author      : Ali Jarjis
 *
 ******************************************************************************/
package autocomplete;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

/**
 *
 * @author Ali Jarjis
 */
public class DictionaryMaker {
    public DictionaryMaker() {}

    /**
     * Reads all the words in a comma separated text document into an Array
     *
     * @param file address of file to read from
     * @return a list of words retrieved from the file
     *
     * @throws java.io.FileNotFoundException if file does not exist
     */
    public static ArrayList<String> readWordsFromCSV(String file)
            throws FileNotFoundException {
        Scanner sc = new Scanner(new File(file));
        sc.useDelimiter("[ |,\r\n]+");
        ArrayList<String> words = new ArrayList<>();
        String str;

        while (sc.hasNext()) {
            str = sc.next();
            str = str.trim();
            str = str.toLowerCase();
            words.add(str);
        }

        return words;
    }

    /**
     * Writes each item in a collection to a file
     *
     * @param c collection to write
     * @param file location where to write to
     * @throws IOException if failed to write to file
     */
    public static void saveCollectionToFile(Collection<?> c, String file)
            throws IOException {
        FileWriter fileWriter = new FileWriter(file);
        PrintWriter printWriter = new PrintWriter(fileWriter);

        for (Object w : c) {
            printWriter.println(w.toString());
        }

        printWriter.close();
    }

    /**
     * Forms a dictionary (sorted alphabetically) from a list of words
     *
     * @param words list of words to form dictionary from
     * @return      hash table of words and their frequency
     */
    public static TreeMap formDictionary(ArrayList<String> words) {
        TreeMap<String, Integer> dictionary = new TreeMap();
        
        for (String w : words) {
            if (dictionary.containsKey(w)) {
                dictionary.put(w, dictionary.get(w) + 1);
            } else {
                dictionary.put(w, 1);
            }
        }
        
        return dictionary;
    }

    /**
     * Saves the dictionary to a file
     *
     * @throws java.io.IOException if failed to write to file
     */
    public static void saveToFile(TreeMap dictionary) throws IOException {
        String file = "dictionaryOuput.txt";
        
        FileWriter fileWriter = new FileWriter(file);
        PrintWriter printWriter = new PrintWriter(fileWriter);

        /* Iterating through hashmap was implemented from
           http://www.java2novice.com/java-collections-and-util
                 /linkedhashmap/read/                           */
        Set<String> keys = dictionary.keySet();
        for(String k : keys){
            printWriter.println(k + ", " + dictionary.get(k));
        }

        printWriter.close();

    }

    public static void main(String[] args) throws Exception {
        ArrayList<String> in = readWordsFromCSV("testDocument.txt");
        
        TreeMap dictionary = DictionaryMaker.formDictionary(in);
        
        DictionaryMaker.saveToFile(dictionary);
    }
}
