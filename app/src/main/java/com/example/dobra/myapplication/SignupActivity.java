package com.example.dobra.myapplication;

import android.content.Intent;
import android.graphics.Typeface;
import android.provider.ContactsContract;
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
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import pl.droidsonroids.gif.GifImageView;

import static android.graphics.Color.WHITE;

public class SignupActivity extends AppCompatActivity {

    private EditText loginCredentialsID, loginCredentialsPassword, loginCredentialsConfirmPassword;

    private TextView signUserUpButton, goToLogInScreen;

    private FirebaseAuth mAuth;

    private FirebaseDatabase mDatabase;

    private GifImageView loadingAnimation;

    private String userID, userPW, confirmPassword;

    private static final String FILE_NAME = "currentuser.txt";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();

        mDatabase = FirebaseDatabase.getInstance();

        loadingAnimation = (GifImageView) findViewById(R.id.loadingAnimation);

        initScreenElements();
    }


    private void initScreenElements(){

        loginCredentialsID = (EditText) findViewById(R.id.loginCredentialsID);

        loginCredentialsPassword = (EditText) findViewById(R.id.loginCredentialsPassword);

        loginCredentialsConfirmPassword = (EditText) findViewById(R.id.loginCredentialsConfirmPassword);

        signUserUpButton = (TextView) findViewById(R.id.signUserUpButton);

        goToLogInScreen = (TextView) findViewById(R.id.goToLogInScreen);


        setSignUserUpButtonOnClickListener();

        setGoToLogInScreenOnClickListener();

    }

    private void setSignUserUpButtonOnClickListener(){
        signUserUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingAnimation.setVisibility(View.VISIBLE);

                userID = loginCredentialsID.getText().toString().trim();
                userPW = loginCredentialsPassword.getText().toString().trim();
                confirmPassword = loginCredentialsConfirmPassword.getText().toString().trim();

                if(checkUserInput()) {

                    userID = userID + "@bathspa.ac.uk";

                    mAuth.createUserWithEmailAndPassword(userID, userPW).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Signed Up Successfully! Logging " + userID + " ID in...", Toast.LENGTH_SHORT).show();

                                logUserIn();
                            } else {
                                if (task.getException() instanceof FirebaseAuthException) {
                                    loadingAnimation.setVisibility(View.GONE);
                                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
                }
            }
        });
    }

    private void setGoToLogInScreenOnClickListener(){
        goToLogInScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToLogIn = new Intent("android.intent.action.LoginActivity");
                LoginActivity.loginActivity.finish();
                startActivity(goToLogIn);
                finish();
            }
        });
    }

    private boolean checkUserInput(){
        if(TextUtils.isEmpty(userID)){
            Toast.makeText(getApplicationContext(), "ID Field cannot be empty!", Toast.LENGTH_SHORT).show();
            loadingAnimation.setVisibility(View.GONE);
            return false;
        }

        if(userID.length() < 4){
            Toast.makeText(getApplicationContext(), "ID must be at least four characters long!", Toast.LENGTH_SHORT).show();
            loadingAnimation.setVisibility(View.GONE);
            return false;
        }

        if(TextUtils.isEmpty(userPW) || TextUtils.isEmpty(confirmPassword)){
            Toast.makeText(getApplicationContext(), "Password Fields cannot be empty!", Toast.LENGTH_SHORT).show();
            loadingAnimation.setVisibility(View.GONE);
            return false;
        }

        if(userPW.length() < 6){
            Toast.makeText(getApplicationContext(), "Password must be at least six characters long!", Toast.LENGTH_SHORT).show();
            loadingAnimation.setVisibility(View.GONE);
            return false;
        }

        if(!userPW.equals(confirmPassword)){
            Toast.makeText(getApplicationContext(), "Confirmation password does not match!", Toast.LENGTH_SHORT).show();
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

    private void logUserIn(){
        mAuth.signInWithEmailAndPassword(userID, userPW).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    loginSuccessful();
                } else {
                    loadingAnimation.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void loginSuccessful(){
        CurrentUserInformation.getInstance().setUpUser(userID);

        CurrentUserInformation.getInstance().getUserProgressionStatus();

        writeData(userID +"\n" + userPW);

        String currentUser = mAuth.getCurrentUser().getUid();

        DatabaseReference lastLoggedInRef = mDatabase.getReference("Users").child(currentUser).child("User Information").child("LastLoggedIn");

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        lastLoggedInRef.setValue(dateFormat.format(date));

        Intent login_intent = new Intent("android.intent.action.ModeSelectorActivity");

        LoginActivity.loginActivity.finish();

        MainScreenActivity.mainActivity.finish();

        loadingAnimation.setVisibility(View.GONE);

        startActivity(login_intent);

        finish();
    }
}
