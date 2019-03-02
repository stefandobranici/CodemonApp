package com.example.dobra.myapplication;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import pl.droidsonroids.gif.GifImageView;

public class InventoryActivity extends AppCompatActivity {
    private ImageView storyModeButton, multiplayerModeButton, practiceModeButton, profileModeButton, friendsModeButton, inventoryModeButton, shopModeButton, settingsModeButton, backButton;

    private GifImageView loadingAnimation;

    private TextView equipItemButton, closeEquipItemLayout;

    private LinearLayout equipItemLayout;

    private List<Appearance> userInventoryAppearances;
    private List<Consumable> userInventoryConsumables;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        loadingAnimation = (GifImageView) findViewById(R.id.loadingAnimation);

        equipItemButton = (TextView) findViewById(R.id.equipItemButton);

        closeEquipItemLayout = (TextView) findViewById(R.id.closeEquipItemLayout);

        equipItemLayout = (LinearLayout) findViewById(R.id.equipItemLayout);

        userInventoryAppearances = new ArrayList<>();

        userInventoryConsumables = new ArrayList<>();

        initInventoryCollection();

        setUpButtons();
    }

    private void initInventoryCollection(){
        loadingAnimation.setVisibility(View.VISIBLE);

        for(Map.Entry entry: CurrentUserInformation.getInstance().userAppearancesCollection.entrySet()){
            Appearance appearance = (Appearance) entry.getValue();

            if(appearance.getUnlocked()) {
                userInventoryAppearances.add((Appearance) entry.getValue());
            }
        }

        for(Map.Entry entry: CurrentUserInformation.getInstance().userConsumablesCollection.entrySet()){
            userInventoryConsumables.add((Consumable) entry.getValue());
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                initRecyclerView();
                loadingAnimation.setVisibility(View.GONE);
            }
        }, 1000);
    }

    private void initRecyclerView(){

        RecyclerView recyclerView = findViewById(R.id.itemsRecyclerView);
        RecyclerViewAdapterAppearance adapter = new RecyclerViewAdapterAppearance(getApplicationContext(), userInventoryAppearances,userInventoryConsumables, equipItemLayout);
        recyclerView.removeAllViewsInLayout();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }


    private void setUpButtons() {
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

        setCloseEquipItemLayoutButtonOnClickListener();
        setEquipItemButtonOnClickListener();
    }


    //Set submenu button click listeners

    private void setStoryModeButtonOnClickListener() {
        storyModeButton = (ImageView) findViewById(R.id.storybutton);
        storyModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent storyMode = new Intent("android.intent.action.ChapterSelectorActivity");
                startActivity(storyMode);
            }
        });
    }

    private void setMultiplayerModeButtonOnClickListener() {
        multiplayerModeButton = (ImageView) findViewById(R.id.multiplayerbutton);
        multiplayerModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Mode not yet implemented! Work In Progress!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setPracticeModeButtonOnClickListener() {
        practiceModeButton = (ImageView) findViewById(R.id.practicebutton);
        practiceModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Mode not yet implemented! Work In Progress!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setProfileButtonOnClickListener() {
        profileModeButton = (ImageView) findViewById(R.id.profilebutton);
        profileModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profileMode = new Intent("android.intent.action.ProfileViewActivity");
                startActivity(profileMode);
            }
        });
    }

    private void setFriendsButtonOnClickListener() {
        friendsModeButton = (ImageView) findViewById(R.id.friendsbutton);
        friendsModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent friendsMode = new Intent("android.intent.action.FriendsView");
                startActivity(friendsMode);
            }
        });
    }

    private void setInventoryModeButtonOnClickListener() {
        inventoryModeButton = (ImageView) findViewById(R.id.itemsbutton);
        inventoryModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inventory_intent = new Intent("android.intent.action.InventoryActivity");
                startActivity(inventory_intent);
            }
        });
    }

    private void setShopModeButtonOnClickListener() {
        shopModeButton = (ImageView) findViewById(R.id.shopbutton);
        shopModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shop_intent = new Intent("android.intent.action.ShopActivity");
                startActivity(shop_intent);
            }
        });
    }

    private void setSettingsButtonOnClickListener() {
        settingsModeButton = (ImageView) findViewById(R.id.settingsbutton);
        settingsModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settings_intent = new Intent("android.intent.action.SettingsScreen");
                startActivity(settings_intent);
            }
        });
    }

    private void setBackButtonOnClickListener() {
        backButton = (ImageView) findViewById(R.id.backbutton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setCloseEquipItemLayoutButtonOnClickListener(){
        closeEquipItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                equipItemLayout.setVisibility(View.GONE);
            }
        });
    }

    private void setEquipItemButtonOnClickListener(){
        equipItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingAnimation.setVisibility(View.VISIBLE);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadingAnimation.setVisibility(View.GONE);
                        equipItemLayout.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext()," Item has been equipped/unequipped!", Toast.LENGTH_SHORT).show();
                    }
                }, 1000);
                CurrentUserInformation.getInstance().setUserCurrentEquippedItem(CurrentUserInformation.getInstance().getItemToBeEquipped());
            }
        });
    }
}
