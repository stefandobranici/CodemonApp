package com.example.dobra.myapplication;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class FriendsView extends AppCompatActivity {

    private ImageView storyModeButton, multiplayerModeButton, practiceModeButton,  profileModeButton, friendsModeButton, inventoryModeButton, shopModeButton, settingsModeButton, backButton;


    private List<Friend> mFriends = new ArrayList<>();

    private LinearLayout addFriendLayout;

    private ImageView openAddFriendLayout;

    private TextView addFriendButton, closeAddFriendLayout;

    private EditText addFriendField;


    private Typeface cyberFont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_view);

        cyberFont = Typeface.createFromAsset(getAssets(), "font/Cyberverse.otf");

        addFriendLayout = (LinearLayout) findViewById(R.id.addFriendLayout);

        openAddFriendLayout = (ImageView) findViewById(R.id.openAddFriendLayout);

        addFriendButton = (TextView) findViewById(R.id.addFriendButton);

        closeAddFriendLayout = (TextView) findViewById(R.id.closeAddFriendLayout);

        addFriendField = (EditText) findViewById(R.id.addFriendField);



        initFriendList();

        initRecyclerView();

        setUpButtons();


    }

    private void initFriendList(){
        mFriends.add(new Friend("https://cdn.vox-cdn.com/thumbor/QarshUMW7yZODSRUrWmtPdfsKns=/0x0:1280x960/1220x813/filters:focal(538x378:742x582):format(webp)/cdn.vox-cdn.com/uploads/chorus_image/image/57601275/60861120_1280x960.0.0.jpg", "Chiu Plus", "Lv. 1", "2/144", "12 Hours Ago"));
        mFriends.add(new Friend("https://cdn.vox-cdn.com/thumbor/QarshUMW7yZODSRUrWmtPdfsKns=/0x0:1280x960/1220x813/filters:focal(538x378:742x582):format(webp)/cdn.vox-cdn.com/uploads/chorus_image/image/57601275/60861120_1280x960.0.0.jpg", "Chiu Plus", "Lv. 2", "5/144", "11 Hours Ago"));
        mFriends.add(new Friend("https://cdn.vox-cdn.com/thumbor/QarshUMW7yZODSRUrWmtPdfsKns=/0x0:1280x960/1220x813/filters:focal(538x378:742x582):format(webp)/cdn.vox-cdn.com/uploads/chorus_image/image/57601275/60861120_1280x960.0.0.jpg", "Chiu Plus", "Lv. 4", "22/144", "6 Hours Ago"));
        mFriends.add(new Friend("https://cdn.vox-cdn.com/thumbor/QarshUMW7yZODSRUrWmtPdfsKns=/0x0:1280x960/1220x813/filters:focal(538x378:742x582):format(webp)/cdn.vox-cdn.com/uploads/chorus_image/image/57601275/60861120_1280x960.0.0.jpg", "Chiu Plus", "Lv. 3", "12/144", "8 Hours Ago"));
        mFriends.add(new Friend("https://cdn.vox-cdn.com/thumbor/QarshUMW7yZODSRUrWmtPdfsKns=/0x0:1280x960/1220x813/filters:focal(538x378:742x582):format(webp)/cdn.vox-cdn.com/uploads/chorus_image/image/57601275/60861120_1280x960.0.0.jpg", "Chiu Plus", "Lv. 8", "62/144", "3 Hours Ago"));
        mFriends.add(new Friend("https://cdn.vox-cdn.com/thumbor/QarshUMW7yZODSRUrWmtPdfsKns=/0x0:1280x960/1220x813/filters:focal(538x378:742x582):format(webp)/cdn.vox-cdn.com/uploads/chorus_image/image/57601275/60861120_1280x960.0.0.jpg", "Chiu Plus", "Lv. 10", "144/144", "1 Hour Ago"));
    }

    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.friendsRecyclerView);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(getApplicationContext(), mFriends);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
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

    private void openFriendLayoutSetOnClickListener(){
        openAddFriendLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFriendLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    private void closeAddFriendLayoutSetOnClickListener(){
        closeAddFriendLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFriendLayout.setVisibility(View.GONE);
            }
        });
    }

    private void addFriendButtonSetOnClickListener(){
        addFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "This is a Work In Progress!", Toast.LENGTH_SHORT).show();
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

        openFriendLayoutSetOnClickListener();
        closeAddFriendLayoutSetOnClickListener();
        addFriendButtonSetOnClickListener();
    }
}
