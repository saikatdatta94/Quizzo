package com.example.saikat.quizzo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements Toolbar.OnMenuItemClickListener{

    public String emailId;

    public ProfileFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//      Adding toolbars,find views by Id and setting are quite different in fragments Code to add toolbar into fragments



        Toolbar toolbar = getActivity().findViewById(R.id.profile_toolbar);
        toolbar.getMenu().clear();
        toolbar.inflateMenu(R.menu.profile_toolbar_items);
        toolbar.setOnMenuItemClickListener(this);
        toolbar.setTitle("Profile");


            
        






        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);

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
}
