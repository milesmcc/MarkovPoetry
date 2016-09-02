package com.mdc.markovpoetry;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String[] args) {

        if(args.length != 2){
            p("Usage: ... /full/path/to/trump.txt");
        }

        //File file = new File(args[0]);
        //boolean probabilityMode = Boolean.valueOf(args[1]);
        File file = new File("/Users/Main/Documents/spratt_data.txt");

        p("Source: " + file.getAbsolutePath());
        p("Creating database...");
        Database database = new DatabaseBuilder(file).buildDatabase();
        p("Database created.");
        //database.printDatabase();
        database.printDatabaseInfo();
        p("Generating markov chains to PERIOD from each stem...");
        Random random = new Random();
        for(Bigram stem : database.getStems()){
            ArrayList<String> chain = new ArrayList<>();
            chain.add(stem.getW1());
            chain.add(stem.getW2());
            boolean print = true;
            while(chain.get(chain.size() - 1).equals("PERIOD") != true) {
                Bigram state = new Bigram(chain.get(chain.size() - 2), chain.get(chain.size() - 1));
                List<String> possibilities = database.getPossibleWords(state);
                if (possibilities.size() > 0) {
                    chain.add(possibilities.get(random.nextInt(possibilities.size()))); // random markov, but still follows adheres to probability because higher frequency state-result pairs will have higher quantity within the possibilities pool.
                }else{
                    String chain_string = "";
                    for(String s : chain){
                        chain_string += s + " ";
                    }
                    p(ANSI_YELLOW + chain_string);
                    print = false;
                    break;
                }
            }
            if(print) {
                String chain_string = "";
                for (String s : chain) {
                    chain_string += s + " ";
                }
                p(chain_string);
            }
        }
        database.printDatabaseInfo();
    }

    public static void p(String s){
        System.out.println(ANSI_GREEN + "main: " + s + ANSI_RESET); // because i'm lazy
    }

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
}
