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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ModeSelectorActivity extends AppCompatActivity {

    private ImageButton binauralbeatsBtn, mnemonicsBtn, plainBtn;

    private ImageButton settingsBtn;

    private FirebaseDatabase mDatabase;

    private FirebaseAuth mAuth;

    private DatabaseReference modeSelectorReference;

    HomeWatcher mHomeWatcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode_selector);

        mDatabase = FirebaseDatabase.getInstance();

        mAuth = FirebaseAuth.getInstance();

        String currentUser = mAuth.getCurrentUser().getUid();

        modeSelectorReference = mDatabase.getReference("Users").child(currentUser).child("mode");

        binauralBeatsButtonOnClickListener();
        mnemonicsButtonOnClickListener();
        plainButtonOnClickListener();

        doBindService();
        Intent music = new Intent();
        music.setClass(this, MusicService.class);
        startService(music);

        mHomeWatcher = new HomeWatcher(this);
        mHomeWatcher.setOnHomePressedListener(new HomeWatcher.OnHomePressedListener() {
            @Override
            public void onHomePressed() {
                if (mServ != null) {
                    mServ.pauseMusic();
                }
            }
            @Override
            public void onHomeLongPressed() {
                if (mServ != null) {
                    mServ.pauseMusic();
                }
            }
        });
        mHomeWatcher.startWatch();

        setSettingsBtnOnClickListener();
    }

    public void binauralBeatsButtonOnClickListener(){
        binauralbeatsBtn = (ImageButton) findViewById(R.id.bbeatsselect);

        binauralbeatsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mode = "binaural";

                modeSelectorReference.setValue(mode);

                Intent gamemode_intent = new Intent("android.intent.action.ChapterSelectorActivity");
                startActivity(gamemode_intent);
                finish();
            }
        });
    }

    public void mnemonicsButtonOnClickListener(){
        mnemonicsBtn = (ImageButton) findViewById(R.id.mnemonicselector);

        mnemonicsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mode = "mnemonics";

                modeSelectorReference.setValue(mode);

                Intent gamemode_intent = new Intent("android.intent.action.ChapterSelectorActivity");
                startActivity(gamemode_intent);
                finish();
            }
        });
    }

    public void plainButtonOnClickListener(){
        plainBtn = (ImageButton) findViewById(R.id.plainselector);

        plainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mode = "plain";

                modeSelectorReference.setValue(mode);

                Intent gamemode_intent = new Intent("android.intent.action.ChapterSelectorActivity");
                startActivity(gamemode_intent);
                finish();
            }
        });
    }
    private boolean mIsBound = false;
    private MusicService mServ;
    private ServiceConnection Scon = new ServiceConnection(){

        public void onServiceConnected(ComponentName name, IBinder
                binder) {
            mServ = ((MusicService.ServiceBinder)binder).getService();
        }

        public void onServiceDisconnected(ComponentName name) {
            mServ = null;
        }
    };

    void doBindService(){
        bindService(new Intent(this,MusicService.class),
                Scon,Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }

    void doUnbindService()
    {
        if(mIsBound)
        {
            unbindService(Scon);
            mIsBound = false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mServ != null) {
            mServ.resumeMusic();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        doUnbindService();
/*        Intent music = new Intent();
        music.setClass(this,MusicService.class);
        stopService(music);*/

    }

    private void setSettingsBtnOnClickListener(){
        settingsBtn = (ImageButton) findViewById(R.id.settingsbutton);
        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settings_intent = new Intent("android.intent.action.SettingsScreen");
                startActivity(settings_intent);
            }
        });
    }

}
