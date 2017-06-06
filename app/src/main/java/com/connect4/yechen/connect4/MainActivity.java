package com.connect4.yechen.connect4;

import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.connect4.yechen.connect4.model.Board;
import com.connect4.yechen.connect4.model.Coordinator;
import com.connect4.yechen.connect4.model.WinnerPath;
import com.connect4.yechen.connect4.presenter.GamePresenter;

import java.util.HashMap;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    public static final int ROW = 6;
    public static final int COL = 7;
    public static final int WARNING_STEP = 30;
    public static final int MARGIN = 5;
    public static final String P1_NAME_PARAM = "p1_name";
    public static final String P2_NAME_PARAM = "p2_name";
    public  static  HashMap hashChess = new HashMap();
    private Board board;
    private WinnerPath winnerPath;
    private Handler handler;
    private Vibrator vibrator;
    private TextView tv_result;
    private TextView tv_player_1;
    private TextView tv_player_2;
    private ImageView iv_player_1;
    private ImageView iv_player_2;
    private GamePresenter gamePresenter;
    private GridLayout gridLayout;
    private LinearLayout result_hint;
    private DisplayMetrics metrics;
    private GameMotion gameMotion;
    private Animation scaleAnimation_1;
    private Animation scaleAnimation_2;
    private TextView tv_steps;
    private int element_width;
    private int element_margin;
    private String s_steps;
    private String getS_steps;
    private String p1_name;
    private String p2_name;
    private boolean player_lock ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        if(intent != null){
            p1_name = intent.getStringExtra(P1_NAME_PARAM);
            if(p1_name == null)
                p1_name = "Player1";
            p2_name = intent.getStringExtra(P2_NAME_PARAM);
            if(p2_name == null)
                p2_name = "Player2";
        }
        initHandler();
        vibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
        tv_steps = (TextView) findViewById(R.id.tv_step);
        s_steps = getResources().getString(R.string.step);
        tv_steps.setText(s_steps+"0");
        scaleAnimation_1 = new ScaleAnimation(0.1f, 1.0f, 0.1f, 1.0f);
        scaleAnimation_2 = new ScaleAnimation(0.1f, 1.0f, 0.1f, 1.0f);
        scaleAnimation_1.setDuration(3000);
        scaleAnimation_2.setDuration(3000);
        hashChess.put(Board.PLAYER_1,
                new BitmapDrawable(getResources(), BitmapFactory.decodeResource(getResources(), R.drawable.boom_blue)));
        hashChess.put(Board.PLAYER_2,
                new BitmapDrawable(getResources(), BitmapFactory.decodeResource(getResources(), R.drawable.boom_orange)));
        hashChess.put(Board.EMPTY,
                new BitmapDrawable(getResources(), BitmapFactory.decodeResource(getResources(), R.drawable.boom_init)));
        hashChess.put(Board.FULL,
                new BitmapDrawable(getResources(), BitmapFactory.decodeResource(getResources(), R.drawable.boom_red)));
        hashChess.put(Board.WIN_1,
                new BitmapDrawable(getResources(), BitmapFactory.decodeResource(getResources(), R.drawable.win_blue)));
        hashChess.put(Board.WIN_2,
                new BitmapDrawable(getResources(), BitmapFactory.decodeResource(getResources(), R.drawable.win_orange)));
        hashChess.put(Board.WARNING,
                new BitmapDrawable(getResources(), BitmapFactory.decodeResource(getResources(), R.drawable.boom_ready)));
        hashChess.put(Board.EMPTY,
                new BitmapDrawable(getResources(), BitmapFactory.decodeResource(getResources(), R.drawable.boom_init)));
        hashChess.put(Board.WARNING_1,
                new BitmapDrawable(getResources(),BitmapFactory.decodeResource(getResources(),R.drawable.warning_blue)));
        hashChess.put(Board.WARNING_2,
                new BitmapDrawable(getResources(),BitmapFactory.decodeResource(getResources(),R.drawable.warning_orange)));
        result_hint = (LinearLayout) findViewById(R.id.ll_result);
        tv_result = (TextView) findViewById(R.id.tv_result);
        tv_player_1 = (TextView) findViewById(R.id.tv_p1);
        tv_player_2 = (TextView) findViewById(R.id.tv_p2);
        iv_player_1 = (ImageView) findViewById(R.id.iv_p1);
        iv_player_2 = (ImageView) findViewById(R.id.iv_p2);
        element_width = measureChessWidth();
        tv_player_1.setText(p1_name);
        tv_player_2.setText(p2_name);
        iv_player_1.setAnimation(scaleAnimation_1);
        iv_player_2.setAnimation(scaleAnimation_2);
        scaleAnimation_1.start();
        scaleAnimation_2.start();
        init();
    }

    private void init(){
        player_lock = true;
        board = new Board(ROW,COL);
        winnerPath = new WinnerPath();
        gamePresenter = new GamePresenter(board,handler,winnerPath);
        gameMotion = new GameMotion(metrics,handler);
        gridLayout = (GridLayout) findViewById(R.id.gl_main);
        for(int i = 0;i != ROW;i ++)
            for(int j = 0;j != COL;j ++)
                setElement(gridLayout,i,j);
        gameMotion.hintPlayer(tv_player_1);
    }
    private void setElement(final GridLayout gridLayout, int row, int coloum){
        final ImageButton button = new ImageButton(this);
        button.setImageDrawable((Drawable) hashChess.get(Board.EMPTY));
        button.setBackgroundColor(Color.WHITE);
        GridLayout.Spec rowSpec = GridLayout.spec(row);
        GridLayout.Spec colSpec = GridLayout.spec(coloum);
        final GridLayout.LayoutParams params = new GridLayout.LayoutParams(rowSpec,colSpec);
        params.width = element_width;
        params.height = params.width;
        params.setMargins(MARGIN,MARGIN,MARGIN,MARGIN);
        element_margin = params.leftMargin;
        button.setLayoutParams(params);
        button.setOnClickListener(new ChessListener());
        gridLayout.addView(button);
        board.setImageButton(button,row,coloum);
    }
    private int measureChessWidth(){
        int width;
        int height;
        metrics = getResources().getDisplayMetrics();
        width = metrics.widthPixels-2*MARGIN*(COL)-64;
        height = metrics.heightPixels - result_hint.getHeight()- 80 - 2*MARGIN*ROW;
        return Math.min(width/COL,height/ROW);
    }

    private class ChessListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            if(player_lock) {
                player_lock = false;
                final int col;
                float horizontol_distance;
                horizontol_distance =  v.getX();
                col =(int)Math.floor(horizontol_distance / (element_width+2*element_margin));
                //Log.e("dis",horizontol_distance+"");
                //Log.e("measure",element_width+2*element_margin+"");
                //Log.e("col",col+"");
                new Thread() {
                    @Override
                    public void run() {
                        gamePresenter.putChess(col);
                    }
                }.start();

            }
        }
    }
    private void initHandler(){
        handler = new Handler(){
            Animation result_scaleAnimation;
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case GamePresenter.GAME_DRAW:
                        tv_result.setTextColor(ContextCompat.getColor(MainActivity.this,R.color.colorPrimaryDark));
                        tv_result.setText("Game Draw.");
                        tv_result.setVisibility(View.VISIBLE);
                        result_scaleAnimation = new ScaleAnimation(0.1f,1f,0.1f,1f);
                        result_scaleAnimation.setDuration(2000);
                        tv_result.setAnimation(result_scaleAnimation);
                        result_scaleAnimation.start();
                        break;
                    case GamePresenter.Terminated:
                        gameMotion.bubbleChess(board,msg.arg2,element_width+element_margin*2,
                                new TermminatedListener(msg.arg2,handler,board));
                        getS_steps = s_steps+board.getCount();
                        if(board.getCount() >= WARNING_STEP)
                            tv_steps.setTextColor(Color.RED);
                        tv_steps.setText(getS_steps);
                        break;
                    case GamePresenter.FINISH:
                        Drawable winner;
                        //gameMotion.bubbleChess(board,msg.arg1,msg.arg2,element_width+MARGIN*2);
                        if(board.getCurrent_player() == Board.PLAYER_1)
                            winner = (Drawable) hashChess.get(Board.WIN_1);
                        else
                            winner = (Drawable) hashChess.get(Board.WIN_2);
                        List path = winnerPath.getPath();
                        for(Object object : path){
                            Coordinator coordinator = (Coordinator)object;
                            ImageButton button = board.getImageButton(coordinator.getRow(), coordinator.getCol());
                            gameMotion.winShake(button,winner);
                        }
                        if(board.getCurrent_player() == Board.PLAYER_1){
                            tv_result.setTextColor(ContextCompat.getColor(MainActivity.this,R.color.blue));
                            tv_result.setText(p1_name+" Win !");
                        }
                        else{
                            tv_result.setTextColor(ContextCompat.getColor(MainActivity.this,R.color.orange));
                            tv_result.setText(p2_name+" Win !");
                        }
                        tv_result.setVisibility(View.VISIBLE);
                        result_scaleAnimation = new ScaleAnimation(0.1f,1f,0.1f,1f);
                        result_scaleAnimation.setDuration(2000);
                        tv_result.setAnimation(result_scaleAnimation);
                        result_scaleAnimation.start();
                        break;
                    case GamePresenter.COMPLETED:
                        gameMotion.bubbleChess(board,msg.arg2,element_width+element_margin*2,
                                new CompletedBubbleListener(msg.arg2,handler,board));
                        getS_steps = s_steps+board.getCount();
                        if(board.getCount() >= WARNING_STEP)
                            tv_steps.setTextColor(Color.RED);
                        tv_steps.setText(getS_steps);
                        break;
                    case GamePresenter.NORMAL:
                        gameMotion.bubbleChess(board,msg.arg2,element_width+element_margin*2,
                                new NormalBubbleListener(msg.arg2,handler,board));
                        break;
                    case GamePresenter.ROLL_BACK:
                        //board.getImageButton(msg.arg1,msg.arg2).setImageDrawable((Drawable) hashChess.get(Board.EMPTY));
                        gameMotion.rollbackChess(
                                board,msg.arg2,element_width+element_margin*2,new RollBackListener(msg.arg2,handler,board));
                        sendEmptyMessageDelayed(GamePresenter.UNLOCKED,GameMotion.MOVE_DURATION+GameMotion.STAMP);
                         break;
                    case GamePresenter.COL_FULL:
                        vibrator.vibrate(GameMotion.SHORT_SWITCH_PAGE);
                        for(int i = 0;i != ROW;i ++)
                            gameMotion.shake(board.getImageButton(i,msg.arg1));
                        sendEmptyMessageDelayed(GamePresenter.UNLOCKED,GameMotion.SHORT_SWITCH_PAGE+GameMotion.STAMP);
                        break;
                    case GamePresenter.TURN_PLAYER:
                        gamePresenter.turnPlayer();
                        if(board.getCurrent_player() == Board.PLAYER_1) {
                            gameMotion.hintPlayer(tv_player_1);
                        }
                        else{
                            gameMotion.hintPlayer(tv_player_2);
                        }
                        getS_steps = s_steps+board.getCount();
                        if(board.getCount() >= WARNING_STEP)
                            tv_steps.setTextColor(Color.RED);
                        tv_steps.setText(getS_steps);
                        sendEmptyMessage(GamePresenter.UNLOCKED);
                        break;
                    case GamePresenter.UNLOCKED:
                        player_lock = true;
                        break;
                    default:break;
                }
            }
        };
    }

    public void Restart(View v){
        Intent intent = new Intent(MainActivity.this,MainActivity.class);
        intent.putExtra(P1_NAME_PARAM,p1_name);
        intent.putExtra(P2_NAME_PARAM,p2_name);
        finish();
        startActivity(intent);
    }
    public void Rollback(View v){
        if(player_lock) {
            player_lock = false;
            gamePresenter.rollBack();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
       if(keyCode == KeyEvent.KEYCODE_BACK){
           Intent intent = new Intent(MainActivity.this,StartActivity.class);
           startActivity(intent);
           finish();
       }
        return  false;
    }
}
