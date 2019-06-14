package com.example.saikat.quizzo;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignInActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    public static final String USER_ID = "userId";
    public static final String EMAIL = "email";
    public static final String IS_PREMIUM = "isPremium";
    public static final String PROFILE_IMAGE = "profileImage";
    public static final String GEM_NUMBER = "gems";
    private Button googleSignInButton;
    private static final int PERMISSION_SIGN_IN = 1;
    GoogleSignInClient mGoogleSignInClient;
    GoogleApiClient mGoogleApiClient;
    private FirebaseAuth firebaseAuth;

//    Google User Details
    public String email;
    public String userName;
    public Uri profilePhotoURL;
    public String userId;

//    FireStore Document ref
    FirebaseFirestore db = FirebaseFirestore.getInstance();



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == PERMISSION_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()){
                GoogleSignInAccount account = result.getSignInAccount();
                String idToken = account.getIdToken();

                AuthCredential credential = GoogleAuthProvider.getCredential(idToken,null);
                firebaseAuthWithGoogle(credential);

            }else {
                Log.e("Error","Login Failed");
                Log.e("Error",result.getStatus().getStatusMessage());
            }

        }
    }


    private void firebaseAuthWithGoogle(final AuthCredential credential) {
        firebaseAuth.signInWithCredential(credential)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        // Start New activity and pass email

                        Toast.makeText(SignInActivity.this, "email:"+authResult.getUser().getEmail(), Toast.LENGTH_LONG).show();
                        Log.i("Login","email:"+authResult.getUser().getEmail());

                        email = authResult.getUser().getEmail();
                        userName = authResult.getUser().getDisplayName();
                        profilePhotoURL = authResult.getUser().getPhotoUrl();
                        userId = authResult.getUser().getUid();
                        createUser();


                        //Redirecting user to Main Activity and Passing the user data
                        Intent intent = new Intent(SignInActivity.this,MainActivity.class); //Creating Intent and Passing Login Creds
                        intent.putExtra("email",email);
                        intent.putExtra("userName",userName);
                        intent.putExtra("profile_photo_URL",profilePhotoURL.toString());
                        intent.putExtra("uId", userId);
                        startActivity(intent);
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SignInActivity.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        configureGoogleSignIn();

//        If user is already Logged in redirect them to mainActivity
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user!=null){
            email = user.getEmail();
            userName = user.getDisplayName();
            profilePhotoURL = user.getPhotoUrl();
            userId = user.getUid();



            //Redirecting user to Main Activity and Passing the user data
            Intent intent = new Intent(SignInActivity.this,MainActivity.class); //Creating Intent and Passing Login Creds
            intent.putExtra("email",email);
            intent.putExtra("userName",userName);
            intent.putExtra("profile_photo_URL",profilePhotoURL.toString());
            intent.putExtra("uId", userId);
            startActivity(intent);
            finish();
        }

        firebaseAuth = FirebaseAuth.getInstance();
        googleSignInButton = findViewById(R.id.google_sign_in);


        googleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInWithGoogle();
            }
        });

    }


    private void signInWithGoogle() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent,PERMISSION_SIGN_IN);
    }





    private void configureGoogleSignIn(){
        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.google_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,options)
                .build();

        mGoogleApiClient.connect();

    }




    //If connection Fails
    
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, ""+connectionResult.getErrorMessage(), Toast.LENGTH_SHORT).show();
    }


    public void createUser(){
        Map<String,Object> user = new HashMap<String, Object>();
        user.put(USER_ID,userId);
        user.put(EMAIL,email);
        user.put(IS_PREMIUM,0);
        user.put(PROFILE_IMAGE,profilePhotoURL.toString());
        user.put(GEM_NUMBER,100);
        db.collection("users").document(userId).set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(SignInActivity.this, "User Added", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("TAG","Error",e);
            }
        });

    }
}