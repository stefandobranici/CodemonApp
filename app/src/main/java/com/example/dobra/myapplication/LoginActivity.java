package com.example.dobra.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Environment;
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
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import pl.droidsonroids.gif.GifImageView;

import static android.graphics.Color.WHITE;

public class LoginActivity extends AppCompatActivity {
    public static Activity loginActivity;

    private LinearLayout screenLayout;
    private float scale;
    private Typeface cyberFont;
    private FirebaseAuth mAuth;
    private ImageButton settingsBtn;

    private static final String FILE_NAME = "currentuser.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        loginActivity = this;

        screenLayout = (LinearLayout) findViewById(R.id.loginlayout);

        scale = getResources().getDisplayMetrics().density;

        cyberFont = Typeface.createFromAsset(getAssets(), "font/Cyberverse.otf");

        writeData("");

        generateScreenElements();
    }


    //This method will generate all View elements that will be displayed on screen
    private void generateScreenElements(){
        //Top part of the screen
        GifImageView credentialsText = new GifImageView(this);
        credentialsText.setImageResource(R.drawable.logintext);

        int dpWidthInPx = (int) (360 * scale);
        int dpHeightInPx = (int) (150 * scale);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(dpWidthInPx, dpHeightInPx);
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
        credentialsText.setLayoutParams(layoutParams);

        screenLayout.addView(credentialsText);

        //Middle part of the screen

        //Show USER ID text
        ImageView userIdTxt = new ImageView(this);
        userIdTxt.setImageResource(R.drawable.useridtxt);

        dpWidthInPx = (int) (140 * scale);
        dpHeightInPx = (int) (20 * scale);

        layoutParams = new LinearLayout.LayoutParams(dpWidthInPx, dpHeightInPx);
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
        userIdTxt.setLayoutParams(layoutParams);

        screenLayout.addView(userIdTxt);


        //Place for user to enter user ID
        final EditText userIdBox = new EditText(this);
        userIdBox.setBackgroundResource(R.drawable.credentialsbox);


        userIdBox.setTypeface(cyberFont);
        userIdBox.setTextSize(24);
        userIdBox.setTextColor(WHITE);
        userIdBox.setPadding(10,5,0,5);


        dpWidthInPx = (int) (200 * scale);
        dpHeightInPx = (int) (45 * scale);

        layoutParams = new LinearLayout.LayoutParams(dpWidthInPx, dpHeightInPx);
        layoutParams.setMargins(0, dpHeightInPx/2, 0, dpHeightInPx/2);
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
        userIdBox.setLayoutParams(layoutParams);

        screenLayout.addView(userIdBox);

        //Show Password text field
        ImageView passwordTxt = new ImageView(this);
        passwordTxt.setImageResource(R.drawable.passwordtxt);

        dpWidthInPx = (int) (160 * scale);
        dpHeightInPx = (int) (30 * scale);

        layoutParams = new LinearLayout.LayoutParams(dpWidthInPx, dpHeightInPx);
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
        passwordTxt.setLayoutParams(layoutParams);

        screenLayout.addView(passwordTxt);

        //Place for user to insert password
        final EditText passwordBox = new EditText(this);
        passwordBox.setBackgroundResource(R.drawable.credentialsbox);

        passwordBox.setTextSize(24);
        passwordBox.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        passwordBox.setTextColor(WHITE);
        passwordBox.setTypeface(cyberFont);
        passwordBox.setPadding(10,5,0,5);

        dpWidthInPx = (int) (200 * scale);
        dpHeightInPx = (int) (45 * scale);

        layoutParams = new LinearLayout.LayoutParams(dpWidthInPx, dpHeightInPx);
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
        layoutParams.setMargins(0, dpHeightInPx/2, 0, 0);
        passwordBox.setLayoutParams(layoutParams);

        screenLayout.addView(passwordBox);

        //Bottom part of the screen

        //Login button
        ImageButton loginBtn = new ImageButton(this);

        loginBtn.setBackgroundResource(R.drawable.loginbtn2);
        dpWidthInPx = (int) (130 * scale);
        dpHeightInPx = (int) (40 * scale);

        layoutParams = new LinearLayout.LayoutParams(dpWidthInPx, dpHeightInPx);
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
        layoutParams.setMargins(0, dpHeightInPx, 0, 0);
        loginBtn.setLayoutParams(layoutParams);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId = userIdBox.getText().toString().trim();
                final String UID = userId;
                String password = passwordBox.getText().toString().trim();
                final String PW = password;

                if(TextUtils.isEmpty(userId)){
                    Toast.makeText(getApplicationContext(), "User ID field is empty!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(getApplicationContext(), "Password field is empty!", Toast.LENGTH_SHORT).show();
                    return;
                }

                userId = userId + "@bathspa.ac.uk";

                mAuth.signInWithEmailAndPassword(userId, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            writeData(UID +"\n" + PW);

                            Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_SHORT).show();

                            Intent mode_selector = new Intent("android.intent.action.ModeSelectorActivity");
                            startActivity(mode_selector);
                            MainActivity.mainActivity.finish();
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        screenLayout.addView(loginBtn);

        //Signup button
        ImageButton signupBtn = new ImageButton(this);

        signupBtn.setBackgroundResource(R.drawable.signupbtn);
        dpWidthInPx = (int) (130 * scale);
        dpHeightInPx = (int) (40 * scale);

        layoutParams = new LinearLayout.LayoutParams(dpWidthInPx, dpHeightInPx);
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
        layoutParams.setMargins(0, dpHeightInPx, 0, 0);
        signupBtn.setLayoutParams(layoutParams);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signup_intent = new Intent("android.intent.action.SignupActivity");
                startActivity(signup_intent);
            }
        });

        screenLayout.addView(signupBtn);
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

    private void setSettingsBtnOnClickListener(){
        settingsBtn = (ImageButton) findViewById(R.id.settingsbutton);
        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settings_intent = new Intent("android.intent.action.SettingsScreen");
                startActivity(settings_intent);
            }
        });
    }
}
