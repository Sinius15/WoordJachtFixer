package com.sinius15.woordjacht;

import com.sinius15.woordjacht.util.Rowcol;
import com.sinius15.woordjacht.util.Dictionary;

import java.util.ArrayList;

/**
 * Created by Sinius on 8-5-2015.
 * The Main class
 */
public class WoordJachtFixer {

    private Dictionary dictionary;
    private WoordjachtFrame frame;

    private ArrayList<WordMatch> found = new ArrayList<>();

    private ArrayList<JachterThread> zoekers = new ArrayList<>();

    private long startTime;

    private final static int MATRIX_WIDTH = 4;
    private final static int MATRIX_HEIGHT = 4;
    private final static int MAX_WORD_LENGTH = MATRIX_HEIGHT*MATRIX_WIDTH;


    public WoordJachtFixer(){
        frame = new WoordjachtFrame(MATRIX_HEIGHT, MATRIX_WIDTH, this);
        dictionary = new Dictionary();
    }

    /**
     * Holds thread
     * @param matrix the matrix to search
     */
    public ArrayList<WordMatch> doSearch(char[][] matrix) {

        startTime = System.currentTimeMillis();

        Rowcol.colMax = matrix.length;
        Rowcol.rowMax = matrix[0].length;
        found.clear();
        zoekers.clear();

        String avalablechars = "";
        for(int row = 0; row< matrix[0].length; row++) {
            for (int col = 0; col < matrix.length; col++) {
                avalablechars +=matrix[row][col];
            }
        }
        this.dictionary.setAvalableChars(avalablechars);

        for(int row = 0; row< matrix[0].length; row++){
            for(int col = 0; col<matrix.length; col++){
                JachterThread zerozero = new JachterThread(this, matrix, new Rowcol(row, col), MAX_WORD_LENGTH, dictionary);
                zerozero.start();
                zoekers.add(zerozero);
            }
        }

        while(!isFinished()){
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        printFinishInfo();

        return found;
    }

    private void printFinishInfo() {
        int words = 0;
        for(JachterThread zoeker : zoekers)
            words += zoeker.getCheckedWords();
        System.out.println("Woorden gechecked : " + String.format("%,d", words));
        System.out.println("Woorden gevonden: " + found.size());
        System.out.println("Run Tijd: " + ((System.currentTimeMillis() - startTime) / 1000f) + " seconden");
        System.out.println("==========");
        found.sort((o1, o2) -> {
            if (o1.getWord().length() > o2.getWord().length()) {
                return -1;
            } else if (o1.getWord().length() < o2.getWord().length()) {
                return 1;
            } else {
                return 0;
            }
        });
    }

    private boolean isFinished() {
        boolean isFinished = true;
        for(JachterThread zoeker : zoekers){
            if(zoeker.isBusy())
                isFinished = false;
        }
        return isFinished;
    }

    public void addFound(WordMatch match){
        found.add(match);
    }

    public static void main(String[] args){
        new WoordJachtFixer();
    }
}
