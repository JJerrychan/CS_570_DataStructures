package hw6;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * An anagram is word or phrase formed by rearranging the letters of a different word or phrase, typically using all the original letters exactly once.
 *
 * @author Junjie Chen 10476718
 */
public class Anagrams {
    final Integer[] primes;
    Map<Character, Integer> letterTable;
    Map<Long, ArrayList<String>> anagramTable;

    /**
     * Constructor method for initialize the primes and the anagram table.
     */
    public Anagrams() {
        primes = new Integer[]{2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101};
        anagramTable = new HashMap<Long, ArrayList<String>>();
        buildLetterTable();
    }

    public static void main(String[] args) {
        Anagrams a = new Anagrams();
        final long startTime = System.nanoTime();
        try {
            a.processFile("words_alpha.txt");
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        ArrayList<Map.Entry<Long, ArrayList<String>>> maxEntries = a.getMaxEntries();
        final long estimatedTime = System.nanoTime() - startTime;
        final double seconds = ((double) estimatedTime / 1000000000);
        System.out.println(" Time : " + seconds);
        System.out.println(" List of max anagrams : " + maxEntries);
    }

    /**
     * build the hash table letterTable.
     */
    private void buildLetterTable() {
        letterTable = new HashMap<Character, Integer>();
        Character[] letter = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        for (int i = 0; i < 26; i++) {
            letterTable.put(letter[i], primes[i]);
        }
    }

    /**
     * compute the hash code of the string s, and add this word to the hash table anagramTable.
     *
     * @param s the string that will be hashed and added into hash table
     */
    private void addWord(String s) {
        long code = myHashCode(s);
        ArrayList<String> newWord;
        if (anagramTable.get(code) != null) newWord = anagramTable.get(code);
        else newWord = new ArrayList<String>();
        newWord.add(s);
        anagramTable.put(code, newWord);
    }

    /**
     * computes a hash code of the strings.
     *
     * @param s the string that will be hashed
     * @return a code after hash
     */
    private Long myHashCode(String s) {
        long code = 1;
        for (int i = 0; i < s.length(); i++) {
            code = code * letterTable.get(s.charAt(i));
        }
        return code;
    }

    /**
     * receives the name of a text file containing words, one per line, and builds the hash table anagramTable
     *
     * @param s the name of a text file
     * @throws IOException
     */
    private void processFile(String s) throws IOException {
        FileInputStream fstream = new FileInputStream(s);
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
        String strLine;
        while ((strLine = br.readLine()) != null) {
            this.addWord(strLine);
        }
        br.close();
    }

    /**
     * return the entries in the anagramTable that have the largest number of anagrams
     *
     * @return the largest number of anagrams
     */
    private ArrayList<Map.Entry<Long, ArrayList<String>>> getMaxEntries() {
        int maximum = 0;
        ArrayList<Map.Entry<Long, ArrayList<String>>> entry = new ArrayList<Map.Entry<Long, ArrayList<String>>>();
        for (Map.Entry<Long, ArrayList<String>> temp : anagramTable.entrySet()) {
            if (temp.getValue().size() > maximum) {
                maximum = temp.getValue().size();
                entry = new ArrayList<Map.Entry<Long, ArrayList<String>>>();
                entry.add(temp);
            } else if (temp.getValue().size() == maximum) entry.add(temp);
        }
        return entry;
    }

}
