package com.example.saikat.quizzo;


import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements Toolbar.OnMenuItemClickListener{






//    User Variables
    public String emailId;
    public String name;
    public String profileImageURL;
    public TextView emailText;
    public ImageView profileImageView;

    public ProfileFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile,container,false);//Setting up view for getting View elements from Fragments

        profileImageView = view.findViewById(R.id.profile_image);

//      Adding toolbars,find views by Id and setting are quite different in fragments Code to add toolbar into fragments
        Toolbar toolbar = getActivity().findViewById(R.id.profile_toolbar);
        toolbar.getMenu().clear();
        toolbar.inflateMenu(R.menu.profile_toolbar_items);
        toolbar.setOnMenuItemClickListener(this);
        toolbar.setTitle("Profile");

//        Receiving Bundle arguments(Profile Details) from main activity
        if (getArguments()!=null){
             name = getArguments().getString("name");
             emailId = getArguments().getString("email");
             profileImageURL = getArguments().getString("dpURL");
        }

         emailText = view.findViewById(R.id.email_view);
         emailText.setText(emailId);

         loadProfileImage();

        // Inflate the layout for this fragment
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.settings:
                Toast.makeText(this.getContext(), "Settings Clicked", Toast.LENGTH_SHORT).show();
                return true;

        }
        return true;
    }

    public void setTextViewText(CharSequence charEmail){
//        this.charEmail = charEmail;
    }

    public void loadProfileImage(){
        ImageRequest imageRequest = new ImageRequest(profileImageURL,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        //Making the bitmap rounded and setting the image view
                        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(),response);
                        roundedBitmapDrawable.setCircular(true);
                        profileImageView.setImageDrawable(roundedBitmapDrawable);
                    }
                }, 0, 0, null,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("PhotoError","Photo couldn't be loaded");
                    }
                });
        MySingleton.getInstance(getActivity()).addToRequestQueue(imageRequest);



    }
}
