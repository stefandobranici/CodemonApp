package com.example.dobra.myapplication;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FirebaseDatabaseHelper {
    private FirebaseDatabase mDatabase;
    private FirebaseAuth mAuth;
    private DatabaseReference mReferenceChapters;
    private DatabaseReference mReferenceLevels;
    private DatabaseReference fromDatabaseChapterInfo;
    private List<Level> levels = new ArrayList<>();
    private Integer currentChapter, currentLevel;

    private DatabaseReference userLevelsProgression;
    private DatabaseReference userLevelsProgressionOnEachChapter;

    private static final String FILE_NAME = "currentuser.txt";

    public interface DataStatus{
        void DataIsLoaded(List<Level> levels, List<String> keys);
        void DataIsInserted();
        void DataIsUpdated();
        void DataIsDeleted();
    }

    public FirebaseDatabaseHelper(){
        mDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
    }

    public void readLevels(final DataStatus dataStatus){
        mReferenceChapters = mDatabase.getReference("Chapters");
        mReferenceLevels = mReferenceChapters.child(ChapterSelectorActivity.chapterSelected.toString());

        mReferenceLevels.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                levels.clear();
                List<String> keys = new ArrayList<>();
                for(DataSnapshot keyNode : dataSnapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    Level level = keyNode.getValue(Level.class);
                    levels.add(level);
                }

                dataStatus.DataIsLoaded(levels, keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void generateUserLevels(){
        userLevelsProgression = mDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("Chapter Progression");

        for(int i = 1; i < 2; i ++){
            levels.clear();
            currentChapter = i;
            userLevelsProgressionOnEachChapter = userLevelsProgression.child(currentChapter.toString());

            userLevelsProgressionOnEachChapter.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(!dataSnapshot.exists()){
                        fromDatabaseChapterInfo = mDatabase.getReference("Chapters").child(currentChapter.toString());

                        fromDatabaseChapterInfo.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for(DataSnapshot keyNode:dataSnapshot.getChildren()){
                                    Level level = keyNode.getValue(Level.class);
                                    levels.add(level);
                                }

                                userLevelsProgression.setValue(levels);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
}
