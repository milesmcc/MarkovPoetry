package com.mdc.markovpoetry;

/**
 * Created by Main on 9/1/16.
 */
public class LanguageUtility {
    /**
     * A simple naive tokenizer that splits string s into its words.
     * Strips double spaces.
     * @param s the string to tokenize
     * @return an array of Strings, each of which is a word from string s
     */
    public static String[] tokenize(String s){
        s = stripDoubleSpaces(s);
        return s.split(" ");
    }

    /**
     * Replace punctuation with a verbose-er version.
     * @param s the string to verbosify
     * @return the verbosified string
     */
    public static String verbosePunctuation(String s){
        s = s.replace(".", " PERIOD ");
        s = s.replace(",", " COMMA ");
        s = s.replace(":", " COLON ");
        s = s.replace("\"", ""); // dont want this shit
        s = s.replace("(", ""); // dont want this shit
        s = s.replace(")", ""); // dont want this shit
        s = s.replace("?", " QUESTION_MARK ");
        s = s.replace("!", " EXCLAMATION ");
        return stripDoubleSpaces(s); // ...we do it again. Can't be too sure.
    }

    /**
     * Strip all double, triple, etc spaces, replace them with single spaces
     * @param s the string to unspace
     * @return the unspaced string
     */
    public static String stripDoubleSpaces(String s){
        s = s.replace("\n", "");
        while(s.contains("  ")){
            s = s.replace("  ", " ");
        }
        return s;
    }
}
