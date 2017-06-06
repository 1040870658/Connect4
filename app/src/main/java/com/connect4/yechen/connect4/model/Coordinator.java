package com.connect4.yechen.connect4.model;

/**
 * Created by yechen on 2016/10/27.
 */

public class Coordinator {
    int row;
    int col;
    Coordinator(int row, int col){
        Coordinator.this.col = col;
        Coordinator.this.row = row;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

}