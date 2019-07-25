package com.example.saikat.quizzo;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class CategoryListActivity extends AppCompatActivity {


    private static final String TAG = "CategotyList";
    private String activityHeading;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef = db.collection("categoryItems");

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
        Query query = notebookRef
                .whereEqualTo("parentCategory",activityHeading)
                .whereEqualTo("isProduction","true");

        FirestoreRecyclerOptions<CategoryItemClass> options = new FirestoreRecyclerOptions.Builder<CategoryItemClass>()
                .setQuery(query, CategoryItemClass.class)
                .build();

        adapter = new VerticalCategoryListAdapter(options);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


//        TODO: ************ On clicking an item takes to a new Activity inherited from Vertical category list Adapter
        adapter.setOnItemClickListener(new VerticalCategoryListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {

                Bundle bndlanimation =
                        ActivityOptions.makeCustomAnimation(getBaseContext(), R.anim.animation,R.anim.animation2).toBundle();
                CategoryItemClass following = documentSnapshot.toObject(CategoryItemClass.class);
                Intent intent = new Intent(CategoryListActivity.this, CategoryDetailActivity.class);
                String path = documentSnapshot.getReference().getPath();

                Log.i(TAG, "onItemClick: "+path);
                intent.putExtra("path",path);
                intent.putExtra("title",following.getTitle());
                intent.putExtra("description",following.getDescription());
                intent.putExtra("id",documentSnapshot.getReference().getId());
                intent.putExtra("parent",activityHeading);
//                intent.putExtra("path",path);

//                TODO:***************************** PASS details to the intent

                startActivity(intent,bndlanimation);

            }
        });
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
