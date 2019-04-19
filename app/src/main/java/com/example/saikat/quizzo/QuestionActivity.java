package com.example.saikat.quizzo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class QuestionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        String heading = getIntent().getStringExtra("heading");
        String description = getIntent().getStringExtra("description");

        TextView headingText = findViewById(R.id.head);
        TextView descriptionText = findViewById(R.id.descri);

        headingText.setText(heading);
        descriptionText.setText(description);


    }
}
