package com.mdc.markovpoetry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by Main on 9/1/16.
 */
public class Database {
    HashMap<String, HashMap<String, List<String>>> database = new HashMap<>(); // our database tree-ish system. I know there are better ways to implement this, but this one is so fast. If you've got a better way, PR or tell me.
    // shady binary search tree implementation

    ArrayList<Bigram> stems = new ArrayList<>(); // our lovely stems

    /**
     * Insert word into the database as the result of bigram.
     * @param bigram the bigram stem
     * @param word the word to insert
     */
    public void insert(Bigram bigram, String word){ // i'm not going to comment the below because it's pretty self explanitory.
        if(database.containsKey(bigram.getW1())){
            if(database.get(bigram.getW1()).containsKey(bigram.getW2())){
                database.get(bigram.getW1()).get(bigram.getW2()).add(word);
            }else{
                ArrayList<String> words = new ArrayList<>();
                words.add(word);
                database.get(bigram.getW1()).put(bigram.getW2(), words);
            }
        }else{
            ArrayList<String> words = new ArrayList<>();
            words.add(word);
            HashMap<String, List<String>> secondDimensionalMap = new HashMap<>();
            secondDimensionalMap.put(bigram.getW2(), words);
            database.put(bigram.getW1(), secondDimensionalMap);
        }

        if(bigram.getW1().equals("PERIOD")){ //okay, I'll comment this. If the bigram w2 is PERIOD, the next words are likely the beginning of a new sentence, and therefore would make good stems.
            stems.add(new Bigram(bigram.getW2(), word));
        }
        // gotta love Java
    }

    /**
     * Print the database info.
     */
    public void printDatabaseInfo(){
        p("Number of level 1 keys: " + database.size());
        int level2 = 0;
        int total = 0;
        for(HashMap<String, List<String>> maps : database.values()){
            level2 += maps.size();
            for(List<String> sl : maps.values()){
                total += sl.size();
            }
        }
        p("Number of level 2 keys: " + level2);
        p("Total number of stem-value pairs: " + total);
        p("Number of root stems: " + stems.size());
    }

    Bigram mostCommonStem = null;

    // yeah. Load most common stem.
    public void loadMostCommonStem(){
        HashMap<Long, Integer> stemsLoaded = new HashMap<>();
        HashMap<Long, Bigram> refindIndex = new HashMap<>(); // so that it's easy to get the correct Bigram back at the end
        for(Bigram b : stems){
            if(stemsLoaded.containsKey(b.id())){
                stemsLoaded.put(b.id(), stemsLoaded.get(b.id()) + 1);
            }else{
                stemsLoaded.put(b.id(), 1);
                refindIndex.put(b.id(), b);
            }
        }
        long largest = -1;
        int amount = 0;
        for(long l : stemsLoaded.keySet()){
            if(stemsLoaded.get(l) > amount){
                largest = l;
                amount = stemsLoaded.get(l);
            }
        }
        mostCommonStem = refindIndex.get(largest);
    }

    public String getMostLikelyFollower(Bigram bigram){
        ArrayList<String> words = new ArrayList<>();
        boolean cont = true;
        if(database.containsKey(bigram.getW1())) {
            if (database.get(bigram.getW1()).containsKey(bigram.getW2())) {
                words.addAll(database.get(bigram.getW1()).get(bigram.getW2()));
                cont = false;
            }
        }
        if(cont) {
            for (HashMap<String, List<String>> tree1 : database.values()) {
                if (tree1.containsKey(bigram.getW2())) {
                    words.addAll(tree1.get(bigram.getW2()));
                }
            }
        }
        HashMap<String, Integer> commons = new HashMap<>();
        for(String s : words){
            if(commons.containsKey(s)){
                commons.put(s, commons.get(s) + 1);
            }else{
                commons.put(s, 1);
            }
        }
        String mostCommonWord = null;
        int amount = 0;
        for(String s : commons.keySet()){
            if(commons.get(s) > amount){
                mostCommonWord = s;
            }
        }
        return mostCommonWord;
    }

    public Bigram getMostCommonStem(){
        if(mostCommonStem == null){
            loadMostCommonStem();
        }
        return mostCommonStem;
    }

    public Bigram getRandomStem(){
        Random random = new Random();
        return stems.get(random.nextInt(stems.size()));
    }

    public void printDatabase(){
        for(String w1 : database.keySet()){
            for(String w2 : database.get(w1).keySet()){
                for(String s : database.get(w1).get(w2)) {
                    p(w1 + " " + w2 + " -> " + s);
                }
            }
        }
        for(Bigram b : stems){
            p("(stem) " + b.getW1() + " " + b.getW2());
        }
    }

    public HashMap<String, HashMap<String, List<String>>> getDatabase() {
        return database;
    }

    public ArrayList<Bigram> getStems() {
        return stems;
    }

    public List<String> getPossibleWords(Bigram bigram){
        if(database.containsKey(bigram.getW1())) {
            if (database.get(bigram.getW1()).containsKey(bigram.getW2())) {
                return database.get(bigram.getW1()).get(bigram.getW2());
            }
        }
        /*
        Okay, this is where shit gets weird. If there is no state in the database that
        matches the given bigram, then our markov model falls apart. My idea (don't know
        if this has been done before) is to "deflate the state" in a way-- query only words
        of bigram.w2? It's the best we can do (not actually, just the simplest) to ensure
        that there will be SOMETHING. If no bigram.w2 exists, then I think we are... uh...
         */
        ArrayList<String> words = new ArrayList<>();
        for(HashMap<String, List<String>> tree1 : database.values()){
            if(tree1.containsKey(bigram.getW2())){
                words.addAll(tree1.get(bigram.getW2()));
            }
        }
        return words;
    }

    public static void p(String s){
        System.out.println(Main.ANSI_BLUE + "database: " + s + Main.ANSI_RESET); // because i'm lazy
    }
}
