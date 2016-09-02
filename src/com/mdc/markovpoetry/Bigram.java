package com.mdc.markovpoetry;

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
}
