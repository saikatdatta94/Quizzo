package com.example.saikat.quizzo;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class CategoryListActivity extends AppCompatActivity {


    private static final String TAG = "CategotyList";
    private String[] categoryListDetailsDestination = {"space","Earth"};
    private String activityHeading;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference categoriesRef = db.document("/categories/science/space/detail");

    //    TODO: Make this a single object
    private ArrayList<ListItem> listViewItems = new ArrayList<ListItem>();
    //    TODO: Make this a single object

    //  RecycleView
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    RecyclerViewAdapter adapter;

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

        //  TODO:  Test
        loadCategory();
        addList();
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

    //    Initialized Horizontal recycler view for Recommended section

    private void initRecyclerView() {
        recyclerView = this.findViewById(R.id.category_vertical_list);
        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerViewAdapter(this,listViewItems);
        recyclerView.setAdapter(adapter);
    }


    private void addList() {
//        Todo: Or basically we don't need to add data because we will be receiving Object from the
//        Todo:  So populate a list of objects with the data received from the database



        String head = "";
        String description ="";
        for (int i = 0; i <categoryListDetailsDestination.length ; i++) {

            listViewItems.add(new ListItem(ListItem.ListType.TWO,"",""+i,
                    ""+i,"Science"));
        }

//        head.add("Lorem Ip1");
//        descriptionText.add("hjshdjsd");
//        imageURL.add("jhdjhs");
//
//        head.add("Lorem Ip2");
//        descriptionText.add("hjssdhdjsd");
//        imageURL.add("jhdjhs");
//
//        head.add("Lorem Ip1");
//        descriptionText.add("hjshdjsd");
//        imageURL.add("jhdjhs");
//
//        head.add("Lorem Ip2");
//        descriptionText.add("hjssdhdjsd");
//        imageURL.add("jhdjhs");
//
//        head.add("Lorem Ip1");
//        descriptionText.add("hjshdjsd");
//        imageURL.add("jhdjhs");
//
//        head.add("Lorem Ip2");
//        descriptionText.add("hjssdhdjsd");
//        imageURL.add("jhdjhs");

        // Calling initRecyclerView Method for Show Horizontal recycler view
        initRecyclerView();

    }

    //  TODO:  Test
    private void loadCategory() {
        Log.i(TAG,"I was Loaded");
        categoriesRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                       if (documentSnapshot.exists()){
                           Log.i(TAG,"Doc: " + documentSnapshot.get("description") );
                       }else {
                           Log.d(TAG, "No such document");
                       }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "Failure");
                    }
                });

    }

}
