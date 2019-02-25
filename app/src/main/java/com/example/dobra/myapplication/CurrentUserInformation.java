package com.example.dobra.myapplication;

import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CurrentUserInformation {

    private static final CurrentUserInformation SINGLE_INSTANCE = new CurrentUserInformation();

    private FirebaseAuth mAuth;

    private FirebaseDatabase mDatabase;

    private DatabaseReference mChaptersReference;

    private DatabaseReference userCurrentActiveChapter, userCurrentActiveLevel;

    public Map<Integer, Integer> progressionOfChapters;

    public Map<Integer, Integer> numberOfMissionsInChapter;

    private Integer currentActiveChapter, currentActiveLevel;

    private Integer chapterSelected;

    private Level levelSelectedForPlay;


    private CurrentUserInformation() {

        progressionOfChapters = new HashMap<>();

        numberOfMissionsInChapter = new HashMap<>();

        mAuth = FirebaseAuth.getInstance();

        mDatabase = FirebaseDatabase.getInstance();

    }

    public static CurrentUserInformation getInstance() {
        return SINGLE_INSTANCE;
    }

    public void getUserProgressionStatus(final ChapterSelectorActivity chapterSelectorActivity){

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

                chapterSelectorActivity.setUpLayout();
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

    public Integer getCurrentActiveChapter(){
        return currentActiveChapter;
    }

    public Integer getCurrentActiveLevel(){
        return currentActiveLevel;
    }

    public Integer getChapterSelected(){
        return chapterSelected;
    }

    public Level getLevelSelectedForPlay(){
        return levelSelectedForPlay;
    }

    public void setCurrentActiveChapter(int updatedChapter){
        currentActiveChapter = updatedChapter;
    }

    public void setCurrentActiveLevel(int updatedLevel){
        currentActiveLevel = updatedLevel;
    }


    public void setChapterSelected(Integer chapterSelected){
        this.chapterSelected = chapterSelected;
    }

    public void setLevelSelectedForPlay(Level level){
        levelSelectedForPlay = level;
    }
}
