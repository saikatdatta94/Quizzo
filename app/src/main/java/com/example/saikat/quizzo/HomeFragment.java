package com.example.saikat.quizzo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    private ArrayList<String> imageURL = new ArrayList<>();
    private ArrayList<String> head = new ArrayList<>();
    private ArrayList<String> descriptionText = new ArrayList<>();
    View view;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    RecyclerViewAdapter adapter;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_home,container,false);

        Toolbar toolbar = view.findViewById(R.id.profile_toolbar);
        toolbar.getMenu().clear();
        addList();


        for (int i = 0; i < head.size() ; i++) {
            Log.i("Head",head.get(i));
        }

        return view;
    }



    private void addList() {
//        Todo: Or basically we don't need to add data because we will be receiving Object from the
//        Todo:  So populate a list of objects with the data received from the database
        head.add("Lorem Ip1");
        descriptionText.add("hjshdjsd");
        imageURL.add("jhdjhs");

        head.add("Lorem Ip2");
        descriptionText.add("hjssdhdjsd");
        imageURL.add("jhdjhs");

        head.add("Lorem Ip1");
        descriptionText.add("hjshdjsd");
        imageURL.add("jhdjhs");

        head.add("Lorem Ip2");
        descriptionText.add("hjssdhdjsd");
        imageURL.add("jhdjhs");

        head.add("Lorem Ip1");
        descriptionText.add("hjshdjsd");
        imageURL.add("jhdjhs");

        head.add("Lorem Ip2");
        descriptionText.add("hjssdhdjsd");
        imageURL.add("jhdjhs");

        // Calling initRecyclerView Method for Show Horizontal recycler view
        initRecyclerView();

    }


//    Initialized Horizontal recycler view for Recommended section

    private void initRecyclerView() {
        recyclerView = view.findViewById(R.id.home_horizontal_recyclerView);
        layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerViewAdapter(getActivity(),imageURL,head,descriptionText);
        recyclerView.setAdapter(adapter);
    }


}
