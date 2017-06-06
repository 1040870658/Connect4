package com.connect4.yechen.connect4;

import android.animation.Animator;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.widget.ImageButton;

import com.connect4.yechen.connect4.model.Board;
import com.connect4.yechen.connect4.presenter.GamePresenter;

/**
 * Created by yechen on 2016/10/27.
 */

public class CompletedBubbleListener implements Animator.AnimatorListener {
    private Handler handler;
    private int current_row;
    private ImageButton desButton;
    private Board board;
    private int col;
    private float Y;
    public CompletedBubbleListener(int col, Handler handler, Board board){
        this.board = board;
        this.desButton = board.getImageButton(0,col);
        this.handler = handler;
        this.col = col;
        this.current_row = board.getCurrent_position(col);
    }
    @Override
    public void onAnimationStart(Animator animation) {
        Y = desButton.getY();
    }

    @Override
    public void onAnimationEnd(Animator animation) {
        board.getImageButton(current_row,col).
                setImageDrawable((BitmapDrawable) MainActivity.hashChess.get(board.getCurrent_player()));
        if(current_row != 0) {
            desButton.setImageDrawable((BitmapDrawable) MainActivity.hashChess.get(Board.EMPTY));
            desButton.setY(Y);
        }
        handler.sendEmptyMessage(GamePresenter.FINISH);
    }

    @Override
    public void onAnimationCancel(Animator animation) {
    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }
}
