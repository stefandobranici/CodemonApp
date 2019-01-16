package com.example.dobra.myapplication;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MapViewActivity extends AppCompatActivity {

    private RelativeLayout map;

    LevelAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_view);

        map = (RelativeLayout) findViewById(R.id.layout);

        adapter = new LevelAdapter(getApplicationContext());

        adapter.generateMap(map, ChapterSelectorActivity.gameStatus.getChapter(ChapterSelectorActivity.chapter_selected));

    }

    public static void startIntent(Intent intent){
        startIntent(intent);
    }
}
