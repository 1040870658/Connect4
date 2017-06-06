package com.connect4.yechen.connect4.presenter;

import android.os.Handler;
import android.os.Message;

import com.connect4.yechen.connect4.MainActivity;
import com.connect4.yechen.connect4.model.Board;
import com.connect4.yechen.connect4.model.WinnerPath;

/**
 * Created by yechen on 16/10/11.
 */

public class GamePresenter {
    private Board board;
    private WinnerPath winnerPath;
    private int pre_step;
    public static final int ROLLBACK_UNLOCKED = 0x00000009;
    public static final int START_STEP = -1;
    public static final int COMPLETED = 0x00000001;
    public static final int NORMAL = 0x00000002;
    public static final int ROLL_BACK = 0x00000003;
    public static final int COL_FULL = 0x00000004;
    public static final int Terminated = 0x00000005;
    public static final int TURN_PLAYER = 0x00000006;
    public static final int FINISH = 0x00000007;
    public static final int UNLOCKED = 0x00000008;
    public static final int GAME_DRAW = 0x00000010;
    private Handler handler;
    private int termination_count;

    public GamePresenter(Board board,Handler handler,WinnerPath winnerPath){
        this.board = board;
        this.handler = handler;
        this.winnerPath = winnerPath;
        pre_step = START_STEP;
        termination_count = MainActivity.ROW*MainActivity.COL;
    }

    public void turnPlayer(){
        if(board.getCurrent_player() == Board.PLAYER_1) {
            board.setCurrent_player(Board.PLAYER_2);
        }
        else {
            board.setCurrent_player(Board.PLAYER_1);
        }
    }

    public boolean putChess(int col){
        Message msg = new Message();
        msg.arg1 = col;
        msg.what = COL_FULL;
        int row = board.getCurrent_position(col);
        if(isFull(row)) {
            handler.sendMessage(msg);
            return false;
        }

        int player = board.getCurrent_player();
        row = row - 1;

        msg.arg1 = row;
        msg.arg2 = col;
        board.setBoard(row,col,player);
        board.setRecord(row,col,pre_step);
        pre_step = col;
        board.setCount(board.getCount()+1);
        if(judge(row,col)){
            msg.obj = winnerPath;
            msg.what = COMPLETED;
        }
        else if(board.getCount() == termination_count){
            msg.what = Terminated;
        }
        else{
            msg.what = NORMAL;
        }
        board.setCurrent_position(col,row);

        handler.sendMessage(msg);
        return true;
    }

    private boolean verticalJudge(int row,int col){
        int count = 0;
        int start = board.getElement(row,col);
        int t_row = row;
        while (t_row < board.getRow() && start == board.getElement(t_row,col)){
            winnerPath.add(t_row,col);
            count++;
            t_row++;
            if(count >= WinnerPath.WIN_NUM){
                winnerPath.addPath();
                return true;
            }
        }
        winnerPath.clearCordinators();
        return false;
    }

    private boolean horizontalJudge(int row,int col){
        boolean flag = false;
        int count = 0;
        int start = board.getElement(row,col);
        int t_col = col;
        while(t_col >= 0 && start == board.getElement(row,t_col)){
            winnerPath.add(row,t_col);
            t_col--;
            count++;
            if(count >= WinnerPath.WIN_NUM){
                winnerPath.addPath();
                flag = true;
            }
        }
        t_col = col+1;
        while (t_col < board.getCol() && start == board.getElement(row,t_col)){
            winnerPath.add(row,t_col);
            count++;
            t_col++;
            if(count >= WinnerPath.WIN_NUM){
                winnerPath.addPath();
                flag = true;
            }
        }
        if(flag) return  true;
        winnerPath.clearCordinators();
        return false;
    }

    private boolean diagnolJudge(int row,int col){
        boolean flag = false;
        int count = 0;
        int start = board.getElement(row,col);
        int t_row = row;
        int t_col = col;
        while(t_row >= 0 && t_col < board.getCol() && start == board.getElement(t_row,t_col)){
            winnerPath.add(t_row,t_col);
            t_row--;
            t_col++;
            count++;
            if(count >= WinnerPath.WIN_NUM){
                winnerPath.addPath();
                flag = true;
            }
        }
        t_row = row+1;
        t_col = col-1;
        while (t_row < board.getRow() && t_col >= 0 && start == board.getElement(t_row,t_col)){
            winnerPath.add(t_row,t_col);
            t_row++;
            t_col--;
            count++;
            if(count >= WinnerPath.WIN_NUM){
                winnerPath.addPath();
                flag = true;
            }
        }
        if (flag) return true;
        winnerPath.clearCordinators();
        return false;
    }

    private boolean p_diagnolJudge(int row,int col){
        boolean flag = false;
        int count = 0;
        int start = board.getElement(row,col);
        int t_row = row;
        int t_col = col;
        while(t_row < board.getRow() && t_col < board.getCol() && start == board.getElement(t_row,t_col)){
            winnerPath.add(t_row,t_col);
            count++;
            t_row++;
            t_col++;
            if(count >= WinnerPath.WIN_NUM){
                winnerPath.addPath();
                flag = true;
            }
        }
        t_col = col-1;
        t_row = row-1;
        while (t_row >= 0 && t_col >= 0 && start == board.getElement(t_row,t_col)){
            winnerPath.add(t_row,t_col);
            count++;
            t_col--;
            t_row--;
            if(count >= WinnerPath.WIN_NUM){
                winnerPath.addPath();
                flag = true;
            }
        }
        if(flag) return true;
        winnerPath.clearCordinators();
        return false;
    }

    private boolean judge(int row,int col){
        boolean vertical = verticalJudge(row, col);
        boolean horizontal = horizontalJudge(row, col);
        boolean diagnol = diagnolJudge(row, col);
        boolean p_diagnol = p_diagnolJudge(row, col);
        if(vertical) return true;
        if(horizontal) return true;
        if(diagnol) return true;
        if(p_diagnol)return true;
        return false;
    }

    private boolean isFull(int row){
        if(0 >= row)
            return true;
        return false;
    }

    public void rollBack(){
        if(pre_step == START_STEP) {
            handler.sendEmptyMessage(GamePresenter.UNLOCKED);
            return;
        }
        int current_col = pre_step;
        int current_row = board.getCurrent_position(current_col);
        int pre_col = board.getRecord(current_row,current_col);
        board.setBoard(current_row,current_col,Board.EMPTY);
        board.setCount(board.getCount()-1);
        board.setCurrent_position(current_col,current_row+1);
        pre_step = pre_col;
        Message msg = new Message();
        msg.arg1 = current_row;
        msg.arg2 = current_col;
        msg.what = ROLL_BACK;
        handler.sendMessage(msg);
    }
}
