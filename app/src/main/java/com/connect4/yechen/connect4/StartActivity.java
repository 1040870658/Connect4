package com.connect4.yechen.connect4;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by yechen on 2016/10/31.
 */

public class StartActivity extends Activity{
    private Intent intent;
    private TextView tv_caption;
    private EditText ed_p1;
    private EditText ed_p2;
    private String p1_name;
    private String p2_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ed_p1 = (EditText) findViewById(R.id.ed_p1);
        ed_p2 = (EditText) findViewById(R.id.ed_p2);
        tv_caption = (TextView) findViewById(R.id.tv_caption);
    }
    public void Start(View v){
        v.setEnabled(false);
        ObjectAnimator startAnimator = ObjectAnimator.ofFloat(
                tv_caption,"rotation",0f,-20f,-10f,0);
        ObjectAnimator goAinimator = ObjectAnimator.ofFloat(
                tv_caption,"rotation",0f,25f);
        goAinimator.setDuration(200);
        startAnimator.setDuration(1200);
        startAnimator.setRepeatCount(2);
        goAinimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                p1_name = ed_p1.getText().toString();
                p2_name = ed_p2.getText().toString();
                if(p1_name == null || p1_name.equals("")){
                    p1_name = "Player1";
                }
                if(p2_name == null || p2_name.equals("")){
                    p2_name = "Player2";
                }
                intent = new Intent(StartActivity.this,MainActivity.class);
                intent.putExtra(MainActivity.P1_NAME_PARAM,p1_name);
                intent.putExtra(MainActivity.P2_NAME_PARAM,p2_name);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                startActivity(intent);
                finish();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(goAinimator).after(startAnimator);
        animatorSet.start();
    }
    public void Enable_p1(View v){
        ObjectAnimator rotateAnimation;
        if(ed_p1.isEnabled()){
           rotateAnimation = ObjectAnimator.ofFloat(v,"rotation",25f,0f);
            ed_p1.setEnabled(false);
        }
        else{
            rotateAnimation = ObjectAnimator.ofFloat(v,"rotation",0,25f);
            ed_p1.setEnabled(true);
        }
        rotateAnimation.setDuration(500);
        rotateAnimation.start();
    }
    public void Enable_p2(View v){
        ObjectAnimator rotateAnimation;
        if(ed_p2.isEnabled()){
            rotateAnimation = ObjectAnimator.ofFloat(v,"rotation",25f,0f);
            ed_p2.setEnabled(false);
        }
        else{
            rotateAnimation = ObjectAnimator.ofFloat(v,"rotation",0f,25f);
            ed_p2.setEnabled(true);
        }
        rotateAnimation.setDuration(500);
        rotateAnimation.start();
    }
}
