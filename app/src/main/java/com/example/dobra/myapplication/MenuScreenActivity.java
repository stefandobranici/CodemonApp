package com.example.dobra.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class MenuScreenActivity extends AppCompatActivity {

    ImageView storyMode, multiplayerMode, practiceMode, profileMode, itemsMode, friendsMode, shopMode, settingsMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_selector);

        setAllOnClickListeners();

    }

    private void setStoryModeOnClickListener(){
        storyMode = (ImageView) findViewById(R.id.storyModeView);

        storyMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent storyMode = new Intent("android.intent.action.ChapterSelectorActivity");
                startActivity(storyMode);
            }
        });
    }

    private void setMultiplayerModeOnClickListener(){
        multiplayerMode = (ImageView) findViewById(R.id.multiplayerModeView);

        multiplayerMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Mode not yet implemented! Work In Progress!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setPracticeModeOnClickListener(){
        practiceMode = (ImageView) findViewById(R.id.practiceModeView);

        practiceMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Mode not yet implemented! Work In Progress!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setProfileModeOnClickListener(){
        profileMode = (ImageView) findViewById(R.id.profileModeView);

        profileMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profileMode = new Intent("android.intent.action.ProfileViewActivity");
                startActivity(profileMode);
            }
        });
    }

    private void setFriendsModeOnClickListener(){
        friendsMode = (ImageView) findViewById(R.id.friendsModeView);

        friendsMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent friendsMode = new Intent("android.intent.action.FriendsView");
                startActivity(friendsMode);
            }
        });
    }

    private void setItemsModeOnClickListener(){
        itemsMode = (ImageView) findViewById(R.id.itemsModeView);

        itemsMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Mode not yet implemented! Work In Progress!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setShopModeOnClickListener(){
        shopMode = (ImageView) findViewById(R.id.shopModeView);

        shopMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Mode not yet implemented! Work In Progress!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setSettingsModeOnClickListener(){
        settingsMode = (ImageView) findViewById(R.id.settingsModeView);

        settingsMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settingsMode = new Intent("android.intent.action.SettingsScreen");
                startActivity(settingsMode);
            }
        });
    }

    private void setAllOnClickListeners(){
        setStoryModeOnClickListener();
        setProfileModeOnClickListener();
        setFriendsModeOnClickListener();
        setSettingsModeOnClickListener();


        //Work in progress
        setMultiplayerModeOnClickListener();
        setPracticeModeOnClickListener();
        setShopModeOnClickListener();
        setItemsModeOnClickListener();
    }
}
