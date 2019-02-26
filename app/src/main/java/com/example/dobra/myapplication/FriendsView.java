package com.example.dobra.myapplication;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

public class FriendsView extends AppCompatActivity {

    private ImageView storyModeButton, multiplayerModeButton, practiceModeButton, profileModeButton, friendsModeButton, inventoryModeButton, shopModeButton, settingsModeButton, backButton;

    private GifImageView loadingAnimation;


    private List<Friend> mFriends = new ArrayList<>();

    private LinearLayout addFriendLayout;


    private TextView addFriendButton, closeAddFriendLayout, openAddFriendLayout;

    private EditText addFriendField;

    private String friendID;

    DatabaseReference userFriendList, userList, currentUserUID;

    private FirebaseDatabase mDatabase;

    private FirebaseAuth mAuth;

    private boolean friendAdded;

    int currentPosition;


    private Typeface cyberFont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_view);

        friendAdded = false;

        cyberFont = Typeface.createFromAsset(getAssets(), "font/Cyberverse.otf");

        addFriendLayout = (LinearLayout) findViewById(R.id.addFriendLayout);

        openAddFriendLayout = (TextView) findViewById(R.id.openAddFriendLayout);

        addFriendButton = (TextView) findViewById(R.id.addFriendButton);

        closeAddFriendLayout = (TextView) findViewById(R.id.closeAddFriendLayout);

        addFriendField = (EditText) findViewById(R.id.addFriendField);

        loadingAnimation = (GifImageView) findViewById(R.id.loadingAnimation);

        currentPosition = 0;

        mDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        userFriendList = mDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("FriendList");

        setUpButtons();

        initFriendList();


/*        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                initFriendList();
                handler.postDelayed(this, 2000);
            }
        }, 4000);*/
    }

    private void initFriendList() {

        loadingAnimation.setVisibility(View.VISIBLE);

        currentPosition = 0;

        userFriendList.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        final Friend currentFriend = new Friend();

                        mFriends.add(currentFriend);

                        final int thisFriendPosition = currentPosition;

                        mFriends.get(thisFriendPosition).setFriendName("Chiu");

                        mFriends.get(thisFriendPosition).setFriendLevel("Lv. 1");

                        mFriends.get(thisFriendPosition).setFriendProgress("1/144");

                        mFriends.get(thisFriendPosition).setFriendActivity("1 Minute Ago");

                        mFriends.get(thisFriendPosition).setFriendImage("https://cdn.vox-cdn.com/thumbor/QarshUMW7yZODSRUrWmtPdfsKns=/0x0:1280x960/1220x813/filters:focal(538x378:742x582):format(webp)/cdn.vox-cdn.com/uploads/chorus_image/image/57601275/60861120_1280x960.0.0.jpg");

                        String UID = dataSnapshot1.getValue(String.class);

                        DatabaseReference currentUserRef = mDatabase.getReference("Users").child(UID);

                        final DatabaseReference currentUserNameRef = currentUserRef.child("User Information").child("Name");

                        currentUserNameRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    mFriends.get(thisFriendPosition).setFriendName(dataSnapshot.getValue(String.class));
                                }
                                currentUserNameRef.removeEventListener(this);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                        final DatabaseReference currentUserLevelRef = currentUserRef.child("User Information").child("Level");

                        currentUserLevelRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    mFriends.get(thisFriendPosition).setFriendLevel("Lv. "+dataSnapshot.getValue(Integer.class).toString());
                                }
                                currentUserLevelRef.removeEventListener(this);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                        final DatabaseReference currentUserProgressRef = currentUserRef.child("Game Status").child("Current Level");

                        currentUserProgressRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    mFriends.get(thisFriendPosition).setFriendProgress(dataSnapshot.getValue(Integer.class).toString() + "/144");
                                }
                                currentUserProgressRef.removeEventListener(this);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                        final DatabaseReference currentUserActivityRef = currentUserRef.child("User Information").child("LastLoggedIn");

                        currentUserActivityRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                                    Date currentDate = new Date();
                                    Date lastLoggedIn = new Date();
                                    try {
                                        lastLoggedIn = dateFormat.parse(dataSnapshot.getValue(String.class));
                                    } catch (ParseException e) {

                                    }

                                    long diffInTime = currentDate.getTime() - lastLoggedIn.getTime();

                                    Integer diffDays = (int) (diffInTime / (24 * 60 * 60 * 1000));

                                    Integer diffHours = (int) (diffInTime / (60 * 60 * 1000));

                                    Integer diffMin = (int) (diffInTime / (60 * 1000));

                                    if (diffDays > 0) {
                                        mFriends.get(thisFriendPosition).setFriendActivity(diffDays.toString() + " Days Ago");
                                    } else if (diffHours > 0) {
                                        mFriends.get(thisFriendPosition).setFriendActivity(diffHours.toString() + " Hours Ago");
                                    } else if (diffMin >= 0) {
                                        mFriends.get(thisFriendPosition).setFriendActivity(diffMin.toString() + " Minutes Ago");
                                    } else {
                                        mFriends.get(thisFriendPosition).setFriendActivity("N/A/N");
                                    }
                                }
                                currentUserActivityRef.removeEventListener(this);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                        currentPosition++;


                    }
                }
                userFriendList.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                initRecyclerView();
                loadingAnimation.setVisibility(View.GONE);
            }
        }, 2000);
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.friendsRecyclerView);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(getApplicationContext(), mFriends);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

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

    private void openFriendLayoutSetOnClickListener() {
        openAddFriendLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFriendLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    private void closeAddFriendLayoutSetOnClickListener() {
        closeAddFriendLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFriendLayout.setVisibility(View.GONE);
            }
        });
    }

    private void addFriendButtonSetOnClickListener() {
        addFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadingAnimation.setVisibility(View.VISIBLE);

                friendID = addFriendField.getText().toString().trim();

                userList = mDatabase.getReference("Users");

                userList.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {

                            if (friendAdded) {
                                break;
                            }
                            final String UID = dataSnapshot1.getKey();
                            currentUserUID = userList.child(UID).child("User Information").child("User ID");

                            currentUserUID.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                    String newFriendId = friendID + "@bathspa.ac.uk";

                                    if (newFriendId.equals(dataSnapshot.getValue(String.class))) {
                                        loadingAnimation.setVisibility(View.GONE);
                                        Toast.makeText(getApplicationContext(), "Friend Added!", Toast.LENGTH_SHORT).show();
                                        friendAdded = true;
                                        userFriendList.child(friendID).setValue(UID);
                                    }

                                    currentUserUID.removeEventListener(this);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }

                        if(friendAdded){
                            loadingAnimation.setVisibility(View.GONE);
                            friendAdded = false;
                        }
                        userList.removeEventListener(this);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(friendAdded){
                            loadingAnimation.setVisibility(View.GONE);
                            friendAdded = false;
                        } else {
                            loadingAnimation.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(), "User ID was not found!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, 1500);
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
                Toast.makeText(getApplicationContext(), "Mode not yet implemented! Work In Progress!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setShopModeButtonOnClickListener() {
        shopModeButton = (ImageView) findViewById(R.id.shopbutton);
        shopModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Mode not yet implemented! Work In Progress!", Toast.LENGTH_SHORT).show();
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

        openFriendLayoutSetOnClickListener();
        closeAddFriendLayoutSetOnClickListener();
        addFriendButtonSetOnClickListener();
    }
}
