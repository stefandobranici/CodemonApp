package com.example.dobra.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class FriendsView extends AppCompatActivity {

    private ImageView storyModeButton, multiplayerModeButton, practiceModeButton,  profileModeButton, friendsModeButton, inventoryModeButton, shopModeButton, settingsModeButton, backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_view);

        setUpButtons();
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
}
