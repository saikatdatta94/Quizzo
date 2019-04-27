package com.example.saikat.quizzo;


import android.app.ActivityOptions;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {



    private FirebaseFirestore db;
    private CollectionReference notebookRef;
    private HorizontalCategoryListAdapter horizontalCategoryListAdapter;



    private static final String TAG = "HomeFragment";
    //    TODO: Make this a single object
    //    TODO: Make this a single object


//    It'll contain category list fetched from database



    View view;
    private RecyclerView recommendedRecyclerView;
    private FirestoreRecyclerOptions<FollowingCategoryItemClass> options;

    //  Horizontal RecycleView
//    RecyclerView recyclerView;
//    LinearLayoutManager layoutManager;
//    RecyclerViewAdapter adapter;

    //  Initialise Categories
    View categoryScience;
    View category2;
    View category3;
    View category4;
    View category5;
    public String userId ="" ;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_home,container,false);

//        Declare Categories
        categoryScience = view.findViewById(R.id.category_science);
        category2 = view.findViewById(R.id.cat2);
        category3 = view.findViewById(R.id.cat3);
        category4 = view.findViewById(R.id.cat4);
        category5 = view.findViewById(R.id.cat5);


//      This array Contains color list for Category Items Strip
        View[] categoryViewList = {categoryScience,category2,category3,category4,category5};
        String[] categoryName = {"Science","category2","category3","category4","category5"};

        ColorStateList[] categoryTintList = {
                getActivity().getResources().getColorStateList(R.color.color2),
                getActivity().getResources().getColorStateList(R.color.color1),
                getActivity().getResources().getColorStateList(R.color.colorChrome),
                getActivity().getResources().getColorStateList(R.color.tooLightViolet),
                getActivity().getResources().getColorStateList(R.color.lightViolet)
        };



//        Create category Item list view
        for (int i = 0; i < categoryViewList.length ; i++) {
            populateCategoriesList(categoryViewList[i],categoryTintList[i],categoryName[i]);
        }

        Toolbar toolbar = view.findViewById(R.id.profile_toolbar);
        toolbar.getMenu().clear();



//    TODO************************  Code for recommended categories
        recommendedRecyclerView = view.findViewById(R.id.home_horizontal_recyclerView);
        horizontalCategoryListAdapter = new HorizontalCategoryListAdapter(options);
        recommendedRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        recommendedRecyclerView.setAdapter(horizontalCategoryListAdapter);


        horizontalCategoryListAdapter.setOnHorizontalCategoryOnclickListener(new HorizontalCategoryListAdapter.OnHorizontalCategoryOnclickListener() {
            @Override
            public void onHorizontalItemCLick(DocumentSnapshot documentSnapshot, int position) {
                Bundle bndlanimation =
                        ActivityOptions.makeCustomAnimation(getActivity().getBaseContext(), R.anim.animation,R.anim.animation2).toBundle();
                FollowingCategoryItemClass following = documentSnapshot.toObject(FollowingCategoryItemClass.class);
                Intent intent = new Intent(getActivity(),QuestionActivity.class);

//                TODO:***************************** PASS details to the intent

                startActivity(intent,bndlanimation);
            }
        });

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//     TODO **********************   This code must be kept inside onCreate method because lifecycle od onCreate is started earlier than onViewCreated
        //    Get Firebase user
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user!=null){
            userId = user.getUid();
        }



        db = FirebaseFirestore.getInstance();
        notebookRef   = db.collection("/users/"+userId+"/Notebook");//Change the collection path to desired collection later
        Query query = notebookRef.orderBy("priority", Query.Direction.DESCENDING);// Sorted according to priority
        options = new FirestoreRecyclerOptions.Builder<FollowingCategoryItemClass>()
                .setQuery(query, FollowingCategoryItemClass.class)
                .build();



    }

    @Override
    public void onStart() {
        super.onStart();
        horizontalCategoryListAdapter.startListening();
        Log.i(TAG,"Started Listening");
    }

    @Override
    public void onStop() {
        super.onStop();
        horizontalCategoryListAdapter.stopListening();
        Log.i(TAG,"Stopped Listening");
    }






//    TODO This is categoryList containing the strip view

    private void populateCategoriesList(View categoryView, ColorStateList color, final String categoryName) {
//        Grabbing StripView of type View
        View stripView = categoryView.findViewById(R.id.category_item_strip);
        stripView.setBackgroundTintList(color);

        TextView categoryTextView = categoryView.findViewById(R.id.category_item_text);
        categoryTextView.setText(categoryName);

        categoryView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //Redirecting user to Main Activity and Passing the user data
                Intent categoryListIntent = new Intent(getActivity(),CategoryListActivity.class); //Creating Intent and Passing Login Creds
                categoryListIntent.putExtra("categoryName",categoryName);
//                intent.putExtra("userName",userName);
//                intent.putExtra("profile_photo_URL",profilePhotoURL.toString());
                Bundle bndlanimation =
                        ActivityOptions.makeCustomAnimation(getContext(), R.anim.animation,R.anim.animation2).toBundle();

                startActivity(categoryListIntent,bndlanimation);
//                finish();
                Log.i("Msg","I was clicked");
            }
        });


    }










}
