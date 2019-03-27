package com.example.dobra.myapplication;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CurrentUserInformation {

    private static final CurrentUserInformation SINGLE_INSTANCE = new CurrentUserInformation();

    private FirebaseAuth mAuth;

    private FirebaseDatabase mDatabase;

    private DatabaseReference mChaptersReference;

    private DatabaseReference userCurrentActiveChapter, userCurrentActiveLevel, userInformation, userNameRef, userInnerIDRef, userLevelRef, userXpRef, userCoinsRef, userHpRef, userImgUrl, userCurrentNotepadContent, userCurrentEquippedItem, modeSelectorReference;

    private DatabaseReference userItemsAppearances, userItemsMedals, userItemsConsumables, userSkills, skillsRef, consumablesRef, medalsRef, appearancesRef;

    private DatabaseReference userSkillsFromDbRef, userItemsMedalsFromDbRef, userItemsConsumablesFromDbRef, userItemsAppearanceFromDbRef;

    public Map<String, Skill> userSkillsCollection;

    public Map<String, Consumable> userConsumablesCollection;

    public Map<String, Appearance> userAppearancesCollection;

    public Map<String, Medal> userMedalsCollection;

    public Map<Integer, Integer> progressionOfChapters;

    public Map<Integer, Integer> numberOfMissionsInChapter;

    private Integer currentActiveChapter, currentActiveLevel;

    private Integer chapterSelected;

    private Level levelSelectedForPlay;

    private String userName, userProfilePictureURL, userPosePictureURL, userPersonalID;

    private Integer userLevel, userXp, userCoins, userHealth;

    private List<Skill> availableSkills;

    private List<Consumable> availableConsumables;

    private List<Medal> availableMedals;

    private List<Appearance> availableAppearances;

    private String friendSelectedForRemove;

    private String userNotepadContent;

    private String currentEquippedItem, itemToBeEquipped;

    private String userGameModeSelected;

    private long startTime;
    private long endTime;

    private CurrentUserInformation() {

        //Initiate arrays
        progressionOfChapters = new HashMap<>();

        numberOfMissionsInChapter = new HashMap<>();

        userConsumablesCollection = new HashMap<>();

        userSkillsCollection = new HashMap<>();

        userAppearancesCollection = new HashMap<>();

        userMedalsCollection = new HashMap<>();

        availableSkills = new ArrayList<>();

        availableAppearances = new ArrayList<>();

        availableMedals = new ArrayList<>();

        availableConsumables = new ArrayList<>();

        userGameModeSelected = "plain";

        startTime = 0;

        endTime = 0;

        for (int i = 1; i < 8; i++) {
            numberOfMissionsInChapter.put(i, 0);
            progressionOfChapters.put(i, 0);
        }

        //Setup some initial variables to avoid null pointers;
        userName = "N/A/N";
        userLevel = 0;
        userXp = 0;
        userCoins = 0;
        userHealth = 0;
        friendSelectedForRemove = "";
        userPersonalID = "";
        userProfilePictureURL = "https://firebasestorage.googleapis.com/v0/b/myapplication-9586f.appspot.com/o/Profile%2Fprofilepic.png?alt=media&token=1d0d0f95-4d1c-475a-bc36-4ddb79b49955";
        userPosePictureURL = "https://firebasestorage.googleapis.com/v0/b/myapplication-9586f.appspot.com/o/Poses%2Fchiuplusbasic.png?alt=media&token=1514f5cb-50be-4e1e-ae49-ae177853104b";
        currentActiveLevel = 0;
        currentActiveChapter = 0;
        userNotepadContent = "Write your notes here!";
        currentEquippedItem = "Basic";
        itemToBeEquipped = "";

        //Initiate instance of firebase auth and database;
        mAuth = FirebaseAuth.getInstance();

        mDatabase = FirebaseDatabase.getInstance();

    }

    public static CurrentUserInformation getInstance() {
        return SINGLE_INSTANCE;
    }


    //This is the main method that will add listeners to references to pull important information from the database used later in the app;
    //This is done upon logging in or signing up;
    public void getUserProgressionStatus() {

        getChapterProgressionInformation();

        getUserInformation();

        getUserSkills();

        getUserInvetoryStatus();
    }


    //This adds listeners to retrieve chapter information from the db;
    private void getChapterProgressionInformation() {

        mChaptersReference = mDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("Chapter Progression");

        mChaptersReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    progressionOfChapters.clear();
                    numberOfMissionsInChapter.clear();
                    for (DataSnapshot keyNode : dataSnapshot.getChildren()) {
                        for (DataSnapshot keyNode2 : keyNode.getChildren()) {
                            Level level = keyNode2.getValue(Level.class);
                            if (level.isCompleted()) {
                                if (progressionOfChapters.containsKey(level.getChapter())) {
                                    progressionOfChapters.put(level.getChapter(), progressionOfChapters.get(level.getChapter()) + 1);
                                } else {
                                    progressionOfChapters.put(level.getChapter(), 1);
                                }
                            }

                            if (numberOfMissionsInChapter.containsKey(level.getChapter())) {
                                numberOfMissionsInChapter.put(level.getChapter(), numberOfMissionsInChapter.get(level.getChapter()) + 1);
                            } else {
                                numberOfMissionsInChapter.put(level.getChapter(), 1);
                            }

                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //This adds listeners to retrieve user information such as current username, userlevel, userxp, usercoins, user currentlevel, user currentchapter, etc from the db;
    private void getUserInformation() {
        userInformation = mDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("User Information");

        userNameRef = userInformation.child("Name");

        userNameRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    userName = dataSnapshot.getValue(String.class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        userInnerIDRef = userInformation.child("User ID");

        userInnerIDRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    if(!TextUtils.isEmpty(dataSnapshot.getValue(String.class))) {
                        userPersonalID = dataSnapshot.getValue(String.class);
                    }

                    if(userPersonalID!="") {
                        userInnerIDRef.removeEventListener(this);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        userLevelRef = userInformation.child("Level");

        userLevelRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    userLevel = dataSnapshot.getValue(Integer.class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        userXpRef = userInformation.child("XP");

        userXpRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    userXp = dataSnapshot.getValue(Integer.class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        userCoinsRef = userInformation.child("Cybercoins");

        userCoinsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    userCoins = dataSnapshot.getValue(Integer.class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        userHpRef = userInformation.child("HP");

        userHpRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    userHealth = dataSnapshot.getValue(Integer.class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        userCurrentActiveChapter = mDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("Game Status").child("Current Chapter");

        userCurrentActiveChapter.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    currentActiveChapter = dataSnapshot.getValue(Integer.class);
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
                if (dataSnapshot.exists()) {
                    currentActiveLevel = dataSnapshot.getValue(Integer.class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        userCurrentNotepadContent = mDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("Notepad").child("Content");

        userCurrentNotepadContent.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    userNotepadContent = dataSnapshot.getValue(String.class);
                } else {
                    userCurrentNotepadContent.setValue(userNotepadContent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        userCurrentEquippedItem = mDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("User Information").child("EquippedItem");

        userCurrentEquippedItem.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    currentEquippedItem = dataSnapshot.getValue(String.class);

                    DatabaseReference profilePictureStorageLocation = mDatabase.getReference("Items").child("ProfilePictures").child(currentEquippedItem).child("ImageURL");

                    profilePictureStorageLocation.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                userProfilePictureURL = dataSnapshot.getValue(String.class);
                                DatabaseReference userProfilePictureInDbrRef = mDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("User Information").child("Profile Picture");

                                userProfilePictureInDbrRef.setValue(userProfilePictureURL);

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    DatabaseReference posePictureStorageLocation = mDatabase.getReference("Items").child("PosesPictures").child(currentEquippedItem).child("ImageURL");

                    posePictureStorageLocation.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                userPosePictureURL = dataSnapshot.getValue(String.class);

                                DatabaseReference userPosePictureInDbrRef = mDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("User Information").child("Pose Picture");

                                userPosePictureInDbrRef.setValue(userProfilePictureURL);
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                } else {
                    userCurrentEquippedItem.setValue(currentEquippedItem);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        modeSelectorReference = mDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("User Information").child("Mode");

        modeSelectorReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    String modeSelected = dataSnapshot.getValue(String.class);


                    if (TextUtils.isEmpty(modeSelected)) {

                    } else {
                        userGameModeSelected = modeSelected;
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getUserInvetoryStatus() {
        userItemsMedalsFromDbRef = mDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("Items").child("Medals");

        userItemsConsumablesFromDbRef = mDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("Items").child("Consumables");

        userItemsAppearanceFromDbRef = mDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("Items").child("Appearance");


        userItemsMedalsFromDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    if (dataSnapshot.hasChildren()) {
                        userMedalsCollection.clear();
                        for (DataSnapshot keyNode : dataSnapshot.getChildren()) {
                            Medal medal = keyNode.getValue(Medal.class);
                            userMedalsCollection.put(medal.getName(), medal);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        userItemsConsumablesFromDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    if (dataSnapshot.hasChildren()) {
                        userConsumablesCollection.clear();
                        for (DataSnapshot keyNode : dataSnapshot.getChildren()) {
                            Consumable consumable = keyNode.getValue(Consumable.class);
                            userConsumablesCollection.put(consumable.getName(), consumable);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        userItemsAppearanceFromDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    if (dataSnapshot.hasChildren()) {
                        userAppearancesCollection.clear();
                        for (DataSnapshot keyNode : dataSnapshot.getChildren()) {
                            Appearance appearance = keyNode.getValue(Appearance.class);
                            userAppearancesCollection.put(appearance.getName(), appearance);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void getUserSkills() {
        userSkillsFromDbRef = mDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("Skills");

        userSkillsFromDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    if (dataSnapshot.hasChildren()) {
                        userSkillsCollection.clear();
                        for (DataSnapshot keyNode : dataSnapshot.getChildren()) {
                            Skill skill = keyNode.getValue(Skill.class);
                            userSkillsCollection.put(skill.getSkillname(), skill);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    //Getters for userInfo

    public String getUserName() {
        return userName;
    }

    public void setNewUserName(String newUserName) {
        DatabaseReference userName = mDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("User Information").child("Name");

        userName.setValue(newUserName);
    }

    public Integer getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(Integer newLvl) {
        DatabaseReference userLevel = mDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("User Information").child("Level");

        userLevel.setValue(newLvl);
    }

    public Integer getUserXp() {
        return userXp;
    }

    public void setUserXp(Integer newXp) {
        DatabaseReference userXp = mDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("User Information").child("XP");

        userXp.setValue(newXp);
    }

    public void increaseUserXp(Integer newXp) {
        DatabaseReference userXp = mDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("User Information").child("XP");

        userXp.setValue(getUserXp() + newXp);
    }

    public Integer getUserCoins() {
        return userCoins;
    }

    public void setUserCoins(Integer newCoins) {
        DatabaseReference userCoinsRef = mDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("User Information").child("Cybercoins");

        userCoinsRef.setValue(userCoins+newCoins);
    }

    public Integer getUserHealth() {
        return userHealth;
    }

    public void setUserHealth(Integer newHealth) {
        DatabaseReference userHealth = mDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("User Information").child("HP");

        userHealth.setValue(newHealth);
    }

    public void increaseUserHealth(Integer newHealth) {
        DatabaseReference userHealth = mDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("User Information").child("HP");

        userHealth.setValue(getUserHealth()+newHealth);
    }

    public String getUserProfilePictureURL() {
        return userProfilePictureURL;
    }

    public String getUserPosePictureURL() {
        return userPosePictureURL;
    }

    public String getCurrentEquippedItem(){
        return currentEquippedItem;
    }

    public void setItemToBeEquipped(String itemToEquip){
        itemToBeEquipped = itemToEquip;
    }

    public String getItemToBeEquipped(){
        return itemToBeEquipped;
    }

    public void setUserCurrentEquippedItem(String newItem){
        DatabaseReference userCurrentEquippedItemRef = mDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("User Information").child("EquippedItem");
        if(currentEquippedItem.equals(newItem)){
            userCurrentEquippedItemRef.setValue("Basic");
            currentEquippedItem = "Basic";
        } else {
            userCurrentEquippedItemRef.setValue(newItem);
            currentEquippedItem = newItem;
        }
    }

    public String getUserNotepadContent(){
        return userNotepadContent;
    }

    public void setUserNotepadContent(String newContent){
        DatabaseReference currentNodepadContentFromDbRef = mDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("Notepad").child("Content");
        currentNodepadContentFromDbRef.setValue(newContent);
    }

    public String getUserGameModeSelected() {
        return userGameModeSelected;
    }

    public void setUserGameModeSelected(String userGameModeSelected) {
        this.userGameModeSelected = userGameModeSelected;
    }

    public void updateConsumableQuantity(String consumableName, Integer quantityUpdater){
        DatabaseReference consumableToBeUpdated = mDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("Items").child("Consumables").child(consumableName).child("quantity");

        consumableToBeUpdated.setValue(userConsumablesCollection.get(consumableName).getQuantity()+quantityUpdater);


    }

    public String getFriendSelectedForRemove() {
        return friendSelectedForRemove;
    }

    public void setFriendSelectedForRemove(String friendSelectedForRemove) {
        this.friendSelectedForRemove = friendSelectedForRemove;
    }

    public Integer getCurrentActiveChapter() {
        return currentActiveChapter;
    }

    //Dont remember what this does to be honest
    public Integer getCurrentActiveLevel() {
        return currentActiveLevel;
    }

    //Dont remember what this does to be honest
    public Integer getChapterSelected() {
        return chapterSelected;
    }

    //Dont remember what this does to be honest
    public Level getLevelSelectedForPlay() {
        return levelSelectedForPlay;
    }

    ///Dont remember what this does to be honest
    public void setCurrentActiveChapter(Integer updatedChapter) {
        DatabaseReference currentActiveChapterRef = mDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("Game Status").child("Current Chapter");
        currentActiveChapterRef.setValue(updatedChapter);
    }

    //Dont remember what this does to be honest
    public void setCurrentActiveLevel(Integer updatedLevel) {
        DatabaseReference currentActiveLevelRef = mDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("Game Status").child("Current Level");
        currentActiveLevelRef.setValue(updatedLevel);
    }

    //This method sets the current chapter in progress;
    public void setChapterSelected(Integer chapterSelected) {
        this.chapterSelected = chapterSelected;
    }

    //This method sets the current level in progress;
    public void setLevelSelectedForPlay(Level level) {
        levelSelectedForPlay = level;
    }

    public Integer getTotalLevelsAvailableForPlay() {
        Integer totalLevelsAvailable = 0;

        for (Map.Entry missionsInCurrentChapter : numberOfMissionsInChapter.entrySet()) {
            totalLevelsAvailable += (Integer) missionsInCurrentChapter.getValue();
        }

        return totalLevelsAvailable;
    }

    public void unlockUserSkill(String skillName){
        DatabaseReference skillToBeUnlockedRef = mDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("Skills").child(skillName).child("unlocked");
        skillToBeUnlockedRef.setValue(true);
    }

    public void unlockUserAppearanceItem(String appearanceItemName){
        if(!userAppearancesCollection.get(appearanceItemName).getUnlocked()) {
            DatabaseReference itemToBeUnlockedRef = mDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("Items").child("Appearance").child(appearanceItemName).child("unlocked");
            itemToBeUnlockedRef.setValue(true);
        }
    }

    public void unlockCurrentLevel(Level level){
        Integer modulusOfLevel = level.getLevel()%21;

        if(modulusOfLevel == 0){
            modulusOfLevel = 21;
        }

        if(modulusOfLevel == 2){
            Integer childLevel = modulusOfLevel-1;
            DatabaseReference currentLevelToBeUnlocked = mDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("Chapter Progression").child(level.getChapter().toString()).child(childLevel.toString()).child("completed");
            currentLevelToBeUnlocked.setValue(true);
        }

        DatabaseReference currentLevelToBeUnlocked = mDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("Chapter Progression").child(level.getChapter().toString()).child(modulusOfLevel.toString()).child("completed");
        currentLevelToBeUnlocked.setValue(true);
    }

    //Set up singup info
    public void setUpUser(String userID) {
        DatabaseReference userInformation = mDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("User Information");

        DatabaseReference userInnerId = userInformation.child("User ID");

        userInnerId.setValue(userID);

        DatabaseReference userName = userInformation.child("Name");

        userName.setValue("ChiuPlus");

        DatabaseReference userCurrency = userInformation.child("Cybercoins");

        userCurrency.setValue(100);

        DatabaseReference userLevel = userInformation.child("Level");

        userLevel.setValue(1);

        DatabaseReference userXP = userInformation.child("XP");

        userXP.setValue(0);

        DatabaseReference userHP = userInformation.child("HP");

        userHP.setValue(10);

        DatabaseReference userProfilePicture = userInformation.child("Profile Picture");

        userProfilePicture.setValue("https://firebasestorage.googleapis.com/v0/b/myapplication-9586f.appspot.com/o/Profile%2Fprofilepic.png?alt=media&token=1d0d0f95-4d1c-475a-bc36-4ddb79b49955");

        DatabaseReference userCurrentlyEquippedItemRef = userInformation.child("EquippedItem");

        userCurrentlyEquippedItemRef.setValue("Basic");

        userCurrentActiveChapter = mDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("Game Status").child("Current Chapter");

        userCurrentActiveChapter.setValue(1);

        setCurrentActiveChapter(1);


        userCurrentActiveLevel = mDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("Game Status").child("Current Level");

        userCurrentActiveLevel.setValue(1);

        setCurrentActiveLevel(1);


        userSkills = mDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("Skills");

        skillsRef = mDatabase.getReference("Skills");

        DatabaseReference userTimeSpentLearningRef = mDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("Game Status").child("TimeSpentLearning");

        userTimeSpentLearningRef.setValue(0);

        DatabaseReference userTimeSpentTrainingRef = mDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("Game Status").child("TimeSpentTraining");

        userTimeSpentTrainingRef.setValue(0);

        skillsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                availableSkills.clear();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    Skill skill = keyNode.getValue(Skill.class);
                    availableSkills.add(skill);
                }

                for (Skill skill : availableSkills) {
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

        consumablesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                availableConsumables.clear();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    Consumable consumable = keyNode.getValue(Consumable.class);
                    availableConsumables.add(consumable);
                }

                for (Consumable consumable : availableConsumables) {
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

        medalsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                availableMedals.clear();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    Medal medal = keyNode.getValue(Medal.class);
                    availableMedals.add(medal);
                }

                for (Medal medal : availableMedals) {
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

        appearancesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                availableAppearances.clear();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    Appearance appearance = keyNode.getValue(Appearance.class);
                    availableAppearances.add(appearance);
                }

                for (Appearance appearance : availableAppearances) {
                    DatabaseReference currentAppearance = userItemsAppearances.child(appearance.getName());
                    currentAppearance.setValue(appearance);
                }

                appearancesRef.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mChaptersReference = mDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("Chapter Progression");

        mChaptersReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                new FirebaseDatabaseHelper().generateUserLevels(new FirebaseDatabaseHelper.DataStatus() {
                    @Override
                    public void DataIsLoaded(List<Level> levels, List<String> keys) {
                        Integer levelId = 1;
                        for (Level level : levels) {
                            if (levelId == 22) {
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

                mChaptersReference.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void deleteProgress() {
        DatabaseReference userChapterProgress = mDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("Chapter Progression");
        userChapterProgress.setValue(null);

        DatabaseReference userGameStatus = mDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("Game Status");
        userGameStatus.setValue(null);

        DatabaseReference userItemsStatus = mDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("Items");
        userItemsStatus.setValue(null);

        DatabaseReference userSkillsStatus = mDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("Skills");
        userSkillsStatus.setValue(null);

        DatabaseReference userInformationStatus = mDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("User Information");
        userInformationStatus.setValue(null);

        setUpUser(userPersonalID);
    }

    public void setUserBeginActivity(){
        startTime = System.currentTimeMillis();
    }

    public void setUserEndedLearningActivity(){
        endTime = System.currentTimeMillis();
        final long timeSpend = endTime - startTime;

        final DatabaseReference userTimeSpentLearningRef = mDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("Game Status").child("TimeSpentLearning");

        userTimeSpentLearningRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    long timeAlreadySpentByUser = dataSnapshot.getValue(long.class);

                    userTimeSpentLearningRef.setValue(timeSpend+timeAlreadySpentByUser);
                } else {
                    userTimeSpentLearningRef.setValue(timeSpend);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void setUserEndedTrainingActivity(){
        endTime = System.currentTimeMillis();
        final long timeSpend = endTime - startTime;

        final DatabaseReference userTimeSpentTrainingRef = mDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("Game Status").child("TimeSpentTraining");

        userTimeSpentTrainingRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    long timeAlreadySpentByUser = dataSnapshot.getValue(long.class);

                    userTimeSpentTrainingRef.setValue(timeSpend+timeAlreadySpentByUser);
                } else {
                    userTimeSpentTrainingRef.setValue(timeSpend);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
