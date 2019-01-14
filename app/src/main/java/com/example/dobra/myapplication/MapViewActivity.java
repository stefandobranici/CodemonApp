package com.example.dobra.myapplication;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

public class MapViewActivity extends AppCompatActivity {

    private RelativeLayout map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_view);

        generateMap();

    }

    public void generateMap(){
        map = (RelativeLayout) findViewById(R.id.layout);

        ImageView image = new ImageView(getApplicationContext());
        image.setImageResource(R.drawable.bluetile);

        map.addView(image);

    }
}
