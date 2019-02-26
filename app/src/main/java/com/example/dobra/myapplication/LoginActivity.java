package com.example.dobra.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import pl.droidsonroids.gif.GifImageView;

import static android.graphics.Color.WHITE;

public class LoginActivity extends AppCompatActivity {
    public static Activity loginActivity;

    private EditText loginCredentialsID2, loginCredentialsPassword2;

    private TextView logUserInButton, goToSignUpScreen;

    private FirebaseDatabase mDatabase;

    private FirebaseAuth mAuth;

    private DatabaseReference modeSelectorReference;

    private GifImageView loadingAnimation;

    private static final String FILE_NAME = "currentuser.txt";

    private String userID, userPW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        mDatabase = FirebaseDatabase.getInstance();

        loginActivity = this;

        loadingAnimation = (GifImageView) findViewById(R.id.loadingAnimation);

        initScreenElements();
    }


    //This method will generate all View elements that will be displayed on screen
    private void initScreenElements(){

        loginCredentialsID2 = (EditText) findViewById(R.id.loginCredentialsID2);

        loginCredentialsPassword2 = (EditText) findViewById(R.id.loginCredentialsPassword2);

        logUserInButton = (TextView) findViewById(R.id.logUserInButton);

        goToSignUpScreen = (TextView) findViewById(R.id.goToSignUpScreen);


        setLogUserInButtonOnClickListener();

        setGoToSignUpScreenOnClickListener();
    }

    private void setLogUserInButtonOnClickListener(){
        logUserInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingAnimation.setVisibility(View.VISIBLE);

                userID = loginCredentialsID2.getText().toString().trim();
                userPW = loginCredentialsPassword2.getText().toString().trim();

                if(checkUserInput()) {

                    userID = userID + "@bathspa.ac.uk";

                    mAuth.signInWithEmailAndPassword(userID, userPW).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                loginSuccessful();

                            } else {
                                loadingAnimation.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
    }

    private void setGoToSignUpScreenOnClickListener(){
        goToSignUpScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signup_intent = new Intent("android.intent.action.SignupActivity");
                startActivity(signup_intent);
            }
        });
    }

    private boolean checkUserInput(){
        if(TextUtils.isEmpty(userID)){
            Toast.makeText(getApplicationContext(), "User ID field is empty!", Toast.LENGTH_SHORT).show();
            loadingAnimation.setVisibility(View.GONE);
            return false;
        }
        if(TextUtils.isEmpty(userPW)){
            Toast.makeText(getApplicationContext(), "Password field is empty!", Toast.LENGTH_SHORT).show();
            loadingAnimation.setVisibility(View.GONE);
            return false;
        }

        return true;
    }

    private void writeData(String data){
        FileOutputStream fos = null;

        try{
            fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
            fos.write(data.getBytes());
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos!=null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void loginSuccessful(){
        CurrentUserInformation.getInstance().getUserProgressionStatus();


        writeData(userID +"\n" + userPW);

        String currentUser = mAuth.getCurrentUser().getUid();

        DatabaseReference lastLoggedInRef = mDatabase.getReference("Users").child(currentUser).child("User Information").child("LastLoggedIn");

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        lastLoggedInRef.setValue(dateFormat.format(date));

        modeSelectorReference = mDatabase.getReference("Users").child(currentUser).child("User Information").child("Mode");

        modeSelectorReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String modeSelected = dataSnapshot.getValue(String.class);


                if(!TextUtils.isEmpty(modeSelected)){
                    Intent gamemode_intent = new Intent("android.intent.action.MenuScreenActivity");
                    startActivity(gamemode_intent);
                    MainScreenActivity.mainActivity.finish();
                    loadingAnimation.setVisibility(View.GONE);
                    finish();
                } else {
                    Intent mode_selector = new Intent("android.intent.action.ModeSelectorActivity");
                    startActivity(mode_selector);
                    MainScreenActivity.mainActivity.finish();
                    loadingAnimation.setVisibility(View.GONE);
                    finish();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
