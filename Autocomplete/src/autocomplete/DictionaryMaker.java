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
 ***************************************************************************** */
package autocomplete;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.Set;

/**
 *
 * @author Ali Jarjis
 */
public class DictionaryMaker {

    /**
     * Stores a list of words and their frequency
     */
    private LinkedHashMap<String, Integer> dictionary;

    /**
     * Constructor for a DictionaryMaker
     */
    public DictionaryMaker() {
        dictionary = new LinkedHashMap<>();
    }

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
        sc.useDelimiter(" |,");
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
     */
    public void formDictionary(ArrayList<String> words) {
        for (String w : words) {
            if (dictionary.containsKey(w)) {
                dictionary.put(w, dictionary.get(w) + 1);
            } else {
                dictionary.put(w, 1);
            }
        }
    }

    /**
     * Saves the dictionary to a file
     *
     * @throws java.io.IOException if failed to write to file
     */
    public void saveToFile() throws IOException {
        String file = "dictionaryOuput.txt";

        FileWriter fileWriter = new FileWriter(file);
        PrintWriter printWriter = new PrintWriter(fileWriter);

        /* Iterating through hashmap was implemented from
           http://www.java2novice.com/java-collections-and-util
                 /linkedhashmap/read/                           */
        Set<String> keys = this.dictionary.keySet();
        for(String k : keys){
            printWriter.println(k + ", " + this.dictionary.get(k));
        }

        printWriter.close();

    }

    /**
     * Retrieves the dictionary that has been formed
     *
     * @return the dictionary that was made
     */
    public HashMap<String, Integer> getDictionary() {
        return this.dictionary;
    }

    public static void main(String[] args) throws Exception {
        DictionaryMaker df = new DictionaryMaker();
        ArrayList<String> in = readWordsFromCSV("testDocument.txt");

        df.formDictionary(in);
        
        df.saveToFile();
    }
}
