package com.example.saikat.quizzo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class Dummy extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef = db.collection("Notebook");
//
    private VerticalCategoryListAdapter adapter;



    /*TODO This is a DUMMY activity for testing purposes we must delete it in the time of build
*
*
* */



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy);

        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        Query query = notebookRef.orderBy("priority", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<CategoryItemClass> options = new FirestoreRecyclerOptions.Builder<CategoryItemClass>()
                .setQuery(query, CategoryItemClass.class)
                .build();

        adapter = new VerticalCategoryListAdapter(options);

        RecyclerView recyclerView = findViewById(R.id.test_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
        Log.i("Msg:","Started Listening");
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}
