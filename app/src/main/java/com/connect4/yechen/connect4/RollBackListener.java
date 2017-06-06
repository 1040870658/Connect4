package com.connect4.yechen.connect4;

import android.animation.Animator;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.widget.ImageButton;

import com.connect4.yechen.connect4.model.Board;
import com.connect4.yechen.connect4.presenter.GamePresenter;

import static android.R.attr.animation;

/**
 * Created by yechen on 2016/10/30.
 */

public class RollBackListener implements Animator.AnimatorListener{
    private Handler handler;
    private int current_row;
    private ImageButton desButton;
    private Board board;
    private int col;
    private float Y;
    public RollBackListener(int col, Handler handler, Board board){
        this.board = board;
        this.handler = handler;
        this.col = col;
        this.current_row = board.getCurrent_position(col);
        this.desButton = board.getImageButton(current_row-1,col);
    }
    @Override
    public void onAnimationStart(Animator animation) {
        Y = desButton.getY();
    }

    @Override
    public void onAnimationEnd(Animator animation) {
        desButton.setY(Y);
        desButton.setImageDrawable((Drawable) MainActivity.hashChess.get(Board.EMPTY));
        handler.sendEmptyMessage(GamePresenter.TURN_PLAYER);
    }
    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }
}