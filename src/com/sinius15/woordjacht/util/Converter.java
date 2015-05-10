package com.sinius15.woordjacht.util;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by Sinius on 8-5-2015.
 * Converts word files.
 */
public class Converter {

    char[] letters = "qwertyuiopasdfghjklzxcvbnm".toCharArray();
    private static HashMap<Character, ArrayList<String>> filteredWordLists = new HashMap<>(26);

    public Converter(){
        for(char a : letters){
            filteredWordLists.put(a, new ArrayList<String>());
        }

        for(String line : getLines("woordenlijst.txt")){
            line = line.trim().toLowerCase();
            if(line.contains(" ") || line.contains("-") || line.contains("'") || line.contains(".") || line.length() < 3)
                continue;
            char starChar = line.charAt(0);
            ArrayList<String> goedewoordenlijst = filteredWordLists.get(starChar);
            if(goedewoordenlijst != null){
                goedewoordenlijst.add(line);
            }
        }
        saveFiles();
    }



    private void saveFiles(){
        for(char key : filteredWordLists.keySet()){
            ArrayList<String> woorden = filteredWordLists.get(key);
            try {
                PrintWriter out = new PrintWriter("U:\\"+key+"Words.txt", "UTF-8");
                woorden.forEach(out::println);

            } catch (FileNotFoundException | UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args){
        new Converter();
    }

    public static ArrayList<String> getLines(String file){
        ArrayList<String> words = new ArrayList<>();
        Scanner c = new Scanner(Dictionary.class.getResourceAsStream(file));
        while(c.hasNextLine()){
            words.add(c.nextLine());
        }
        c.close();
        return words;
    }

}
