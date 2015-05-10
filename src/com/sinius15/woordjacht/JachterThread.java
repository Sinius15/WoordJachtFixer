package com.sinius15.woordjacht;

import com.sinius15.woordjacht.util.Rowcol;
import com.sinius15.woordjacht.util.Dictionary;

import java.util.ArrayList;

/**
 * Created by Sinius on 8-5-2015.
 * A thread of every block
 */
public class JachterThread extends Thread {

    private WoordJachtFixer fixer;
    private ArrayList<String> myDictionary;

    private char[][] matrix;
    private Rowcol startPoint;

    private int maxDepth;
    private int checkedWords = 0;
    private boolean busy = false;

    private ArrayList<Rowcol> path = new ArrayList<>();

    public JachterThread(WoordJachtFixer fixer, char[][] matrix, Rowcol startPoint, int maxDepth, Dictionary dictionary) {
        this.fixer = fixer;
        this.matrix = matrix;
        this.startPoint = startPoint;
        this.maxDepth = maxDepth;
        this.myDictionary = dictionary.getFilteredWordList(matrix[startPoint.row][startPoint.col]);
    }

    @Override
    public void run() {
        busy = true;
        path.add(startPoint);
        nextLevel(startPoint);
        busy = false;
    }

    private void nextLevel(Rowcol start){
        if(path.size() >= maxDepth)
            return;
        for(Rowcol rowcol : getPointsAround(start)){
            if(!rowcol.isValid() || path.contains(rowcol))
                continue;
            path.add(rowcol);
            String word = getCurWord();
            if(path.size() >= 3 && myDictionary.contains(word)) {
                fixer.addFound(new WordMatch(word, path.toArray(new Rowcol[path.size()])));
            }
            checkedWords++;

            nextLevel(rowcol);

            path.remove(path.size()-1);
        }
    }

    private String getCurWord() {
        String out = "";
        for(Rowcol rowcol : path){
            out += this.matrix[rowcol.row][rowcol.col];
        }
        return out;
    }

    private Rowcol[] getPointsAround(Rowcol p) {
        return new Rowcol[]{
                new Rowcol(p.row+1, p.col),
                new Rowcol(p.row+1, p.col+1),
                new Rowcol(p.row,   p.col+1),
                new Rowcol(p.row-1, p.col+1),
                new Rowcol(p.row-1, p.col),
                new Rowcol(p.row-1, p.col-1),
                new Rowcol(p.row,   p.col-1),
                new Rowcol(p.row+1, p.col-1)
        };
    }

    public int getCheckedWords() {
        return checkedWords;
    }

    public boolean isBusy() {
        return busy;
    }
}
