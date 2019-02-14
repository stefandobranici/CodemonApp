package com.example.dobra.myapplication;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class ModeSelectorActivity extends AppCompatActivity {

    private ImageButton binauralbeatsBtn, mnemonicsBtn, plainBtn;

    HomeWatcher mHomeWatcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode_selector);

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
    }

    public void binauralBeatsButtonOnClickListener(){
        binauralbeatsBtn = (ImageButton) findViewById(R.id.bbeatsselect);

        binauralbeatsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

}
