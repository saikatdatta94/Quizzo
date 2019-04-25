package com.example.saikat.quizzo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class CategoryListActivity extends AppCompatActivity {


    private static final String TAG = "CategotyList";
    private String activityHeading;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef = db.collection("Notebook");

    private VerticalCategoryListAdapter adapter;



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


        //  TODO:  Test
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        Query query = notebookRef.orderBy("priority", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<FollowingCategoryItemClass> options = new FirestoreRecyclerOptions.Builder<FollowingCategoryItemClass>()
                .setQuery(query, FollowingCategoryItemClass.class)
                .build();

        adapter = new VerticalCategoryListAdapter(options);

        Log.i(TAG, "setUpRecyclerView: "+options.getSnapshots().toString());
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
        Log.i(TAG, "onStart: Started listening");
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
        Log.i(TAG, "onStop: Stopped listening");
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
