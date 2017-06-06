package com.connect4.yechen.connect4.model;

import android.widget.ImageButton;

/**
 * Created by yechen on 16/10/11.
 */

public class Board {
    public static final int PLAYER_1 = 0;
    public static final int PLAYER_2 = 1;
    public static final int EMPTY = -1;
    public static final int FULL = 2;
    public static final int WIN_1 = 3;
    public static final int WIN_2 = 4;
    public static final int WARNING = 5;
    public static final int WARNING_1 = 6;
    public static final int WARNING_2 = 7;
    private int count;
    private int row;
    private int col;
    private int[][] board;
    private int[][] record;
    private int[] current_position ;
    private int current_player;
    private ImageButton[][] imageButtons;

    public Board(int row,int col){
        this.row = row;
        this.col = col;
        this.count = 0;
        current_player = PLAYER_1;
        imageButtons = new ImageButton[row][col];
        board = new int[row][col];
        record = new int[row][col];
        current_position = new int[col];

        for(int j = 0;j != col;j++){
            current_position[j] = row;
            for(int i = 0;i != row;i ++){
                board[i][j] = EMPTY;
                record[i][j] = EMPTY;
            }
        }
    }

    public  ImageButton getImageButton(int row,int col){
        return imageButtons[row][col];
    }
    public void setImageButton(ImageButton imageButton,int row,int col){
        imageButtons[row][col]= imageButton;
    }
    public int getCount() {return  count;}

    public void setCount(int count){this.count = count;}

    public int getRow(){
        return row;
    }

    public int getCol(){
        return col;
    }

    public int getRecord(int row,int col){
        return record[row][col];
    }

    public void setRecord(int row,int col,int element){
        this.record[row][col] = element;
    }

    public int getElement(int row,int col) {
        return board[row][col];
    }

    public void setBoard(int row,int col,int element) {
        this.board[row][col] = element;
    }

    public int getCurrent_player() {
        return current_player;
    }

    public void setCurrent_player(int current_player) {
        this.current_player = current_player;
    }

    public int getCurrent_position(int col) {
        return current_position[col];
    }

    public void setCurrent_position(int col,int row) {
        this.current_position[col] = row;
    }
}
