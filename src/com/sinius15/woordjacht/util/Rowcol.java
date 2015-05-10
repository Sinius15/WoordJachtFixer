package com.sinius15.woordjacht.util;

/**
 * Created by Sinius on 8-5-2015.
 * Like point, but with row and col
 */
public class Rowcol {

    public static int rowMax;
    public static int colMax;

    public int row, col;

    public Rowcol(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public Rowcol(Rowcol rowcol){
        this.row = rowcol.row;
        this.col = rowcol.col;
    }

    public Rowcol(){
        this(0, 0);
    }

    public boolean isValid(){
        return row >= 0 && col >= 0 && row < rowMax && col < colMax;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rowcol rowcol = (Rowcol) o;

        return row == rowcol.row && col == rowcol.col;

    }

    @Override
    public int hashCode() {
        return 31 * row + col;
    }

    @Override
    public String toString() {
        return "Rowcol{" +
                "row=" + row +
                ", col=" + col +
                '}';
    }
}
