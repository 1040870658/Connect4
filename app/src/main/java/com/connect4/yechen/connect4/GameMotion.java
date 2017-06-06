package com.connect4.yechen.connect4;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageButton;

import com.connect4.yechen.connect4.model.Board;
import com.connect4.yechen.connect4.presenter.GamePresenter;

/**
 * Created by yechen on 2016/10/14.
 */

public class GameMotion {
    public static final int LONG_SWITCH_PAGE = 2000;
    public static final int NORMAL_SWITCH_PAGE = 800;
    public static final int SHORT_SWITCH_PAGE = 500;
    public static final int MOVE_DURATION = 1200;
    public static final int SHAKE_FACTOR = 10;
    public static final int STAMP = 10;
    public static final int REPEAT_TIMES = 2;
    private Handler handler;
    private ObjectAnimator playerHint;
    private ObjectAnimator fadeInOut;
    private ObjectAnimator fadeOutIn;
    private ObjectAnimator scaleX;
    private ObjectAnimator scaleY;
    private ObjectAnimator rotation;
    private ObjectAnimator translateY;
    private DisplayMetrics metrics;

    public GameMotion(DisplayMetrics metrics,Handler handler) {
        this.metrics = metrics;
        this.handler = handler;
    }
    public void hintPlayer(View v){
        if(playerHint == null) {
            playerHint = ObjectAnimator.ofFloat(v, "rotation", 0f, 10f, 0, -10f, 0);
            playerHint.addListener(new Animator.AnimatorListener() {

                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    View view = (View) playerHint.getTarget();
                    view.setRotation(0f);
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    View view = (View) playerHint.getTarget();
                    view.setRotation(0f);
                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }
        else {
            playerHint.cancel();
            playerHint.setTarget(v);
        }

        playerHint.setDuration(1000);
        playerHint.setRepeatMode(ValueAnimator.REVERSE);
        playerHint.setRepeatCount(ValueAnimator.INFINITE);
        playerHint.setStartDelay(SHORT_SWITCH_PAGE);
        playerHint.start();
    }

    private void initFadeInout(View v) {
        if (fadeInOut == null)
            fadeInOut = ObjectAnimator.ofFloat(v, "alpha", 1f, 0.6f, 0f, 1f);
        else {
            fadeInOut.setTarget(v);
            fadeInOut.cancel();
        }
    }

    private void initFadeoutIn(View v) {
        if(fadeOutIn == null)
            fadeOutIn = ObjectAnimator.ofFloat(v,"alpha",0f,1f,0f);
        else
            fadeOutIn.setTarget(v);
    }

    private void initScaleX(View v) {
        if (scaleX == null)
            scaleX = ObjectAnimator.ofFloat(v, "scale", v.getScaleX(), metrics.widthPixels / 2);
        else
            scaleX.setTarget(v);
    }

    private void initScaleY(View v) {
        if (scaleY == null)
            scaleY = ObjectAnimator.ofFloat(v, "Y", v.getScaleY(), metrics.heightPixels / 2);
        else
            scaleY.setTarget(v);
    }

    private void initRotation(View v){
        if(rotation == null)
            rotation = ObjectAnimator.ofFloat(v,"rotation",0f,360f,0f);
        else
            rotation.setTarget(v);
    }

    public void shake(final View v){
        final float X = v.getX();
        ObjectAnimator translateX;
        translateX = ObjectAnimator.ofFloat(v, "X", X - SHAKE_FACTOR, X + SHAKE_FACTOR, X);
        translateX.setDuration(SHORT_SWITCH_PAGE);
        translateX.start();
    }
    public void shake(final View v,Animator.AnimatorListener animatorListener){
        final float X = v.getX();
        ObjectAnimator translateX;
        translateX = ObjectAnimator.ofFloat(v, "X", X - SHAKE_FACTOR/2, X + SHAKE_FACTOR/2, X,X,X,X);
        translateX.addListener(animatorListener);
        translateX.setDuration(NORMAL_SWITCH_PAGE);
        translateX.setRepeatCount(REPEAT_TIMES);
        translateX.start();
    }
    public void winShake(final ImageButton button, final Drawable winnerDrawable){
        shake(button, new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                button.setImageDrawable((Drawable) MainActivity.hashChess.get(Board.WARNING));
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                button.setImageDrawable(winnerDrawable);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }
    public void startGame(View v){
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(LONG_SWITCH_PAGE);
        initScaleX(v);
        initFadeInout(v);
        initScaleY(v);
        animatorSet.play(fadeInOut).with(scaleX).with(scaleY);
    }
    public void switchPlayer(View v){
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(SHORT_SWITCH_PAGE);
        initFadeInout(v);
        initRotation(v);
        animatorSet.play(fadeInOut).with(rotation);
    }
    public void moveChess(final View v,float Des){
        final float Y = v.getY();
        translateY = ObjectAnimator.ofFloat(v,"Y",Y,Des);
        translateY.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                v.setY(Y);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        AnimatorSet moveChess = new AnimatorSet();
        moveChess.setDuration(MOVE_DURATION);
        //ObjectAnimator m_fadeInOut = ObjectAnimator.ofFloat(v, "alpha", 1f, 0.6f, 0f, 1f);
        initFadeInout(v);
        fadeInOut.setDuration(MOVE_DURATION);
        //m_fadeInOut.setDuration(MOVE_DURATION);
        moveChess.play(fadeInOut).with(translateY);
        moveChess.start();
    }
    public void bubbleChess(final Board board,final int col, int distance,Animator.AnimatorListener bubbleListener){
        final int current_row = board.getCurrent_position(col);
        int i = current_row + 1;
        final ImageButton desButton = board.getImageButton(0,col);
        desButton.setImageDrawable((BitmapDrawable) MainActivity.hashChess.get(board.getCurrent_player()));
        ObjectAnimator setBitmapAfterMoving = ObjectAnimator.ofFloat(desButton,"Y",desButton.getY()
                ,desButton.getY()+board.getCurrent_position(col)*distance);
        setBitmapAfterMoving.setDuration(MOVE_DURATION);
        setBitmapAfterMoving.addListener(bubbleListener);
        setBitmapAfterMoving.start();
        while(--i > 0){
            ImageButton button = board.getImageButton(i,col);
            moveChess(button,button.getY()-distance);
        }
    }
    public void rollbackChess(final Board board, final int col, int distance, Animator.AnimatorListener animatorListener){
        final int current_row = board.getCurrent_position(col);
        int i = current_row ;
        final ImageButton desButton = board.getImageButton(--i,col);
        int factor = board.getCurrent_position(col);
        if (factor == 0)
            factor = 0;
        factor--;
        ObjectAnimator setBitmapAfterMoving = ObjectAnimator.ofFloat(desButton,"Y",desButton.getY()
                ,desButton.getY()-factor*distance);
        setBitmapAfterMoving.setDuration(MOVE_DURATION);
        setBitmapAfterMoving.addListener(animatorListener);
        setBitmapAfterMoving.start();
        while(--i >= 0){
            ImageButton button = board.getImageButton(i,col);
            moveChess(button,button.getY()+distance);
        }
    }
}
