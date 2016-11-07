package com.mdc.markovpoetry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Main on 9/4/16.
 */
public class Dibol {
    private Database database;

    public Dibol(Database database) {
        this.database = database;
    }

    public double compare(String text) {
        String[] split = LanguageUtility.tokenize(text);
        /*
        Process:
        if the database contains the stem, add 2
        for every word that matches, add a match
        Divide matches by
         */
        if (split.length < 3) {
            return 0;
        }


        int match = 0;
        ArrayList<String> chain = new ArrayList<>();
        chain.add(chain.get(0));
        chain.add(chain.get(1));
        if (database.getStemEquivs().contains(Bigram.getCurrentState(chain).id())) {
            match += 2;
        }
        for (int i = 2; i < split.length; i++) {
            String word = split[i];
            List<String> options = database.getPossibleWords(Bigram.getCurrentState(chain));
            System.out.println(word + options.size());
            for (String option : options) {
                if (word.equalsIgnoreCase(option)) {
                    match += 1;
                }
            }
            chain.add(word);
        }
        return ((Double.valueOf(match) / Double.valueOf(split.length) * (Double.valueOf(match) / database.getTotalValues())));
    }
}
