package com.example.saikat.quizzo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.system.Os;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;


public class QuizActivity extends AppCompatActivity  implements BottomSnackbarClass.BottomSheetListener{

    private TextView timerText;
    private ProgressBar timerProgress ;
    private ObjectAnimator progressAnimator;
    private Timer timer;
    private int sec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        timerText = findViewById(R.id.counter);
        timerProgress = findViewById(R.id.timer_progress);
        timerProgress.setMax(100);



        Button btn1 = findViewById(R.id.b1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSnackbarClass popUp = new BottomSnackbarClass();
                popUp.show(getSupportFragmentManager(),"changeLater");
            }
        });

        startTimer();
    }

    //   TODO ********************************** BottomSheet passed text
    @Override
    public void onButtonClicked(String passText) {
        Toast.makeText(this, ""+passText, Toast.LENGTH_LONG).show();
    }


    public void startTimer(){









        CountDownTimer countDownTimer = new CountDownTimer(10000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
              timerText.setText(String.valueOf(millisUntilFinished/1000));
            }

            @Override
            public void onFinish() {
//               timerProgress.setProgress(0);
            }
        };
        countDownTimer.start();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            sec = 100;
            timerProgress.setProgress(sec,true);
            timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    if (sec>=-1){
                        if (Build.VERSION.SDK_INT >= 24){
                            sec--;
                            timerProgress.setProgress(sec,true);
                        }
                    }
                    else {
                        timer.cancel();
                    }

                }
            },0,100);
        }
        else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                    progressAnimator = ObjectAnimator.ofInt(timerProgress,"progress",100,0);

                    progressAnimator.setDuration(10000);
                    progressAnimator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            timerProgress.setVisibility(View.GONE);
                        }
                    });

                    progressAnimator.start();
        }



    }


}
