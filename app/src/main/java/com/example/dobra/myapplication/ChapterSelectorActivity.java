package com.example.dobra.myapplication;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ChapterSelectorActivity extends AppCompatActivity {

    private ImageButton variablesBtn, ifBtn, whileBtn, forBtn, arraysBtn, methodsBtn, classesBtn;

    private Boolean chapterLocked2, chapterLocked3, chapterLocked4, chapterLocked5, chapterLocked6, chapterLocked7;

    private TextView variableProgression, ifProgression, whileProgression, forProgression, arraysProgression, methodsProgression, classesProgression;

    private ImageView storyModeButton, multiplayerModeButton, practiceModeButton,  profileModeButton, friendsModeButton, inventoryModeButton, shopModeButton, settingsModeButton, backButton;

    private CurrentUserInformation currentUserInformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_chapter_selector);

        currentUserInformation = CurrentUserInformation.getInstance();

        setUpLayout();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setUpLayout();
                handler.postDelayed(this, 2000);
            }
        }, 1500);

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



    private void setStoryModeButtonOnClickListener(){
        storyModeButton = (ImageView) findViewById(R.id.storybutton);
        storyModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent storyMode = new Intent("android.intent.action.ChapterSelectorActivity");
                startActivity(storyMode);
            }
        });
    }

    private void setMultiplayerModeButtonOnClickListener(){
        multiplayerModeButton = (ImageView) findViewById(R.id.multiplayerbutton);
        multiplayerModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Mode not yet implemented! Work In Progress!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setPracticeModeButtonOnClickListener(){
        practiceModeButton = (ImageView) findViewById(R.id.practicebutton);
        practiceModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Mode not yet implemented! Work In Progress!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setProfileButtonOnClickListener(){
        profileModeButton = (ImageView) findViewById(R.id.profilebutton);
        profileModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profileMode = new Intent("android.intent.action.ProfileViewActivity");
                startActivity(profileMode);
            }
        });
    }

    private void setFriendsButtonOnClickListener(){
        friendsModeButton = (ImageView) findViewById(R.id.friendsbutton);
        friendsModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent friendsMode = new Intent("android.intent.action.FriendsView");
                startActivity(friendsMode);
            }
        });
    }

    private void setInventoryModeButtonOnClickListener(){
        inventoryModeButton = (ImageView) findViewById(R.id.itemsbutton);
        inventoryModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Mode not yet implemented! Work In Progress!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setShopModeButtonOnClickListener(){
        shopModeButton = (ImageView) findViewById(R.id.shopbutton);
        shopModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Mode not yet implemented! Work In Progress!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setSettingsButtonOnClickListener(){
        settingsModeButton = (ImageView) findViewById(R.id.settingsbutton);
        settingsModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settings_intent = new Intent("android.intent.action.SettingsScreen");
                startActivity(settings_intent);
            }
        });
    }

    private void setBackButtonOnClickListener(){
        backButton = (ImageView) findViewById(R.id.backbutton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void setUpButtons(){
        //Chapter view buttons
        variablesButtonOnClickListener();
        ifButtonOnClickListener();
        whileButtonOnClickListener();
        forButtonOnClickListener();
        arraysButtonOnClickListener();
        methodsButtonOnClickListener();
        classesButtonOnClickListener();

        //Sub menu setup
        setStoryModeButtonOnClickListener();
        setMultiplayerModeButtonOnClickListener();
        setPracticeModeButtonOnClickListener();
        setProfileButtonOnClickListener();
        setFriendsButtonOnClickListener();
        setInventoryModeButtonOnClickListener();
        setShopModeButtonOnClickListener();
        setSettingsButtonOnClickListener();
        setBackButtonOnClickListener();
    }

    public void setUpLayout(){
        String defaultProgression = "0/??";

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
            variableProgression.setText(String.format("0/"+currentUserInformation.numberOfMissionsInChapter.get(1).toString()));
        }

        ifProgression = (TextView) findViewById(R.id.ifsProgression);

        if(currentUserInformation.progressionOfChapters.containsKey(2)){
            if(currentUserInformation.progressionOfChapters.get(2).equals(currentUserInformation.numberOfMissionsInChapter.get(2))){
                chapterLocked3 = false;
            }
            ifProgression.setText(String.format(currentUserInformation.progressionOfChapters.get(2).toString()+"/"+currentUserInformation.numberOfMissionsInChapter.get(2).toString()));
        } else if(chapterLocked2){
            ifProgression.setText(defaultProgression);
        } else {
            ifProgression.setText(String.format("0/"+currentUserInformation.numberOfMissionsInChapter.get(2).toString()));
        }

        whileProgression = (TextView) findViewById(R.id.whilesProgression);

        if(currentUserInformation.progressionOfChapters.containsKey(3)){
            if(currentUserInformation.progressionOfChapters.get(3).equals(currentUserInformation.numberOfMissionsInChapter.get(3))){
                chapterLocked4 = false;
            }
            whileProgression.setText(String.format(currentUserInformation.progressionOfChapters.get(3).toString()+"/"+currentUserInformation.numberOfMissionsInChapter.get(3).toString()));
        } else if(chapterLocked3){
            whileProgression.setText(defaultProgression);
        } else {
            whileProgression.setText(String.format("0/"+currentUserInformation.numberOfMissionsInChapter.get(3).toString()));
        }

        forProgression = (TextView) findViewById(R.id.forsProgression);

        if(currentUserInformation.progressionOfChapters.containsKey(4)){
            if(currentUserInformation.progressionOfChapters.get(4).equals(currentUserInformation.numberOfMissionsInChapter.get(4))){
                chapterLocked5 = false;
            }
            forProgression.setText(String.format(currentUserInformation.progressionOfChapters.get(4).toString()+"/"+currentUserInformation.numberOfMissionsInChapter.get(4).toString()));
        } else if(chapterLocked4){
            forProgression.setText(defaultProgression);
        } else {
            forProgression.setText(String.format("0/"+currentUserInformation.numberOfMissionsInChapter.get(4).toString()));
        }

        arraysProgression = (TextView) findViewById(R.id.arraysProgression);

        if(currentUserInformation.progressionOfChapters.containsKey(5)){
            if(currentUserInformation.progressionOfChapters.get(5).equals(currentUserInformation.numberOfMissionsInChapter.get(5))){
                chapterLocked6 = false;
            }
            arraysProgression.setText(String.format(currentUserInformation.progressionOfChapters.get(5).toString()+"/"+currentUserInformation.numberOfMissionsInChapter.get(5).toString()));
        } else if(chapterLocked5) {
            arraysProgression.setText(defaultProgression);
        } else {
            arraysProgression.setText(String.format("0/"+currentUserInformation.numberOfMissionsInChapter.get(5).toString()));
        }

        methodsProgression = (TextView) findViewById(R.id.methodsProgression);

        if(currentUserInformation.progressionOfChapters.containsKey(6)){
            if(currentUserInformation.progressionOfChapters.get(6).equals(currentUserInformation.numberOfMissionsInChapter.get(6))){
                chapterLocked7 = false;
            }
            methodsProgression.setText(String.format(currentUserInformation.progressionOfChapters.get(6).toString()+"/"+currentUserInformation.numberOfMissionsInChapter.get(6).toString()));
        } else if(chapterLocked6){
            methodsProgression.setText(defaultProgression);
        } else {
            methodsProgression.setText(String.format("0/"+currentUserInformation.numberOfMissionsInChapter.get(6).toString()));
        }

        classesProgression = (TextView) findViewById(R.id.classesProgression);

        if(currentUserInformation.progressionOfChapters.containsKey(7)){
            classesProgression.setText(String.format(currentUserInformation.progressionOfChapters.get(7).toString()+"/"+currentUserInformation.numberOfMissionsInChapter.get(7).toString()));
        } else if(chapterLocked7){
            classesProgression.setText(defaultProgression);
        } else {
            classesProgression.setText(String.format("0/"+currentUserInformation.numberOfMissionsInChapter.get(7).toString()));
        }
        setUpButtons();
    }
}
