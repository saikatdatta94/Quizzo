package com.example.saikat.quizzo;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;

    private HomeFragment homeFragment;
    private ChallengesFragment challengesFragment;
    private ProfileFragment profileFragment;

//    User data
    public String emailId;
    public String fullName;
    public String profilePhotoURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Receive data from LoginActivity
        if (getIntent()!= null){
            emailId = getIntent().getStringExtra("email");
            fullName = getIntent().getStringExtra("userName");
            profilePhotoURL = getIntent().getStringExtra("profile_photo_URL");
            Toast.makeText(this, "email: "+emailId+"fullName: "+fullName+ "URL: "+profilePhotoURL, Toast.LENGTH_LONG).show();

        }

        Toolbar toolbar = findViewById(R.id.profile_toolbar);
        setSupportActionBar(toolbar);


        mMainNav = findViewById(R.id.bottom_nav);
        mMainFrame = findViewById(R.id.main_frame);

        homeFragment = new HomeFragment();
        challengesFragment = new ChallengesFragment();
        profileFragment = new ProfileFragment();




        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.item_home:
                        setFragment(homeFragment);
                        return true;
                    case R.id.item_challenges:
                        setFragment(challengesFragment);
                        return true;
                    case R.id.item_profile:
                        setFragment(profileFragment);
                        passDataToProfile();
                        return true;
                        default:
                            return false;
                }
            }
        });

    }

    private void setFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame,fragment);
        fragmentTransaction.commit();
    }

    public void passDataToProfile(){
        Bundle bundleArgument = new Bundle();
        bundleArgument.putString("name",fullName);
        bundleArgument.putString("email",emailId);
        bundleArgument.putString("dpURL",profilePhotoURL);

        profileFragment.setArguments(bundleArgument);
    }


}
