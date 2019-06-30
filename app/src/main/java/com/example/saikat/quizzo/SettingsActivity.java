package com.example.saikat.quizzo;

import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SettingsActivity extends AppCompatActivity {




    int[] id = {R.id.upgrade_to_premium,R.id.change_pwd,R.id.set_goals,R.id.log_out,R.id.play_store,
            R.id.twitter,R.id.facebook,R.id.help,R.id.suggest_feature,R.id.feedback,R.id.terms_conditions,
            R.id.privacy_policy};

    View premium;
    View changePwd;
    View setGoals ;
    View logOut  ;
    View playStoreRating ;
    View followOnTwitter ;
    View likeOnFacebook ;
    View help ;
    View suggestFeature ;
    View feedBack ;
    View tAndC ;
    View privacyPolicy ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        if (getIntent()!=null){

        }

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();



        Toolbar toolbar = findViewById(R.id.settings_toolbar);
        setSupportActionBar(toolbar);
//       Enabling Back Button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
//       Setting toolbar title to settings
        toolbar.setTitle("Settings");

        premium = findViewById(R.id.upgrade_to_premium);
        changePwd = findViewById(R.id.change_pwd);
        setGoals = findViewById(R.id.set_goals);
        logOut = findViewById(R.id.log_out);
        playStoreRating = findViewById(R.id.play_store);
        followOnTwitter = findViewById(R.id.twitter);
        likeOnFacebook = findViewById(R.id.facebook);
        help = findViewById(R.id.help);
        suggestFeature = findViewById(R.id.suggest_feature);
        feedBack = findViewById(R.id.feedback);
        tAndC = findViewById(R.id.terms_conditions);
        privacyPolicy = findViewById(R.id.privacy_policy);

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user!=null){
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(getApplicationContext(),SignInActivity.class);
                    finishAffinity();
                    startActivity(intent);
                    finish();
                    Toast.makeText(SettingsActivity.this, "Logged Out", Toast.LENGTH_SHORT).show();
                }else {
                    Log.i("User","Couldn't get Email");
                }
            }
        });



        View[] viewArray = {premium,changePwd,setGoals,logOut,playStoreRating,followOnTwitter,
                likeOnFacebook,help,suggestFeature,feedBack,tAndC,privacyPolicy};

        String[] settingText = {"Upgrade to Premium","Change Password","Set Goal","Log Out",
                "Rate us on the Play Store","Follow us on Twitter","Like us on Facebook","Help",
                "Suggest a Feature","Feedback","Terms and Conditions","Privacy Policy"};

        int[] iconResource = {R.drawable.round_settings_black_36dp,R.drawable.change_pwd,R.drawable.bullseye, R.drawable.logout,
        R.drawable.rate_us,R.drawable.twitter,R.drawable.facebook,R.drawable.help,
                R.drawable.suggest_feature, R.drawable.feedback,R.drawable.terms,R.drawable.privacy};

        for (int i = 0; i < viewArray.length; i++) {
            changeSettingIcons(viewArray[i],settingText[i],iconResource[i]);
        }


    }

    public void changeSettingIcons(View view,String settingText,int iconResource){
        ImageView settingIcon = view.findViewById(R.id.setting_page_icon);
        settingIcon.setImageResource(iconResource);
        TextView settingName = view.findViewById(R.id.setting_text);
        settingName.setText(settingText);
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(SettingsActivity.this, "I was Clicked", Toast.LENGTH_SHORT).show();
//            }
//        });
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


}
