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

    public static Integer chapter_selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_chapter_selector);

        variablesButtonOnClickListener();
        ifButtonOnClickListener();
        whileButtonOnClickListener();
        forButtonOnClickListener();
        arraysButtonOnClickListener();
        methodsButtonOnClickListener();
        classesButtonOnClickListener();
    }

    public void variablesButtonOnClickListener(){
        variablesBtn = (ImageButton) findViewById(R.id.variables);

        variablesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapview_intent = new Intent("android.intent.action.MapViewActivity");

                chapter_selected = 1;

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

                chapter_selected = 2;

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

                chapter_selected = 3;

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

                chapter_selected = 4;

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

                chapter_selected = 5;

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

                chapter_selected = 6;

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

                chapter_selected = 7;

                startActivity(mapview_intent);
            }
        });
    }
}
