package com.example.dobra.myapplication;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseDatabaseHelper {
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReferenceChapters;
    private DatabaseReference mReferenceLevels;
    private List<Level> levels = new ArrayList<>();

    public interface DataStatus{
        void DataIsLoaded(List<Level> levels, List<String> keys);
        void DataIsInserted();
        void DataIsUpdated();
        void DataIsDeleted();
    }

    public FirebaseDatabaseHelper(){
        mDatabase = FirebaseDatabase.getInstance();
        mReferenceChapters = mDatabase.getReference("Chapters");
        mReferenceLevels = mReferenceChapters.child(ChapterSelectorActivity.chapter_selected.toString());
    }

    public void readLevels(final DataStatus dataStatus){
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
}
