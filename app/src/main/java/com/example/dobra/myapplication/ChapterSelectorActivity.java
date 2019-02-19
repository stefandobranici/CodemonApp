package com.example.dobra.myapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ChapterSelectorActivity extends AppCompatActivity {

    private ImageButton variablesBtn, ifBtn, whileBtn, forBtn, arraysBtn, methodsBtn, classesBtn;

    private ImageButton settingsBtn;

    private FirebaseAuth mAuth;

    private FirebaseDatabase mDatabase;

    private DatabaseReference mChaptersReference;

    private DatabaseReference mLevelReference;

    private FirebaseDatabaseHelper mHelper;

    private List<Integer> chapterProgress;

    public static Integer chapterSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_chapter_selector);

        mAuth = FirebaseAuth.getInstance();

        mDatabase = FirebaseDatabase.getInstance();

        mHelper = new FirebaseDatabaseHelper();

        mHelper.generateUserLevels();

        String currentUser = mAuth.getCurrentUser().getUid();

/*        chapterProgress = new ArrayList<>();
        for(int i = 0 ; i < 1; i++){
            chapterProgress.add(0);
        }

        for(int i = 1 ; i < 2; i++) {
            Integer currentChapter = i;
            mChaptersReference = mDatabase.getReference("Users").child(currentUser).child("Chapters").child(currentChapter.toString());

            mChaptersReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        for(DataSnapshot keyNode : dataSnapshot.getChildren()){
                            Boolean completed = keyNode.getValue(Boolean.class);
                            if(completed) chapterProgress.set(0, chapterProgress.get(0)+1);
                        }
                    } else {
                        //Map<Integer, Boolean> newPost = new HashMap();
                        for(int i = 1; i < 22; i++){
                            Integer level = i;
                            DatabaseReference currentLevel = mChaptersReference.child(level.toString());
                            currentLevel.setValue(false);
                        }
                        //mChaptersReference.setValue(newPost);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }*/

        setUpButtons();
    }


    private void variablesButtonOnClickListener(){
        variablesBtn = (ImageButton) findViewById(R.id.variables);

        variablesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapview_intent = new Intent("android.intent.action.MapViewActivity");

                chapterSelected = 1;

                startActivity(mapview_intent);
            }
        });
    }

    private void ifButtonOnClickListener(){
        ifBtn = (ImageButton) findViewById(R.id.ifs);

        ifBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapview_intent = new Intent("android.intent.action.MapViewActivity");

                chapterSelected = 2;

                startActivity(mapview_intent);
            }
        });
    }

    private void whileButtonOnClickListener(){
        whileBtn = (ImageButton) findViewById(R.id.whiles);

        whileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapview_intent = new Intent("android.intent.action.MapViewActivity");

                chapterSelected = 3;

                startActivity(mapview_intent);
            }
        });
    }

    private void forButtonOnClickListener(){
        forBtn = (ImageButton) findViewById(R.id.fors);

        forBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapview_intent = new Intent("android.intent.action.MapViewActivity");

                chapterSelected = 4;

                startActivity(mapview_intent);
            }
        });
    }

    private void arraysButtonOnClickListener(){
        arraysBtn = (ImageButton) findViewById(R.id.arrays);

        arraysBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapview_intent = new Intent("android.intent.action.MapViewActivity");

                chapterSelected = 5;

                startActivity(mapview_intent);
            }
        });
    }

    private void methodsButtonOnClickListener(){
        methodsBtn = (ImageButton) findViewById(R.id.methods);

        methodsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapview_intent = new Intent("android.intent.action.MapViewActivity");

                chapterSelected = 6;

                startActivity(mapview_intent);
            }
        });
    }

    private void classesButtonOnClickListener(){
        classesBtn = (ImageButton) findViewById(R.id.classes);

        classesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapview_intent = new Intent("android.intent.action.MapViewActivity");

                chapterSelected = 7;

                startActivity(mapview_intent);
            }
        });
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

    private void setUpButtons(){
        variablesButtonOnClickListener();
        ifButtonOnClickListener();
        whileButtonOnClickListener();
        forButtonOnClickListener();
        arraysButtonOnClickListener();
        methodsButtonOnClickListener();
        classesButtonOnClickListener();

        setSettingsBtnOnClickListener();
    }
}
