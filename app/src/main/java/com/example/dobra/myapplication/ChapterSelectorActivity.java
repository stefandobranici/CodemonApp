package com.example.dobra.myapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ChapterSelectorActivity extends AppCompatActivity {

    private ImageButton variablesBtn, ifBtn, whileBtn, forBtn, arraysBtn, methodsBtn, classesBtn;

    private Boolean chapterLocked2, chapterLocked3, chapterLocked4, chapterLocked5, chapterLocked6, chapterLocked7;

    private TextView variableProgression, ifProgression, whileProgression, forProgression, arraysProgression, methodsProgression, classesProgression;

    private ImageView settingsBtn, profileBtn, friendsBtn;

    private CurrentUserInformation currentUserInformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_chapter_selector);

        currentUserInformation = CurrentUserInformation.getInstance();

        currentUserInformation.getUserProgressionStatus(this);

        setUpLayout();

    }


    private void variablesButtonOnClickListener(){
        variablesBtn = (ImageButton) findViewById(R.id.variables);

        variablesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapview_intent = new Intent("android.intent.action.MapViewActivity");

                currentUserInformation.setChapterSelected(1);

                startActivity(mapview_intent);
            }
        });
    }

    private void ifButtonOnClickListener(){
        ifBtn = (ImageButton) findViewById(R.id.ifs);

        if(chapterLocked2){
            ifBtn.setBackgroundResource(R.drawable.ifworldlocked);
        } else {

            ifBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent mapview_intent = new Intent("android.intent.action.MapViewActivity");

                    currentUserInformation.setChapterSelected(2);

                    startActivity(mapview_intent);
                }
            });
        }
    }

    private void whileButtonOnClickListener(){
        whileBtn = (ImageButton) findViewById(R.id.whiles);

        if(chapterLocked3){
            whileBtn.setBackgroundResource(R.drawable.whilewaitlocked);
        } else {

            whileBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent mapview_intent = new Intent("android.intent.action.MapViewActivity");

                    currentUserInformation.setChapterSelected(3);

                    startActivity(mapview_intent);
                }
            });
        }
    }

    private void forButtonOnClickListener(){
        forBtn = (ImageButton) findViewById(R.id.fors);

        if(chapterLocked4){
            forBtn.setBackgroundResource(R.drawable.foreverlocked);
        } else {

            forBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent mapview_intent = new Intent("android.intent.action.MapViewActivity");

                    currentUserInformation.setChapterSelected(4);

                    startActivity(mapview_intent);
                }
            });
        }
    }

    private void arraysButtonOnClickListener(){
        arraysBtn = (ImageButton) findViewById(R.id.arrays);

        if(chapterLocked5){
            arraysBtn.setBackgroundResource(R.drawable.arrayslocked);
        } else {

            arraysBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent mapview_intent = new Intent("android.intent.action.MapViewActivity");

                    currentUserInformation.setChapterSelected(5);

                    startActivity(mapview_intent);
                }
            });
        }
    }

    private void methodsButtonOnClickListener(){
        methodsBtn = (ImageButton) findViewById(R.id.methods);

        if(chapterLocked6){
            methodsBtn.setBackgroundResource(R.drawable.methodslocked);
        } else {
            methodsBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent mapview_intent = new Intent("android.intent.action.MapViewActivity");

                    currentUserInformation.setChapterSelected(6);

                    startActivity(mapview_intent);
                }
            });
        }
    }

    private void classesButtonOnClickListener(){
        classesBtn = (ImageButton) findViewById(R.id.classes);

        if(chapterLocked7){
            classesBtn.setBackgroundResource(R.drawable.classeslocked);
        } else {

            classesBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent mapview_intent = new Intent("android.intent.action.MapViewActivity");

                    currentUserInformation.setChapterSelected(7);

                    startActivity(mapview_intent);
                }
            });
        }
    }

    private void setSettingsBtnOnClickListener(){
        settingsBtn = (ImageView) findViewById(R.id.settingsbutton);
        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settings_intent = new Intent("android.intent.action.SettingsScreen");
                startActivity(settings_intent);
            }
        });
    }

    private void setProfileBtnOnClickListener(){
        profileBtn = (ImageView) findViewById(R.id.profilebutton);
        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settings_intent = new Intent("android.intent.action.ProfileViewActivity");
                startActivity(settings_intent);
            }
        });
    }

    private void setFriendsBtnOnClickListener(){
        friendsBtn = (ImageView) findViewById(R.id.friendsbutton);
        friendsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settings_intent = new Intent("android.intent.action.FriendsView");
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
        setProfileBtnOnClickListener();
        setFriendsBtnOnClickListener();
    }

    public void setUpLayout(){
        String defaultProgression = "0/21";

        chapterLocked2 = true;
        chapterLocked3 = true;
        chapterLocked4 = true;
        chapterLocked5 = true;
        chapterLocked6 = true;
        chapterLocked7 = true;

        variableProgression = (TextView) findViewById(R.id.variablesProgression);

        if(currentUserInformation.progressionOfChapters.containsKey(1)){
            if(currentUserInformation.progressionOfChapters.get(1).equals(currentUserInformation.numberOfMissionsInChapter.get(1))){
                chapterLocked2 = false;
            }
            variableProgression.setText(String.format(currentUserInformation.progressionOfChapters.get(1).toString()+"/"+currentUserInformation.numberOfMissionsInChapter.get(1).toString()));
        } else {
            variableProgression.setText(defaultProgression);
        }

        ifProgression = (TextView) findViewById(R.id.ifsProgression);

        if(currentUserInformation.progressionOfChapters.containsKey(2)){
            if(currentUserInformation.progressionOfChapters.get(2).equals(currentUserInformation.numberOfMissionsInChapter.get(2))){
                chapterLocked3 = false;
            }
            ifProgression.setText(String.format(currentUserInformation.progressionOfChapters.get(2).toString()+"/"+currentUserInformation.numberOfMissionsInChapter.get(2).toString()));
        } else {
            ifProgression.setText(defaultProgression);
        }

        whileProgression = (TextView) findViewById(R.id.whilesProgression);

        if(currentUserInformation.progressionOfChapters.containsKey(3)){
            if(currentUserInformation.progressionOfChapters.get(3).equals(currentUserInformation.numberOfMissionsInChapter.get(3))){
                chapterLocked4 = false;
            }
            whileProgression.setText(String.format(currentUserInformation.progressionOfChapters.get(3).toString()+"/"+currentUserInformation.numberOfMissionsInChapter.get(3).toString()));
        } else {
            whileProgression.setText(defaultProgression);
        }

        forProgression = (TextView) findViewById(R.id.forsProgression);

        if(currentUserInformation.progressionOfChapters.containsKey(4)){
            if(currentUserInformation.progressionOfChapters.get(4).equals(currentUserInformation.numberOfMissionsInChapter.get(4))){
                chapterLocked5 = false;
            }
            forProgression.setText(String.format(currentUserInformation.progressionOfChapters.get(4).toString()+"/"+currentUserInformation.numberOfMissionsInChapter.get(4).toString()));
        } else {
            forProgression.setText(defaultProgression);
        }

        arraysProgression = (TextView) findViewById(R.id.arraysProgression);

        if(currentUserInformation.progressionOfChapters.containsKey(5)){
            if(currentUserInformation.progressionOfChapters.get(5).equals(currentUserInformation.numberOfMissionsInChapter.get(5))){
                chapterLocked6 = false;
            }
            arraysProgression.setText(String.format(currentUserInformation.progressionOfChapters.get(5).toString()+"/"+currentUserInformation.numberOfMissionsInChapter.get(5).toString()));
        } else {
            arraysProgression.setText(defaultProgression);
        }

        methodsProgression = (TextView) findViewById(R.id.methodsProgression);

        if(currentUserInformation.progressionOfChapters.containsKey(6)){
            if(currentUserInformation.progressionOfChapters.get(6).equals(currentUserInformation.numberOfMissionsInChapter.get(6))){
                chapterLocked7 = false;
            }
            methodsProgression.setText(String.format(currentUserInformation.progressionOfChapters.get(6).toString()+"/"+currentUserInformation.numberOfMissionsInChapter.get(6).toString()));
        } else {
            methodsProgression.setText(defaultProgression);
        }

        classesProgression = (TextView) findViewById(R.id.classesProgression);

        if(currentUserInformation.progressionOfChapters.containsKey(7)){
            classesProgression.setText(String.format(currentUserInformation.progressionOfChapters.get(7).toString()+"/"+currentUserInformation.numberOfMissionsInChapter.get(7).toString()));
        } else {
            classesProgression.setText(defaultProgression);
        }

        setUpButtons();
    }
}
