package com.example.saikat.quizzo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.system.Os;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.Nullable;


public class QuizActivity extends AppCompatActivity  implements BottomSnackbarClass.BottomSheetListener{


//    Receive Intent
    private String categoryName;

//TODO:    is questions loaded from database
    private boolean isLoaded = false;
    private static final String TAG = "QUIZ_ACTIVITY";
    private ArrayList<Question> questionList = new ArrayList<Question>();

    private TextView timerText;
    private TextView highScoreTextView;
    private int highScore = 0;

    private ProgressBar timerProgress ;
//    Question progressbar
    private ProgressBar questionProgressBar;

    private ObjectAnimator progressAnimator = ObjectAnimator.ofInt(timerProgress,"progress",100,0);
    private CountDownTimer countDownTimer;

    private int correctUntilLevel = 0;

    private Timer timer;
    private int sec;

//    SCores
    private TextView currentScoreView;

    private TextView questionTextView;
    private ImageView questionImage;
    private Button b1;
    private Button b2;
    private Button b3;
    private Button b4;

//    category ,level, question no
    private String category;
    private int level = 1; // TODO: get level from previous activity
    int solvedQuestionsInALevel = 0;
    private int questionRequestNo = 0;
    private int score = 0;

    private int qNo = 1;

//    database values
    private String questionText =" ";
    private String op1=" " ;
    private String op2=" " ;
    private String op3=" " ;
    private String op4 =" ";
    private int correctOption ;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference questionRef = db.collection("questions");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        Log.i(TAG, "onCreate: ");

        currentScoreView = findViewById(R.id.current_score);

        questionTextView = findViewById(R.id.question);
        questionImage = findViewById(R.id.image_question);
        b1 = findViewById(R.id.b1);
        b2 = findViewById(R.id.b2);
        b3 = findViewById(R.id.b3);
        b4 = findViewById(R.id.b4);
        highScoreTextView = findViewById(R.id.high_score);

        questionTextView.setVisibility(View.GONE);
        questionImage.setVisibility(View.GONE);
        b1.setVisibility(View.GONE);
        b2.setVisibility(View.GONE);
        b3.setVisibility(View.GONE);
        b4.setVisibility(View.GONE);



        timerText = findViewById(R.id.counter);
        timerProgress = findViewById(R.id.timer_progress);
        timerProgress.setMax(100);  //Setting the timer progressbar max to 100

        questionProgressBar = findViewById(R.id.questions_progressBar);
        questionProgressBar.setMax(5);



        BottomSnackbarClass popUp = new BottomSnackbarClass();
        popUp.show(getSupportFragmentManager(),"changeLater");
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passButtonOfCorrectAnswer(b1,1);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passButtonOfCorrectAnswer(b2,2);
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passButtonOfCorrectAnswer(b3,3);
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passButtonOfCorrectAnswer(b4,4);
            }
        });


        loadNextQuestion();
    }

    private void passButtonOfCorrectAnswer(Button button,int optionNumber) {
        progressAnimator.end();
        timer.cancel();
        countDownTimer.cancel();
        b1.setClickable(false);
        b2.setClickable(false);
        b3.setClickable(false);
        b4.setClickable(false);



        if (isAnswerCorrect(optionNumber)){
            button.setBackgroundColor(getResources().getColor(R.color.theme_green));
            questionProgressBar.setProgress(++correctUntilLevel);
            //            answer correct increment score
            score++;
            currentScoreView.setText(String.valueOf(score));
        }else {
            button.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        }

        setCorrectOptionColor();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms
                showBottomSheetDialog();
            }
        }, 2000);




    }

    private void showBottomSheetDialog() {
        BottomSnackbarClass popUp = new BottomSnackbarClass();
        popUp.show(getSupportFragmentManager(),"changeLater");
    }

    private void setCorrectOptionColor() {
        switch (correctOption){
            case 1:
                b1.setBackgroundColor(getResources().getColor(R.color.theme_green));
                break;
            case 2:
                b2.setBackgroundColor(getResources().getColor(R.color.theme_green));
                break;
            case 3:
                b3.setBackgroundColor(getResources().getColor(R.color.theme_green));
                break;
            case 4:
                b4.setBackgroundColor(getResources().getColor(R.color.theme_green));
                break;
                default:
                    Log.i(TAG, "setCorrectOptionColor: Problem loading data");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        categoryName = getIntent().getStringExtra("catName");
        Log.i(TAG, "onStart: ");
        questionRef.whereEqualTo("category",categoryName)
                .limit(5)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots){
                            Question question = documentSnapshot.toObject(Question.class);
                            questionList.add(question);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
            }
        });


    }

    //   TODO ********************************** BottomSheet passed text



    public void startTimer(){

        countDownTimer = new CountDownTimer(10000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
              timerText.setText(String.valueOf(millisUntilFinished/1000));
            }

            @Override
            public void onFinish() {

            }
        };
        countDownTimer.start();
        if (Build.VERSION.SDK_INT >= 24) {
            sec = 100;
            timerProgress.setProgress(sec,true);
            timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    if (sec>=-1){
                            sec--;
                            timerProgress.setProgress(sec,true);
                    }
                    else {
                        timer.cancel();
                    }

                }
            },0,100);
        }
        else if (Build.VERSION.SDK_INT < 24) {


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

    public void loadNextQuestion(){

    }


//    return true if the answer is correct or false otherwise
    public boolean isAnswerCorrect(int optionNo){
        if (optionNo == correctOption){

            return true;
        }
        return false;
    }

//    On backPressed show quit dialog
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Do you want to Quit?")
                .setMessage("")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(QuizActivity.this, "Quit", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(QuizActivity.this, "Stay", Toast.LENGTH_SHORT).show();
                    }
                }).show();
    }

    @Override
    public void onContinueClicked() {
//    Start the timer only if the player is ready to take the question
        questionTextView.setVisibility(View.VISIBLE);
        questionImage.setVisibility(View.VISIBLE);
        b1.setVisibility(View.VISIBLE);
        b2.setVisibility(View.VISIBLE);
        b3.setVisibility(View.VISIBLE);
        b4.setVisibility(View.VISIBLE);

        b1.setClickable(true);
        b2.setClickable(true);
        b3.setClickable(true);
        b4.setClickable(true);

        startTimer();

        b1.setBackgroundColor(getResources().getColor(R.color.quizBtnDefault));
        b2.setBackgroundColor(getResources().getColor(R.color.quizBtnDefault));
        b3.setBackgroundColor(getResources().getColor(R.color.quizBtnDefault));
        b4.setBackgroundColor(getResources().getColor(R.color.quizBtnDefault));

        questionTextView.setText(questionList.get(questionRequestNo).getQuestion());
        b1.setText(questionList.get(questionRequestNo).getOption1());
        b2.setText(questionList.get(questionRequestNo).getOption2());
        b3.setText(questionList.get(questionRequestNo).getOption3());
        b4.setText(questionList.get(questionRequestNo).getOption4());
        correctOption = questionList.get(questionRequestNo).getCorrect();
        qNo++;

//        increment for next question
        if (questionRequestNo<=4){
            questionRequestNo++;
        }else {
            questionRequestNo = 0;
        }


    }

    @Override
    public void onSurrenderClicked() {
//Send user to a non cancellable modal or a new activity to start again and to view stats
    }


//    Set bottom modal components
    @Override
    public String setQuestionNo() {
//        return a string variable containing number of question

        return "Question "+ qNo;
    }

    @Override
    public String setSubCategoryName() {
        return categoryName;
    }

    @Override
    public String setParentCategoryName() {
        return "Somethung";
    }
}
