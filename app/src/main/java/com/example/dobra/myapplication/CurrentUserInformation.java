package com.example.dobra.myapplication;

import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CurrentUserInformation {

    private static final CurrentUserInformation SINGLE_INSTANCE = new CurrentUserInformation();

    private FirebaseAuth mAuth;

    private FirebaseDatabase mDatabase;

    private DatabaseReference mChaptersReference;

    private DatabaseReference userCurrentActiveChapter, userCurrentActiveLevel;

    private DatabaseReference userItemsAppearances, userItemsMedals, userItemsConsumables, userSkills, skillsRef, consumablesRef, medalsRef, appearancesRef;

    public Map<Integer, Integer> progressionOfChapters;

    public Map<Integer, Integer> numberOfMissionsInChapter;

    private Integer currentActiveChapter, currentActiveLevel;

    private Integer chapterSelected;

    private Level levelSelectedForPlay;

    private String userName;

    private Integer userLevel, userXp, userCoins;

    private List<Skill> availableSkills;

    private List<Consumable> availableConsumables;

    private List<Medal> availableMedals;

    private List<Appearance> availableAppearances;

    private CurrentUserInformation() {

        //Initiate arrays
        progressionOfChapters = new HashMap<>();

        numberOfMissionsInChapter = new HashMap<>();

        availableSkills = new ArrayList<>();

        availableAppearances = new ArrayList<>();

        availableMedals = new ArrayList<>();

        availableConsumables = new ArrayList<>();

        for(int i = 1; i < 8; i++){
            numberOfMissionsInChapter.put(i,0);
            progressionOfChapters.put(i,0);
        }

        //Setup some initial variables to avoid null pointers;
        userName = "N/A/N";
        userLevel = 0;
        userXp = 0;
        userCoins = 0;

        //Initiate instance of firebase auth and database;
        mAuth = FirebaseAuth.getInstance();

        mDatabase = FirebaseDatabase.getInstance();

    }

    public static CurrentUserInformation getInstance() {
        return SINGLE_INSTANCE;
    }


    public Integer getCurrentActiveChapter(){
        return currentActiveChapter;
    }

    //Dont remember what this does to be honest
    public Integer getCurrentActiveLevel(){
        return currentActiveLevel;
    }

    //Dont remember what this does to be honest
    public Integer getChapterSelected(){
        return chapterSelected;
    }

    //Dont remember what this does to be honest
    public Level getLevelSelectedForPlay(){
        return levelSelectedForPlay;
    }

    ///Dont remember what this does to be honest
    public void setCurrentActiveChapter(int updatedChapter){
        currentActiveChapter = updatedChapter;
    }

    //Dont remember what this does to be honest
    public void setCurrentActiveLevel(int updatedLevel){
        currentActiveLevel = updatedLevel;
    }

    //This method sets the current chapter in progress;
    public void setChapterSelected(Integer chapterSelected){
        this.chapterSelected = chapterSelected;
    }

    //This method sets the current level in progress;
    public void setLevelSelectedForPlay(Level level){
        levelSelectedForPlay = level;
    }


    //This is the main method that will add listeners to references to pull important information from the database used later in the app;
    //This is done upon logging in or signing up;
    public void getUserProgressionStatus(){

        getChapterProgressionInformation();

        getUserInformation();
    }


    //This adds listeners to retrieve chapter information from the db;
    private void getChapterProgressionInformation(){

        mChaptersReference = mDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("Chapter Progression");

        mChaptersReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()){
                    progressionOfChapters.clear();
                    numberOfMissionsInChapter.clear();
                    for(DataSnapshot keyNode:dataSnapshot.getChildren()){
                        for(DataSnapshot keyNode2:keyNode.getChildren()){
                            Level level = keyNode2.getValue(Level.class);
                            if(level.isCompleted()) {
                                if(progressionOfChapters.containsKey(level.getChapter())) {
                                    progressionOfChapters.put(level.getChapter(), progressionOfChapters.get(level.getChapter()) + 1);
                                } else {
                                    progressionOfChapters.put(level.getChapter(), 1);
                                }
                            }

                            if(numberOfMissionsInChapter.containsKey(level.getChapter())){
                                numberOfMissionsInChapter.put(level.getChapter(), numberOfMissionsInChapter.get(level.getChapter()) + 1);
                            } else {
                                numberOfMissionsInChapter.put(level.getChapter(), 1);
                            }

                        }
                    }
                } else {
                    new FirebaseDatabaseHelper().generateUserLevels(new FirebaseDatabaseHelper.DataStatus() {
                        @Override
                        public void DataIsLoaded(List<Level> levels, List<String> keys) {
                            Integer levelId = 1;
                            for(Level level:levels){
                                if(levelId == 22) {
                                    levelId = 1;
                                }
                                Integer chapterId = level.getChapter();
                                DatabaseReference currentLevelReference = mChaptersReference.child(chapterId.toString()).child(levelId.toString());
                                currentLevelReference.setValue(level);

                                levelId++;
                            }
                        }


                        @Override
                        public void DataIsInserted() {

                        }

                        @Override
                        public void DataIsUpdated() {

                        }

                        @Override
                        public void DataIsDeleted() {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //This adds listeners to retrieve user information such as current username, userlevel, userxp, usercoins, user currentlevel, user currentchapter, etc from the db;
    private void getUserInformation(){
        DatabaseReference userInformation = mDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("User Information");

        DatabaseReference userNameRef = userInformation.child("Name");

        userNameRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userName = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference userLevelRef = userInformation.child("Level");

        userLevelRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userLevel = dataSnapshot.getValue(Integer.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference useXpRef = userInformation.child("XP");

        useXpRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userXp = dataSnapshot.getValue(Integer.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference userCoinsRef = userInformation.child("Cybercoins");

        userCoinsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userCoins = dataSnapshot.getValue(Integer.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        userCurrentActiveChapter = mDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("Game Status").child("Current Chapter");

        userCurrentActiveChapter.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    setCurrentActiveChapter(dataSnapshot.getValue(Integer.class));
                } else {
                    userCurrentActiveChapter.setValue(1);

                    setCurrentActiveChapter(1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        userCurrentActiveLevel = mDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("Game Status").child("Current Level");

        userCurrentActiveLevel.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    setCurrentActiveLevel(dataSnapshot.getValue(Integer.class));
                } else {
                    userCurrentActiveLevel.setValue(1);

                    setCurrentActiveLevel(1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getUserInvetoryStatus(){
        DatabaseReference userItemsMedals = mDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("Items").child("Medals");

        DatabaseReference userItemsConsumables = mDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("Items").child("Consumables");

        DatabaseReference userItemsAppearance = mDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("Items").child("Appearance");
    }

    private void getUserSkills(){

    }

    //Set up singup info
    public void setUpUser(String userID){
        DatabaseReference userInformation = mDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("User Information");

        DatabaseReference userInnerId = userInformation.child("User ID");

        userInnerId.setValue(userID);

        DatabaseReference userName = userInformation.child("Name");

        userName.setValue("ChiuPlus");

        DatabaseReference userCurrency = userInformation.child("Cybercoins");

        userCurrency.setValue(100);

        DatabaseReference userLevel = userInformation.child("Level");

        userLevel.setValue(1);

        DatabaseReference userXP= userInformation.child("XP");

        userXP.setValue(0);


        userSkills = mDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("Skills");

        skillsRef = mDatabase.getReference("Skills");

        skillsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                availableSkills.clear();
                for(DataSnapshot keyNode:dataSnapshot.getChildren()){
                    Skill skill = keyNode.getValue(Skill.class);
                    availableSkills.add(skill);
                }

                for(Skill skill:availableSkills){
                    DatabaseReference currentSkill = userSkills.child(skill.getSkillname());
                    currentSkill.setValue(skill);
                }

                skillsRef.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        userItemsConsumables = mDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("Items").child("Consumables");

        consumablesRef = mDatabase.getReference("Items").child("Consumables");

        consumablesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                availableConsumables.clear();
                for(DataSnapshot keyNode:dataSnapshot.getChildren()){
                    Consumable consumable = keyNode.getValue(Consumable.class);
                    availableConsumables.add(consumable);
                }

                for(Consumable consumable:availableConsumables){
                    DatabaseReference currentConsumable = userItemsConsumables.child(consumable.getName());
                    currentConsumable.setValue(consumable);
                }

                consumablesRef.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //Add medals table from db to user table

        userItemsMedals = mDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("Items").child("Medals");

        medalsRef = mDatabase.getReference("Items").child("Medals");

        medalsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                availableMedals.clear();
                for(DataSnapshot keyNode:dataSnapshot.getChildren()){
                    Medal medal = keyNode.getValue(Medal.class);
                    availableMedals.add(medal);
                }

                for(Medal medal:availableMedals){
                    DatabaseReference currentMedal = userItemsMedals.child(medal.getName());
                    currentMedal.setValue(medal);
                }

                medalsRef.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        userItemsAppearances = mDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("Items").child("Appearance");

        appearancesRef = mDatabase.getReference("Items").child("Appearance");

        appearancesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                availableAppearances.clear();
                for(DataSnapshot keyNode:dataSnapshot.getChildren()){
                    Appearance appearance = keyNode.getValue(Appearance.class);
                    availableAppearances.add(appearance);
                }

                for(Appearance appearance:availableAppearances){
                    DatabaseReference currentAppearance = userItemsAppearances.child(appearance.getName());
                    currentAppearance.setValue(appearance);
                }

                appearancesRef.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void setNewUserName(String newUserName){
        DatabaseReference userName = mDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("User Information").child("Name");

        userName.setValue(newUserName);
    }

    //Add a friend to the database;

    public boolean addFriend(final String friendID){
        final DatabaseReference userFriendList = mDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("FriendList");

        DatabaseReference usersFromDb = mDatabase.getReference("Users");

        usersFromDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String userUID = dataSnapshot.getKey();
                DatabaseReference getChildUserInformation = mDatabase.getReference("Users").child(userUID).child("User Information").child("User ID");

                getChildUserInformation.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String thisUserId = dataSnapshot.getValue(String.class);

                        if(thisUserId.equals(friendID)){
                            DatabaseReference newFriendRef = userFriendList.child(friendID);
                            newFriendRef.setValue(userUID);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return false;
    }

    //Getters for userInfo

    public String getUserName() {
        return userName;
    }

    public Integer getUserLevel() {
        return userLevel;
    }

    public Integer getUserXp() {
        return userXp;
    }

    public Integer getUserCoins() {
        return userCoins;
    }
}
