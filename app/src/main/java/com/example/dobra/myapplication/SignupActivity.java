package com.example.dobra.myapplication;

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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

import org.w3c.dom.Text;

import pl.droidsonroids.gif.GifImageView;

import static android.graphics.Color.WHITE;

public class SignupActivity extends AppCompatActivity {

    private LinearLayout screenLayout;
    private float scale;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();

        screenLayout = (LinearLayout) findViewById(R.id.signuplayout);

        scale = getResources().getDisplayMetrics().density;

        Typeface cyberFont = Typeface.createFromAsset(getAssets(), "font/Cyberverse.otf");



        //Set gif on top of screen
        GifImageView credentialsText = new GifImageView(this);
        credentialsText.setImageResource(R.drawable.logintext);

        int dpWidthInPx = (int) (360 * scale);
        int dpHeightInPx = (int) (150 * scale);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(dpWidthInPx, dpHeightInPx);
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
        credentialsText.setLayoutParams(layoutParams);

        screenLayout.addView(credentialsText);

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
        dpHeightInPx = (int) (20 * scale);

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
        layoutParams.setMargins(0, dpHeightInPx/2, 0, dpHeightInPx/2);
        passwordBox.setLayoutParams(layoutParams);

        screenLayout.addView(passwordBox);

        //Show Password text field
        ImageView passwordTxt2 = new ImageView(this);
        passwordTxt2.setImageResource(R.drawable.confirmtxt);

        dpWidthInPx = (int) (260 * scale);
        dpHeightInPx = (int) (20 * scale);

        layoutParams = new LinearLayout.LayoutParams(dpWidthInPx, dpHeightInPx);
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
        passwordTxt2.setLayoutParams(layoutParams);

        screenLayout.addView(passwordTxt2);

        //Place for user to insert password
        final EditText passwordBox2 = new EditText(this);
        passwordBox2.setBackgroundResource(R.drawable.credentialsbox);

        passwordBox2.setTextSize(24);
        passwordBox2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        passwordBox2.setTextColor(WHITE);
        passwordBox2.setTypeface(cyberFont);
        passwordBox2.setPadding(10,5,0,5);

        dpWidthInPx = (int) (200 * scale);
        dpHeightInPx = (int) (45 * scale);

        layoutParams = new LinearLayout.LayoutParams(dpWidthInPx, dpHeightInPx);
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
        layoutParams.setMargins(0, dpHeightInPx/2, 0, 0);
        passwordBox2.setLayoutParams(layoutParams);

        screenLayout.addView(passwordBox2);

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

                String userId = userIdBox.getText().toString().trim();
                String password = passwordBox.getText().toString().trim();
                String confirmPassword = passwordBox2.getText().toString().trim();

                if(TextUtils.isEmpty(userId)){
                    Toast.makeText(getApplicationContext(), "ID Field cannot be empty!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(userId.length() < 4){
                    Toast.makeText(getApplicationContext(), "ID must be at least four characters long!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)){
                    Toast.makeText(getApplicationContext(), "Password Fields cannot be empty!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(password.length() < 4){
                    Toast.makeText(getApplicationContext(), "Password must be at least four characters long!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!password.equals(confirmPassword)){
                    Toast.makeText(getApplicationContext(), "Confirmation password does not match!", Toast.LENGTH_SHORT).show();
                    return;
                }

                userId = userId+"@bathspa.ac.uk";

                mAuth.createUserWithEmailAndPassword(userId, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Signed Up Successfuly!", Toast.LENGTH_SHORT).show();

                            Intent login_intent = new Intent("android.intent.action.LoginActivity");
                            LoginActivity.loginActivity.finish();
                            startActivity(login_intent);
                            finish();
                        } else {
                            if(task.getException() instanceof FirebaseAuthException){
                                Toast.makeText(getApplicationContext(), "User ID already registered!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });


            }
        });
        screenLayout.addView(signupBtn);
    }
}
