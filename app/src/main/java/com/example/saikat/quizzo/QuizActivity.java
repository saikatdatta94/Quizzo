package com.example.saikat.quizzo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.Nullable;


public class QuizActivity extends AppCompatActivity  implements BottomSnackbarClass.BottomSheetListener{

//    leader board Database constants
    private static final String USER_NAME = "userName";
    private static final String PROFILE_PHOTO_URL = "photoURL";
    private static final String USER_ID = "userId";
    private static final String SCORE = "score";


//    USer data variables
    private String userName;
    private String userId;
    private Uri photoUrl;
    private int scoreToWrite = 0;
    private int totalXp = 0;
    private boolean firstTime = true;


//    Power ups
    private RelativeLayout correctAnsPowerUp;


//    Receive Intent
    private String categoryName;
    private String categoryId;
    private String parentCategory;

    private int highScore;
    private int previousXp;

    private int levelToIncrement;
    private int residualXp;
    private int gamePlayBonus =100; //given only once ---- After 1 use reduce to 0
    private int correctXpStreak = 0; // needed to count and reset xp


//TODO:    is questions loaded from database
    private boolean isLoaded = false;
    private static final String TAG = "QUIZ_ACTIVITY";
    private ArrayList<Question> questionList = new ArrayList<Question>();




    private ProgressBar timerProgress ;
//    Question progressbar
    private ProgressBar questionProgressBar;

    private ObjectAnimator progressAnimator = ObjectAnimator.ofInt(timerProgress,"progress",100,0);
    private CountDownTimer countDownTimer;

    private int correctUntilLevel = 0;
    private int xp =0;

    private Timer timer;
    private int sec;

//    Stats Text view
    private TextView currentScoreView;
    private TextView timerText;
    private TextView highScoreTextView;
    private TextView xpTextView;


    private TextView questionTextView;
    private ImageView questionImage;
    private Button b1;
    private Button b2;
    private Button b3;
    private Button b4;

//    category ,level, question no
    private String category;
    private int level =1; // TODO: get level from previous activity
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
    private DocumentReference followingCatRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        Log.i(TAG, "onCreate: ");

//        Power Up views
        correctAnsPowerUp = findViewById(R.id.right_answer_power_up);
        correctAnsPowerUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                correctAnswerPowerUpUsed();
            }
        });

        currentScoreView = findViewById(R.id.current_score);

        questionTextView = findViewById(R.id.question);
        questionImage = findViewById(R.id.image_question);
        b1 = findViewById(R.id.b1);
        b2 = findViewById(R.id.b2);
        b3 = findViewById(R.id.b3);
        b4 = findViewById(R.id.b4);
        highScoreTextView = findViewById(R.id.high_score);
        highScore = getIntent().getIntExtra("highScore",0);
        highScoreTextView.setText(String.valueOf(highScore));

        xpTextView = findViewById(R.id.xp);
        xpTextView.setText(String.valueOf(xp));



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



        getUserData();
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
//            questionProgressBar.setProgress(++correctUntilLevel);

            //            answer correct increment score
            score++;
            currentScoreView.setText(String.valueOf(score));
            setXp();
            setHighScore();
            setQuestionProgressBar();
        }else {
            button.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            setCorrectOptionColor();
            writeHighScoreToProfile();
            final Handler wrongAnsHandler = new Handler();
            wrongAnsHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    openResultDialog();
                }
            },2000);
            return;

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
        Log.i(TAG, "checkL: I was called");
        categoryName = getIntent().getStringExtra("catName");
        categoryId = getIntent().getStringExtra("catId");
        parentCategory = getIntent().getStringExtra("parent");
        previousXp = getIntent().getIntExtra("xp",0);
        level = getIntent().getIntExtra("level",0);
        questionRef.whereEqualTo("category",categoryName)
                .limit(10)
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
                Handler timeUpHandler = new Handler();
                timeUpHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        openResultDialog();
                    }
                },2000);
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

//    High score is updated each time if score is greater than highScore
    public void setHighScore(){
        if (score>highScore){
             highScore = score;
             highScoreTextView.setText(String.valueOf(highScore));
        }
    }

//    XP is updated after each correct answer
    public void setXp(){
        ++xp;
        checkLevel();
        xpTextView.setText(String.valueOf(xp*10));
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

//        b1.setBackgroundColor(getResources().getColor(R.color.quizBtnDefault));
        b1.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        b2.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        b3.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        b4.setBackgroundColor(getResources().getColor(R.color.colorWhite));

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
       writeHighScoreToProfile();
       openResultDialog();
    }



    public void openResultDialog(){
//        TODO write to leaderboard if score is higher
        writeToLeaderBoard();

        DialogFragment dialog = GameFinishPopUp.newInstance();
        Bundle args = new Bundle();
        args.putString("catId",categoryId);
        args.putString("catName",categoryName);
        args.putString("parent",parentCategory);
//        TODO send current score
//        TODO send highScore
        dialog.setArguments(args);

        ((GameFinishPopUp) dialog).setCallback(new GameFinishPopUp.Callback() {
            @Override
            public void onActionClick(String name) {

//                User used gem, now get him to next question
                showBottomSheetDialog();

//                TODO Subtract gem
//                TODO Increment score by 1
//                TODO Increment Horizontal progressbar by 1
//                TODO Increment XP and other things




            }
        });

        dialog.show(getSupportFragmentManager(),"tag");
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






//    TODO: PASS THESE DATA TO GAME FINISH POPUP AND UPDATE THERE
//    public void setHighScoreToUserProfile(){
//        final String followingItemsPath = "users/"+userId+"/Notebook/"+categoryId;
//        followingCatRef = db.document(followingItemsPath);
//
//        Map<String,Object> updatedFollowingCategory = new HashMap<>();
//        updatedFollowingCategory.put("highScore",highScore);
//        updatedFollowingCategory.put("xp",0);
//        followingCatRef.update(updatedFollowingCategory);
//    }













//    TODO:  MODIFY it will write to leaderboard if the score is high score or if the user is playing for the first time
    public void writeToLeaderBoard(){
        scoreToWrite = highScore;

        //   TODO:   If document already exists in the leaderboard collection in database then only
        //   TODO:   Update the highScore and XP

        Map<String,Object> leaderBoardObject = new HashMap<>();
        leaderBoardObject.put(USER_NAME,userName);
        leaderBoardObject.put(PROFILE_PHOTO_URL,photoUrl.toString());
        leaderBoardObject.put(USER_ID,userId);
        leaderBoardObject.put(SCORE,scoreToWrite);
        db.collection("categoryItems").document(categoryId).collection("leaderBoard").document(userId).
                set(leaderBoardObject)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(QuizActivity.this, "Failed to write data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getUserData(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userName = user.getDisplayName();
        userId = user.getUid();
        photoUrl = user.getPhotoUrl();
    }


//    We have to check level after quit/wrong ans
    public void checkLevel(){

        correctXpStreak++;

        totalXp = previousXp+(correctXpStreak*10)+gamePlayBonus;

        if (totalXp>=200){

            levelToIncrement = totalXp / 200;
            level = level+levelToIncrement; // Write this level to database
            residualXp = totalXp % 200;  // write this xp to database
            previousXp = residualXp;
            gamePlayBonus = 0;
            correctXpStreak = 0;

        }else {
            residualXp = totalXp;
        }
        Log.i(TAG, "checkLevel:prevXp: "+previousXp+ " xp: "+xp+" streak: " + correctXpStreak +" totalXp : "+totalXp+" residualXp "+residualXp+" gamePlayBon:" +gamePlayBonus);
    }

//    Write highScore to profile at the end of the game(When the answer is wrong or on Surrender clicked
    public void writeHighScoreToProfile(){
        final String followingItemsPath = "users/"+userId+"/Notebook/"+categoryId;
        DocumentReference followingCatDocRef = db.document(followingItemsPath);


        followingCatDocRef.update("xp",residualXp,
                "level",level)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                          correctXpStreak = 0;
                          previousXp = residualXp;
                          gamePlayBonus = 0;

                        Log.i(TAG, "checkLevel Ons: correctXpStreak:"+ correctXpStreak+" previousXp "+ previousXp+" gamePlayBonus: "+gamePlayBonus);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
        if (highScore>=score){

            followingCatDocRef.update("highScore",highScore)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.i(TAG, "onSuccess: Written Highscore :" + highScore);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.i(TAG, "onFailure: Couldn't write score");
                }
            });
        }
    }

    public void setQuestionProgressBar(){
        questionProgressBar.setProgress(score%5);
        if (score%5==0){
            questionProgressBar.setProgress(5);

            final Handler restoreHandler = new Handler();
            restoreHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //            showAnimation
                    questionProgressBar.setProgress(0);
                }
            },2000);
        }



        if (score%5 == 0){

//                TODO  PROGRESSBAR FILLED SHOW SOME ACHIEVEMENT

        }
    }


    private void correctAnswerPowerUpUsed(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setCorrectOptionColor();
            }
        },2000);
    }
}
