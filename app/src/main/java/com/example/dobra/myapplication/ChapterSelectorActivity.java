package com.example.dobra.myapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;
import java.util.List;

public class ChapterSelectorActivity extends AppCompatActivity {

    private ImageButton variablesBtn, ifBtn, whileBtn, forBtn, arraysBtn, methodsBtn, classesBtn;

    public static int chapter_selected;

    public static GameStatus gameStatus;

    private DatabaseReference dbLevels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_chapter_selector);

        gameStatus = new GameStatus();

        for(int i = 0; i < 7; i++) {

            LinkedList<Level> levelList = new LinkedList<>();

            for (int j = 0; j < 21; j++) {
                Level level = new Level();
                level.chapter_id = i;
                level.mission_id = j;
                level.colour_label = (int) (Math.random() * 4 + 1);
                level.completed = false;

                levelList.add(level);
            }

            gameStatus.addChapter(i, levelList);
        }

        variablesButtonOnClickListener();
        ifButtonOnClickListener();
        whileButtonOnClickListener();
        forButtonOnClickListener();
        arraysButtonOnClickListener();
        methodsButtonOnClickListener();
        classesButtonOnClickListener();


/*        Query query = FirebaseDatabase.getInstance().getReference("levels").orderByChild("chapter_id").equalTo("variables");
        query.addListenerForSingleValueEvent(valueEventListener);*/
    }

    public void variablesButtonOnClickListener(){
        variablesBtn = (ImageButton) findViewById(R.id.variables);

        variablesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapview_intent = new Intent("android.intent.action.MapViewActivity");

                chapter_selected = 0;

                startActivity(mapview_intent);
            }
        });
    }

    public void ifButtonOnClickListener(){
        ifBtn = (ImageButton) findViewById(R.id.ifs);

        ifBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapview_intent = new Intent("android.intent.action.MapViewActivity");

                chapter_selected = 1;

                startActivity(mapview_intent);
            }
        });
    }

    public void whileButtonOnClickListener(){
        whileBtn = (ImageButton) findViewById(R.id.whiles);

        whileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapview_intent = new Intent("android.intent.action.MapViewActivity");

                chapter_selected = 2;

                startActivity(mapview_intent);
            }
        });
    }

    public void forButtonOnClickListener(){
        forBtn = (ImageButton) findViewById(R.id.fors);

        forBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapview_intent = new Intent("android.intent.action.MapViewActivity");

                chapter_selected = 3;

                startActivity(mapview_intent);
            }
        });
    }

    public void arraysButtonOnClickListener(){
        arraysBtn = (ImageButton) findViewById(R.id.arrays);

        arraysBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapview_intent = new Intent("android.intent.action.MapViewActivity");

                chapter_selected = 4;

                startActivity(mapview_intent);
            }
        });
    }

    public void methodsButtonOnClickListener(){
        methodsBtn = (ImageButton) findViewById(R.id.methods);

        methodsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapview_intent = new Intent("android.intent.action.MapViewActivity");

                chapter_selected = 5;

                startActivity(mapview_intent);
            }
        });
    }

    public void classesButtonOnClickListener(){
        classesBtn = (ImageButton) findViewById(R.id.classes);

        classesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapview_intent = new Intent("android.intent.action.MapViewActivity");

                chapter_selected = 6;

                startActivity(mapview_intent);
            }
        });
    }

/*    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            levelList.clear();
            if(dataSnapshot.exists()) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    Level level = snapshot.getValue(Level.class);
                    levelList.add(level);
                }

                adapter.generateMap(map,  levelList);
            }


        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };*/
}
