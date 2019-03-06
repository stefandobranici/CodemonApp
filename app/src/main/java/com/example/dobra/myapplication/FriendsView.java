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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

public class FriendsView extends AppCompatActivity {

    private ImageView storyModeButton, multiplayerModeButton, practiceModeButton, profileModeButton, friendsModeButton, inventoryModeButton, shopModeButton, settingsModeButton, backButton;

    private GifImageView loadingAnimation;


    private List<Friend> mFriends = new ArrayList<>();

    private LinearLayout addFriendLayout, removeFriendLayout;


    private TextView addFriendButton, closeAddFriendLayout, openAddFriendLayout, removeFriendButton, closeRemoveFriendLayout;

    private EditText addFriendField;

    private String friendID;

    DatabaseReference userFriendList, userList, currentUserUID;

    private FirebaseDatabase mDatabase;

    private FirebaseAuth mAuth;

    private FirebaseStorage mStorage;

    private boolean friendAdded;

    private int currentPosition, TIMEOUT_DELAY;



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

        removeFriendLayout = (LinearLayout) findViewById(R.id.removeFriendLayout);

        removeFriendButton = (TextView) findViewById(R.id.removeFriendButton);

        closeRemoveFriendLayout = (TextView) findViewById(R.id.closeRemoveFriendLayout);


        loadingAnimation = (GifImageView) findViewById(R.id.loadingAnimation);

        currentPosition = 0;

        mDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mStorage = FirebaseStorage.getInstance();
        userFriendList = mDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("FriendList");

        setUpButtons();

        initFriendList();

    }

    private void initFriendList() {

        mFriends.clear();



        loadingAnimation.setVisibility(View.VISIBLE);

        currentPosition = 0;

        TIMEOUT_DELAY = 1000;

        userFriendList.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        TIMEOUT_DELAY += 1000;
                        final Friend currentFriend = new Friend();

                        mFriends.add(currentFriend);

                        final int thisFriendPosition = currentPosition;

                        mFriends.get(thisFriendPosition).setFriendName("Chiu");

                        mFriends.get(thisFriendPosition).setFriendLevel("Lv. 1");

                        mFriends.get(thisFriendPosition).setFriendProgress("1/144");

                        mFriends.get(thisFriendPosition).setFriendActivity("1 Minute Ago");

                        mFriends.get(thisFriendPosition).setFriendImage("https://firebasestorage.googleapis.com/v0/b/myapplication-9586f.appspot.com/o/Profile%2Fprofilepic.png?alt=media&token=1d0d0f95-4d1c-475a-bc36-4ddb79b49955");

                        mFriends.get(thisFriendPosition).setFriendID("");



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

                        final DatabaseReference currentUserProfilePicRef = currentUserRef.child("User Information").child("Profile Picture");

                        currentUserProfilePicRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    mFriends.get(thisFriendPosition).setFriendImage(dataSnapshot.getValue(String.class));
                                }
                                currentUserProfilePicRef.removeEventListener(this);
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

                        final DatabaseReference currentUserIDRef = currentUserRef.child("User Information").child("User ID");

                        currentUserIDRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    mFriends.get(thisFriendPosition).setFriendID(dataSnapshot.getValue(String.class));
                                }
                                currentUserIDRef.removeEventListener(this);
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
                                    mFriends.get(thisFriendPosition).setFriendProgress(dataSnapshot.getValue(Integer.class).toString() + "/" + CurrentUserInformation.getInstance().getTotalLevelsAvailableForPlay().toString());
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
                                        mFriends.get(thisFriendPosition).setFriendActivity(diffDays.toString() + " Day(s) Ago");
                                    } else if (diffHours > 0) {
                                        mFriends.get(thisFriendPosition).setFriendActivity(diffHours.toString() + " Hour(s) Ago");
                                    } else if (diffMin >= 0) {
                                        mFriends.get(thisFriendPosition).setFriendActivity(diffMin.toString() + " Minute(s) Ago");
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
        }, TIMEOUT_DELAY);
    }

    private void initRecyclerView() {
        Collections.sort(mFriends, Collections.reverseOrder());

        RecyclerView recyclerView = findViewById(R.id.friendsRecyclerView);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(getApplicationContext(), mFriends, removeFriendLayout);
        recyclerView.removeAllViewsInLayout();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    //Add friend functionality

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
                TIMEOUT_DELAY = 1000;

                loadingAnimation.setVisibility(View.VISIBLE);

                friendID = addFriendField.getText().toString().trim();

                userList = mDatabase.getReference("Users");

                userList.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {

                            TIMEOUT_DELAY += 250;
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
                                        addFriendLayout.setVisibility(View.GONE);
                                        initFriendList();
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

                            initFriendList();
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
                }, TIMEOUT_DELAY);
            }
        });
    }

    //Remove friend functionality

    private void closeRemoveFriendSetOnClickListener(){
        closeRemoveFriendLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeFriendLayout.setVisibility(View.GONE);
            }
        });
    }

    private void setRemoveFriendButton(){
        removeFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String friendToBeDeletedBruteID = CurrentUserInformation.getInstance().getFriendSelectedForRemove();

                int positionWhereEmailStarts = 0;

                for(int i = 0 ; i < friendToBeDeletedBruteID.length(); i++){
                    if(friendToBeDeletedBruteID.charAt(i) == '@'){
                        positionWhereEmailStarts = i;
                    }
                }

                DatabaseReference friendToBeDeleted = mDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("FriendList").child(friendToBeDeletedBruteID.substring(0,positionWhereEmailStarts));

                friendToBeDeleted.setValue(null);

                Toast.makeText(getApplicationContext(), "Friend was removed!", Toast.LENGTH_SHORT).show();

                removeFriendLayout.setVisibility(View.GONE);

                initFriendList();
            }
        });
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

        //Set add friend buttons
        openFriendLayoutSetOnClickListener();
        closeAddFriendLayoutSetOnClickListener();
        addFriendButtonSetOnClickListener();

        //Set remove friend buttons
        closeRemoveFriendSetOnClickListener();
        setRemoveFriendButton();
    }
}
