package com.example.dobra.myapplication;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import static android.graphics.Color.WHITE;

public class MainScreenActivity extends AppCompatActivity {

    public static Activity mainActivity;

    private final static String FILE_NAME = "currentuser.txt";

    private Typeface cyberFont;

    private TextView loggedInUserID;

    private ImageButton settingsBtn;

    HomeWatcher mHomeWatcher;

    private ImageButton login_button;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        mainActivity = this;

        mAuth = FirebaseAuth.getInstance();

        cyberFont = Typeface.createFromAsset(getAssets(), "font/Cyberverse.otf");

        setUpMusic();

        setSettingsBtnOnClickListener();

        loginButtonOnClickListener();

        FileInputStream fis = null;

        try {
            fis = openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String UID = br.readLine();
            String PW = br.readLine();

            if(!TextUtils.isEmpty(UID) && !TextUtils.isEmpty(PW)){
                loggedInUserID = (TextView) findViewById(R.id.loggedInID);
                loggedInUserID.setTextSize(15);
                loggedInUserID.setTextColor(WHITE);
                loggedInUserID.setTypeface(cyberFont);
                loggedInUserID.setText("User ID: " + UID);

                loginButtonOnClickListenerIfLoggedIn(UID, PW);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis!=null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void loginButtonOnClickListener(){
        login_button = (ImageButton) findViewById(R.id.loginbtn);

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login_intent = new Intent("android.intent.action.LoginActivity");
                startActivity(login_intent);
            }
        });
    }

    public void loginButtonOnClickListenerIfLoggedIn(String ID, String PW) {

        login_button = (ImageButton) findViewById(R.id.loginbtn);

        final String userID = ID;
        final String userPW = PW;

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String innerUserID = userID + "@bathspa.ac.uk";

                mAuth.signInWithEmailAndPassword(innerUserID, userPW).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent mode_selector = new Intent("android.intent.action.ModeSelectorActivity");
                            startActivity(mode_selector);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
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
                Scon, Context.BIND_AUTO_CREATE);
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

    private void setUpMusic(){
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
