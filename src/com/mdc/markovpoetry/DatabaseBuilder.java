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
    File source;

    public DatabaseBuilder(File source){
        this.source = source;
    }

    public Database buildDatabase() {
        try {
            try (Stream<String> stream = Files.lines(Paths.get(source.getAbsolutePath()))) {
                stream.forEach(line -> process(line));
            }
            Database database = new Database();
            for(int i = 2 ; i < readDataByWord.size() ; i++){
                Bigram bigram = new Bigram(readDataByWord.get(i - 2), readDataByWord.get(i - 1));
                database.insert(bigram, readDataByWord.get(i));
            }
            return database;
        }catch(Exception e){
            e.printStackTrace();
            // Yes I know this is bad practice, but this isn't a production program. Just a proof of concept.
        }
        return null;
    }

    ArrayList<String> readDataByWord = new ArrayList<>();
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
