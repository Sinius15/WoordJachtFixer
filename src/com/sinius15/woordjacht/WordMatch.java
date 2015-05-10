package com.sinius15.woordjacht;

import com.sinius15.woordjacht.util.Rowcol;

import java.util.Arrays;

/**
 * Created by Sinius on 9-5-2015.
 * Reperesents a found word.
 */
public class WordMatch {

    private final String word;
    private final Rowcol[] path;

    public WordMatch(String word, Rowcol[] path) {
        this.word = word;
        this.path = path;
    }

    public String getWord() {
        return word;
    }

    public Rowcol[] getPath() {
        return path;
    }

    @Override
    public String toString() {
        return "WordMatch{" +
                "word='" + word + '\'' +
                ", path=" + Arrays.toString(path) +
                '}';
    }
}
