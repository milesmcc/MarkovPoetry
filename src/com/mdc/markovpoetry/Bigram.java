package com.mdc.markovpoetry;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Main on 9/1/16.
 */
public class Bigram {
    public Bigram(String word1, String word2){
        w1 = word1;
        w2 = word2;
    }

    public long id(){
        // this is useful for quick comparisons of equivalency
        long val = 1;
        for(char c : w1.toCharArray()){
            val *= c;
        }
        for(char c : w2.toCharArray()){
            val *= c;
        }
        return val;
    }

    private String w1;
    private String w2;
    public String[] getWords(){
        String[] words = {w1, w2};
        return words;
    }

    public String getW1(){
        return w1;
    }

    public String getW2() {
        return w2;
    }

    public String readable() { return w1 + " " + w2; }

    public static Bigram getCurrentState(String string){
        return getCurrentState((ArrayList)Arrays.asList(LanguageUtility.tokenize(string)));
    }

    public static Bigram getCurrentState(ArrayList<String> chain){
        return new Bigram(chain.get(chain.size() - 2), chain.get(chain.size() - 1));
    }

}
