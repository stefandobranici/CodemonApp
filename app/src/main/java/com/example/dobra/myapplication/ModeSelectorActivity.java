package com.example.dobra.myapplication;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ModeSelectorActivity extends AppCompatActivity {

    private ImageView binauralbeatsBtn, mnemonicsBtn, plainBtn;

    private FirebaseDatabase mDatabase;

    private FirebaseAuth mAuth;

    private DatabaseReference modeSelectorReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode_selector);

        mDatabase = FirebaseDatabase.getInstance();

        mAuth = FirebaseAuth.getInstance();

        modeSelectorReference = mDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("User Information").child("Mode");

        binauralBeatsButtonOnClickListener();
        mnemonicsButtonOnClickListener();
        plainButtonOnClickListener();
    }

    public void binauralBeatsButtonOnClickListener(){
        binauralbeatsBtn = (ImageView) findViewById(R.id.bbeatsselect);

        binauralbeatsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                modeSelectorReference.setValue("binaural");

                Intent goToMainMenu = new Intent("android.intent.action.MenuScreenActivity");
                startActivity(goToMainMenu);
                finish();
            }
        });
    }

    public void mnemonicsButtonOnClickListener(){
        mnemonicsBtn = (ImageView) findViewById(R.id.mnemonicselector);

        mnemonicsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                modeSelectorReference.setValue("mnemonics");

                Intent goToMainMenu = new Intent("android.intent.action.MenuScreenActivity");
                startActivity(goToMainMenu);
                finish();
            }
        });
    }

    public void plainButtonOnClickListener(){
        plainBtn = (ImageView) findViewById(R.id.plainselector);

        plainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                modeSelectorReference.setValue("plain");

                Intent goToMainMenu = new Intent("android.intent.action.MenuScreenActivity");
                startActivity(goToMainMenu);
                finish();
            }
        });
    }


}
