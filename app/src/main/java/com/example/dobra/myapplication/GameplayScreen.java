package com.example.dobra.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class GameplayScreen extends AppCompatActivity {
    private Level level;

    private LinearLayout topLayout, firstHalfTopLayout, secondHalfTopLayout, middleLayout, bottomLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameplay_screen);

        topLayout = (LinearLayout) findViewById(R.id.topScreenLayout);

        firstHalfTopLayout = (LinearLayout) findViewById(R.id.topScreenLayoutFirstHalf);

        secondHalfTopLayout = (LinearLayout) findViewById(R.id.topScreenLayoutSecondHalf);

        middleLayout = (LinearLayout) findViewById(R.id.middleScreenLayout);

        bottomLayout = (LinearLayout) findViewById(R.id.bottomScreenLayout);

        level = CurrentUserInformation.getInstance().getLevelSelectedForPlay();

        if(level.isGreen()){
            drawScreenLearning();
        } else if(level.isViolette()) {
            drawScreenTraining();
        } else if(level.isRed()) {
            drawScreenBoss();
        }


    }

    private void drawScreenLearning(){
        ImageView trainer = new ImageView(this);

        trainer.setImageResource(R.drawable.trainer);

        topLayout.addView(trainer);
    }

    private void drawScreenTraining(){
        ImageView chiuplus = new ImageView(this);

        chiuplus.setImageResource(R.drawable.chiuplusback);
    }

    private void drawScreenBoss(){

    }
}
