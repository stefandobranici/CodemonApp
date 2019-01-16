package com.example.dobra.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {
    private ImageButton login_button;
    private RelativeLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginButtonOnClickListener();
        //test();
    }

    public void loginButtonOnClickListener(){
        login_button = (ImageButton) findViewById(R.id.loginbtn);

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login_intent = new Intent("android.intent.action.ModeSelectorActivity");
                startActivity(login_intent);
                finish();
            }
        });
    }
/*
    public void test(){
        ImageButton test = new ImageButton(this);
        test.setBackgroundResource(R.drawable.boss);

        float scale = getResources().getDisplayMetrics().density;

        int dpWidthInPx2 = (int) (50 * scale);
        int dpHeightInPx2 = (int) (80 * scale);

        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(dpWidthInPx2, dpHeightInPx2);
        test.setLayoutParams(layoutParams2);

        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gameScreen = new Intent("android.intent.action.GameplayScreen");
                startActivity(gameScreen);
            }
        });

        test.setY(400);
        test.setX(400);


        layout = (RelativeLayout) findViewById(R.id.mainlayout);

        layout.addView(test);


    }*/
}
