package com.example.saikat.quizzo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import java.util.ArrayList;

public class CategoryListActivity extends AppCompatActivity {

    private String activityHeading;

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


        for (int i = 0; i <10 ; i++) {
            listViewItems.add(new ListItem(ListItem.ListType.TWO,"",""+i,""+i));
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

}
