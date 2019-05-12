package com.example.saikat.quizzo;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.TransitionManager;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class CategoryDetailActivity extends AppCompatActivity {


    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference catRef;
    private TextView headingView;
    private TextView descriptionView;
    private Button playBtn;

//    Received intent
    private String title;
    private String description;
    private String path;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);



        FloatingActionButton followButton = findViewById(R.id.follow_button);

//        Toolbar
        final Toolbar toolbar = findViewById(R.id.question_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        title = getIntent().getStringExtra("title");
        description = getIntent().getStringExtra("description");

        final CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.toolbar_layout);
        collapsingToolbarLayout.setTitle(title);


        final LinearLayout heading_description = findViewById(R.id.heading_desc);
        TransitionManager.beginDelayedTransition(heading_description);


        headingView = findViewById(R.id.head);
        headingView.setText(title);
        descriptionView = findViewById(R.id.description);
        descriptionView.setText(description);
        playBtn = findViewById(R.id.play_btn);
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playClicked(v);
            }
        });

        //        Make expanded Toolbar title invisible
         final AlphaAnimation fadeIn = new AlphaAnimation(0.0f , 1.0f ) ;
        final AlphaAnimation fadeOut = new AlphaAnimation( 1.0f , 0.0f ) ;



        AppBarLayout appBarLayout = findViewById(R.id.app_bar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.BaseOnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1){
                    scrollRange = appBarLayout.getTotalScrollRange();

                }
                if (scrollRange+verticalOffset == 0){
                    headingView.startAnimation(fadeOut);
                    descriptionView.startAnimation(fadeOut);
                    fadeOut.setDuration(128);
                    fadeOut.setFillAfter(true);


//                    headingView.setVisibility(View.GONE);
//                    descriptionView.setVisibility(View.GONE);
                    isShow = true;
                }else if (isShow){
                    collapsingToolbarLayout.setExpandedTitleColor(Color.TRANSPARENT);
                    headingView.startAnimation(fadeIn);
                    descriptionView.startAnimation(fadeIn);
                    fadeIn.setDuration(128);
                    fadeIn.setFillAfter(true);
//                    headingView.animate().translationYBy(32).setDuration(1000);
//                    headingView.setVisibility(View.VISIBLE);
//                    descriptionView.setVisibility(View.VISIBLE);
                    isShow = false;
                }
            }
        });



        getDataFromFirebase();




//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,WindowManager.LayoutParams.TYPE_STATUS_BAR);

//        String heading = getIntent().getStringExtra("heading");
//        String description = getIntent().getStringExtra("description");

//        TextView headingText = findViewById(R.id.head);
//        TextView descriptionText = findViewById(R.id.descri);

//        headingText.setText(heading);
//        descriptionText.setText(description);
        followButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimaryDark)));


//     TODO   Clicked on follow button
        followButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                followPressed();
                Toast.makeText(CategoryDetailActivity.this, "Follow Clicked", Toast.LENGTH_SHORT).show();
            }
        });

    }


    public void playClicked(View v){

        Intent intent = new Intent(CategoryDetailActivity.this,QuizActivity.class);
        intent.putExtra("catName",title);
        startActivity(intent);
    }

    public void getDataFromFirebase(){
        path = getIntent().getStringExtra("path");

        catRef = db.document(path);
        catRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()){
                    DocumentSnapshot categoryDoc = task.getResult();

                    if (categoryDoc.exists()){
                        FollowingCategoryItemClass catObject = categoryDoc.toObject(FollowingCategoryItemClass.class);

//                        headingView.setText(catObject.getTitle());
//                        descriptionView.setText(catObject.getDescription());
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    public void onBackPressed(){
        Toast.makeText(this, "BackPressed", Toast.LENGTH_SHORT).show();
        finish();
    }

    public void followPressed(){

    }
}
