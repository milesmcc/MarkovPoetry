package com.mdc.markovpoetry;

/**
 * Created by Main on 9/1/16.
 */
public class LanguageUtility {
    public static String[] tokenize(String s){
        s = stripDoubleSpaces(s);
        return s.split(" ");
    }

    public static String verbosePunctuation(String s){
        s = s.replace(".", " PERIOD ");
        s = s.replace(",", " COMMA ");
        s = s.replace(":", " COLON ");
        s = s.replace("\"", ""); // dont want this shit
        s = s.replace("(", ""); // dont want this shit
        s = s.replace(")", ""); // dont want this shit
        s = s.replace("?", " QUESTION_MARK ");
        s = s.replace("!", " EXCLAMATION ");
        return stripDoubleSpaces(s);
    }

    public static String stripDoubleSpaces(String s){
        s = s.replace("\n", "");
        while(s.contains("  ")){
            s = s.replace("  ", " ");
        }
        return s;
    }
}
