package com.example.saikat.quizzo;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

public class GameFinishPopUp extends DialogFragment implements Animation.AnimationListener {

//    Callback
    private Callback callback;

//    Text animation
// Animation
      private Animation animZoomIn;
      private TextView animTextView;
      private TextView animTextView2;
      private TextView categoryNameTextView;
      private TextView parentCatNameTextView;
      private ImageView profileGameFinish;

//    private LeaderBoardListAdapter adapter;
//    private FirebaseFirestore db;
//    private CollectionReference leaderBoardRef;
//    private RecyclerView leaderBoardRecyclerView;
//    private FirestoreRecyclerOptions<LeaderBoardItem> options;

    private RelativeLayout gemBtn;

    private FirebaseFirestore db;
    private CollectionReference leaderBoardRef;
    private LeaderBoardListAdapter leaderBoardListAdapter;

    private RecyclerView leaderBoardRecyclerView;
    private FirestoreRecyclerOptions<LeaderBoardItem> options;

//    Intents
    private String categoryId;
    private String categoryName;
    private String parent;
    private String photoURL;

    View view;

    static GameFinishPopUp newInstance(){
        return new GameFinishPopUp();
    }

//    Setting up constructor for callback
    public void setCallback(Callback callback){
        this.callback = callback;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL,R.style.quiFinishDialog);
//       Not cancellable at back press
        setCancelable(false);



        // load the animation
        animZoomIn = AnimationUtils.loadAnimation(getActivity(),
                R.anim.zoom_in);

        // set animation listener
        animZoomIn.setAnimationListener(this);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                animTextView.setVisibility(View.VISIBLE);
                animTextView2.setVisibility(View.VISIBLE);

                // start the animation
                animTextView.startAnimation(animZoomIn);
                animTextView2.startAnimation(animZoomIn);
            }
        },2000);



        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId="";
        if (user!=null){
            userId = user.getUid();
            photoURL = user.getPhotoUrl().toString();
        }

        categoryId = getArguments().getString("catId");
        parent = getArguments().getString("parent");
        categoryName = getArguments().getString("catName");




        db = FirebaseFirestore.getInstance();
        leaderBoardRef   = db.collection("categoryItems").document(categoryId).collection("leaderBoard");//Change the collection path to desired collection later
        Query query = leaderBoardRef.orderBy("score", Query.Direction.DESCENDING);// Sorted according to priority
        options = new FirestoreRecyclerOptions.Builder<LeaderBoardItem>()
                .setQuery(query, LeaderBoardItem.class)
                .build();


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.quiz_finish_popup_layout,container,false);

        gemBtn= view.findViewById(R.id.use_gem_button);
        gemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gemBtnClicked();
            }
        });

        leaderBoardRecyclerView = view.findViewById(R.id.leader_board_recycler_view);
        leaderBoardListAdapter = new LeaderBoardListAdapter(options);
        leaderBoardRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        leaderBoardRecyclerView.setAdapter(leaderBoardListAdapter);
        animTextView = view.findViewById(R.id.anim_text_view);
        animTextView2 = view.findViewById(R.id.anim_text_view2);

        categoryNameTextView = view.findViewById(R.id.category_text_view);
        parentCatNameTextView = view.findViewById(R.id.parent_category_text_view);

//        Profile Image
        profileGameFinish = view.findViewById(R.id.pro_img_view_finish);

        categoryNameTextView.setText(categoryName);
        parentCatNameTextView.setText(parent);

        Picasso.get().load(photoURL).into(profileGameFinish);

        return view;
    }

    private void gemBtnClicked() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                callback.onActionClick("Saikat");
                dismiss();
            }
        },1000);

    }

    @Override
    public void onStart() {
        super.onStart();
        leaderBoardListAdapter.startListening();
    }



    @Override
    public void onStop() {
        super.onStop();
        leaderBoardListAdapter.stopListening();
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }


    public interface Callback{
        void onActionClick(String name);
    }
}
