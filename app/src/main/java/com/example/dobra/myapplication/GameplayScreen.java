package com.example.dobra.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class GameplayScreen extends AppCompatActivity {

    private RelativeLayout gamescreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameplay_screen);

        gamescreen = (RelativeLayout) findViewById(R.id.gamescreenlayout);

        if(LevelAdapter.CURRENT_GAME_MISSION.colour_label == 2){
            ImageView image = new ImageView(this);
            image.setImageResource(R.drawable.trainer);

            image.setX(300);
            image.setY(300);

            gamescreen.addView(image);
        }
    }
}
