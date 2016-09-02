package com.mdc.markovpoetry;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

/**
 * Created by Main on 9/1/16.
 */
public class DatabaseBuilder {
    File source; // the file from which the markov model will be built

    public DatabaseBuilder(File source){
        this.source = source;
    }

    /**
     * Build a database from the source file.
     * @return a fully instantiated Database object with everything you need for your mind, body, and soul
     */
    public Database buildDatabase() {
        try {
            try (Stream<String> stream = Files.lines(Paths.get(source.getAbsolutePath()))) {
                stream.forEach(line -> process(line)); // process each line... beautiful lambdas.
            }
            Database database = new Database(); // instantiate a new database for insertion
            for(int i = 2 ; i < readDataByWord.size() ; i++){ // for each word... (starting at 2 because we need to have something to reference as the bigram stem)
                Bigram bigram = new Bigram(readDataByWord.get(i - 2), readDataByWord.get(i - 1)); // build the bigram stem
                database.insert(bigram, readDataByWord.get(i)); // and insert
            }
            return database; // give them the database
        }catch(Exception e){
            e.printStackTrace();
            // Yes I know this is bad practice, but this isn't a production program. Just a proof of concept.
        }
        return null;
    }

    ArrayList<String> readDataByWord = new ArrayList<>(); // temporary object containing every... single... word... in... order...
    private void process(String line){
        line = LanguageUtility.stripDoubleSpaces(line);
        line = LanguageUtility.verbosePunctuation(line);
        for(String s : LanguageUtility.tokenize(line)){
            readDataByWord.add(s);
        }
    }

    public static void p(String s){
        System.out.println(Main.ANSI_CYAN + "database builder: " + s + Main.ANSI_RESET); // because i'm lazy
    }
}
