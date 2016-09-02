package com.mdc.markovpoetry;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String[] args) {

        /*
        Make sure they are using this correctly.
         */
        if(args.length != 1){
            p("Usage: ... /full/path/to/data.txt");
            return;
        }

        File file = new File(args[0]);
        //File file = new File("/Users/Main/Documents/spratt_data.txt");

        p("Source: " + file.getAbsolutePath());
        p("Creating database...");
        Database database = new DatabaseBuilder(file).buildDatabase(); // build the database... this may take awhile.
        p("Database created.");
        //database.printDatabase(); // this pushes out a lot of data...
        database.printDatabaseInfo(); // this just pushes out metadata
        p("Generating markov chains to PERIOD from each stem...");
        p("----------------------------------------------------");
        Random random = new Random();
        for(Bigram stem : database.getStems()){ // for every stem in the database we create a sentence
            ArrayList<String> chain = new ArrayList<>();
            chain.add(stem.getW1()); // we add the first word of the stem to the chain
            chain.add(stem.getW2()); // and the second word
            boolean print = true; // this comes in handy later when we want to know whether or not the sentence is complete with a PERIOD at the end.
            while(chain.get(chain.size() - 1).equals("PERIOD") != true) { // keep on adding new words to the chain until the last word in the chain is PERIOD
                Bigram state = new Bigram(chain.get(chain.size() - 2), chain.get(chain.size() - 1)); // create a bigram of the current state, words 1 and 2 indexes behind the i
                List<String> possibilities = database.getPossibleWords(state); // get the possibilities out of the database
                if (possibilities.size() > 0) { // if there are possibilities
                    chain.add(possibilities.get(random.nextInt(possibilities.size()))); // random markov, but still follows adheres to probability because higher frequency state-result pairs will have higher quantity within the possibilities pool.
                }else{ // and if there aren't...
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
                m(chain_string);
            }
        }
        p("----------------------------------------------------");
        database.printDatabaseInfo(); // just so we know
    }

    public static void p(String s){
        System.out.println(ANSI_GREEN + "main: " + s + ANSI_RESET); // because i'm lazy
    }
    public static void m(String s){
        System.out.println(ANSI_PURPLE + "" + s + ANSI_RESET); // because i'm lazy
    }

    public static final String ANSI_RESET = "\u001B[0m"; //colors!
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
}
