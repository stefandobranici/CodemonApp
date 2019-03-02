package com.example.dobra.myapplication;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.Image;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import static android.graphics.Color.WHITE;

public class ProfileViewActivity extends AppCompatActivity {

    private ImageView storyModeButton, multiplayerModeButton, practiceModeButton,  profileModeButton, friendsModeButton, inventoryModeButton, shopModeButton, settingsModeButton, backButton;

    private TextView userName, userLevel, userXp, userCoins;

    private Typeface cyberFont;

    private ImageView poseView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_view);

        cyberFont = Typeface.createFromAsset(getAssets(), "font/Cyberverse.otf");

        poseView = (ImageView) findViewById(R.id.poseView);

        setUpButtons();
        setUpTextViews();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                updateTextViews();
                handler.postDelayed(this, 2000);
            }
        }, 1500);
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
                Intent inventory_intent = new Intent("android.intent.action.InventoryActivity");
                startActivity(inventory_intent);
            }
        });
    }

    private void setShopModeButtonOnClickListener(){
        shopModeButton = (ImageView) findViewById(R.id.shopbutton);
        shopModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shop_intent = new Intent("android.intent.action.ShopActivity");
                startActivity(shop_intent);
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

    private void setUpTextViews(){
        userName = (TextView) findViewById(R.id.userSetName);
        userLevel = (TextView) findViewById(R.id.userSetLevel);
        userXp = (TextView) findViewById(R.id.userSetXp);
        userCoins = (TextView) findViewById(R.id.userSetCoins);

        userName.setTypeface(cyberFont);
        userLevel.setTypeface(cyberFont);
        userXp.setTypeface(cyberFont);
        userCoins.setTypeface(cyberFont);

        userName.setTextSize(18);
        userLevel.setTextSize(18);
        userXp.setTextSize(18);
        userCoins.setTextSize(18);

        //userName.setTextColor(getResources().getColor(R.color.colorAccent));

        userName.setText(CurrentUserInformation.getInstance().getUserName());
        userLevel.setText("Lv.  "+String.format(CurrentUserInformation.getInstance().getUserLevel().toString()));
        userXp.setText("XP:  "+String.format(CurrentUserInformation.getInstance().getUserXp().toString() +" / "+ UserLevel.getInstance().getXpNeedForCurrentLevel(CurrentUserInformation.getInstance().getUserLevel()).toString()));
        userCoins.setText("Cyber Coins:  "  +String.format(CurrentUserInformation.getInstance().getUserCoins().toString()));

        updateProfilePicture();


    }

    private void updateTextViews(){
        userName.setText(CurrentUserInformation.getInstance().getUserName());
        userLevel.setText("Lv.  "+String.format(CurrentUserInformation.getInstance().getUserLevel().toString()));
        userXp.setText("XP:  "+String.format(CurrentUserInformation.getInstance().getUserXp().toString() +" / "+ UserLevel.getInstance().getXpNeedForCurrentLevel(CurrentUserInformation.getInstance().getUserLevel()).toString()));
        userCoins.setText("Cyber Coins:  "  +String.format(CurrentUserInformation.getInstance().getUserCoins().toString()));

        updateProfilePicture();
    }

    private void updateProfilePicture(){
        Glide.with(getApplicationContext()).asBitmap().load(CurrentUserInformation.getInstance().getUserPosePictureURL()).into(poseView);
    }

}
