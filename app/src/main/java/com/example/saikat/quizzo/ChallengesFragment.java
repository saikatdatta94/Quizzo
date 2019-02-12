package com.example.saikat.quizzo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChallengesFragment extends Fragment {


    public ChallengesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Toolbar toolbar = getActivity().findViewById(R.id.profile_toolbar);
        toolbar.getMenu().clear();
// Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_challenges, container, false);
    }

}
