package com.mdc.markovpoetry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Main on 9/3/16.
 */
public class InteractiveSession {
    int c = 5;
    Database database;
    public InteractiveSession(Database database){
        this.database = database;
    }

    ArrayList<String> chain = new ArrayList<>();

    public void interact(){
        HashMap<Integer, String> choices = new HashMap<>();
        Scanner in = new Scanner(System.in);
        p("Enter choice -1 at any time to restart.");
        if(chain.isEmpty()){
            int i = 0;
            while(i < database.stems.size() && i < c){
                p(i + ": " + database.stems.get(i).readable());
                choices.put(i, database.stems.get(i).readable());
                i++;
            }
            p(Main.ANSI_RED + (i+1) + ": " + database.getMostCommonStem().readable());
            choices.put(i+1, database.getMostCommonStem().readable());
            int choice = in.nextInt();
            chain.add(choices.get(choice).split(" ")[0]); // sketch I know
            chain.add(choices.get(choice).split(" ")[1]);
        }
        choices.clear();
        while(true){
            choices.clear();
            printChain();
            List<String> possibilities = database.getPossibleWords(getCurrentState());
            int i = 0;
            while(i < possibilities.size() && i < c){
                p(i + ": " + possibilities.get(i));
                choices.put(i, possibilities.get(i));
                i++;
            }
            p(Main.ANSI_RED + (i+1) + ": " + database.getMostCommonStem().readable());
            choices.put(i+1, database.getMostCommonStem().readable());
            int choice = in.nextInt();
            if(choice == -1){
                start(database);
                return;
            }
            chain.add(choices.get(choice));
        }
    }

    public Bigram getCurrentState(){
        return new Bigram(chain.get(chain.size() - 2), chain.get(chain.size() - 1));
    }

    public static void start(Database database){
        new InteractiveSession(database).interact();
    }

    public void printChain(){
        String together = "";
        for(String s : chain){
            together += s + " ";
        }
        p(Main.ANSI_PURPLE + together.trim() + "...");
    }

    public static void p(String s){
        System.out.println(Main.ANSI_CYAN + s + Main.ANSI_RESET); // because i'm lazy
    }


}
