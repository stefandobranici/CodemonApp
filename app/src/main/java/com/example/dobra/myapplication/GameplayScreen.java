package com.example.dobra.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class GameplayScreen extends AppCompatActivity {

    private RelativeLayout topscreen, middlescreen;
    private float scale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameplay_screen);

        topscreen = (RelativeLayout) findViewById(R.id.topscreenlayout);
        middlescreen = (RelativeLayout) findViewById(R.id.middlescreenlayout);

        scale = getResources().getDisplayMetrics().density;

        if(LevelAdapter.CURRENT_GAME_MISSION.colour_label == 2){
            ImageView image = new ImageView(this);
            image.setImageResource(R.drawable.trainer);

            int dpWidthInPx = (int) (360 * scale);
            int dpHeightInPx = (int) (180 * scale);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(dpWidthInPx, dpHeightInPx);
            image.setLayoutParams(layoutParams);

            topscreen.addView(image);

            TextView text = new TextView(this);

            text.setText("Hello! How are you doing!");
            text.setX(middlescreen.getX());
            text.setY(middlescreen.getY());

            middlescreen.addView(text);


        }
    }
}
