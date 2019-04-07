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
import android.widget.LinearLayout;
import android.widget.TextView;

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

    private LinearLayout selectModeWarningLayout;

    private TextView modeSelectedExplanation, acceptModeSelected, declineModeSelected;

    private String modeSelectedString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode_selector);

        mDatabase = FirebaseDatabase.getInstance();

        mAuth = FirebaseAuth.getInstance();

        modeSelectorReference = mDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("User Information").child("Mode");

        selectModeWarningLayout = (LinearLayout) findViewById(R.id.selectModeWarningLayout);

        modeSelectedExplanation = (TextView) findViewById(R.id.modeSelectedExplanation);

        modeSelectedString = "";

        setAcceptModeSelectedOnClickListener();
        setDeclineModeSelected();

        binauralBeatsButtonOnClickListener();
        mnemonicsButtonOnClickListener();
        plainButtonOnClickListener();
    }

    public void binauralBeatsButtonOnClickListener(){
        binauralbeatsBtn = (ImageView) findViewById(R.id.bbeatsselect);

        binauralbeatsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                modeSelectedExplanation.setText("Enhance your learning experience through the use of brain waves!\n\nNote that the use of headphones is required at all times in this mode!");

                selectModeWarningLayout.setVisibility(View.VISIBLE);

                modeSelectedString = "binaural";

/*                modeSelectorReference.setValue("binaural");

                Intent goToMainMenu = new Intent("android.intent.action.MenuScreenActivity");
                startActivity(goToMainMenu);
                finish();*/
            }
        });
    }

    public void mnemonicsButtonOnClickListener(){
        mnemonicsBtn = (ImageView) findViewById(R.id.mnemonicselector);

        mnemonicsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                modeSelectedExplanation.setText("The Mnemonics learning mode allows you to draw concepts in the way that you imagine them!\n\nUse your creativity to learn and remember difficult concepts!");

                selectModeWarningLayout.setVisibility(View.VISIBLE);

                modeSelectedString = "mnemonics";


              /*  modeSelectorReference.setValue("mnemonics");

                Intent goToMainMenu = new Intent("android.intent.action.MenuScreenActivity");
                startActivity(goToMainMenu);
                finish();*/
            }
        });
    }

    public void plainButtonOnClickListener(){
        plainBtn = (ImageView) findViewById(R.id.plainselector);

        plainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                modeSelectedExplanation.setText("The Plain learning mode offers no additional features to enhance your learning experience.\n\nChoose this mode if you prefer a simple learning experience!");

                selectModeWarningLayout.setVisibility(View.VISIBLE);

                modeSelectedString = "plain";


/*                modeSelectorReference.setValue("plain");

                Intent goToMainMenu = new Intent("android.intent.action.MenuScreenActivity");
                startActivity(goToMainMenu);
                finish();*/
            }
        });
    }

    private void setAcceptModeSelectedOnClickListener(){
        acceptModeSelected = (TextView) findViewById(R.id.acceptModeSelected);
        acceptModeSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modeSelectorReference.setValue(modeSelectedString);

                Intent goToMainMenu = new Intent("android.intent.action.MenuScreenActivity");
                startActivity(goToMainMenu);
                finish();
            }
        });
    }

    private void setDeclineModeSelected(){
        declineModeSelected = (TextView) findViewById(R.id.declineModeSelected);
        declineModeSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectModeWarningLayout.setVisibility(View.GONE);

                modeSelectedString = "";
            }
        });
    }


}
