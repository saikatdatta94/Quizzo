package com.example.saikat.quizzo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

public class CategoryListActivity extends AppCompatActivity {

    private String activityHeading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);

        if (getIntent()!=null){
            activityHeading = getIntent().getStringExtra("categoryName");
        }


        Toolbar toolbar = findViewById(R.id.category_list_toolbar);
        setSupportActionBar(toolbar);
//       Enabling Back Button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
//       Setting toolbar title to settings
        getSupportActionBar().setTitle(activityHeading);
//        toolbar.setTitle("Category List");
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

}
