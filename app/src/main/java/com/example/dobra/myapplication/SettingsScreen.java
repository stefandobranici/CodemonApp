package com.example.dobra.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SettingsScreen extends AppCompatActivity {

    private ImageView logoutBtn, changeModeBtn;
    private static final String FILE_NAME = "currentuser.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_screen);

        setLogoutBtnOnClickListener();

        setChangeGameModeBtnOnClickListener();

    }

    private void setLogoutBtnOnClickListener(){
        logoutBtn = (ImageView) findViewById(R.id.logoutButton);

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearData();
                MainScreenActivity.mainActivity.finish();
                Intent backToMain = new Intent("android.intent.action.MainScreenActivity");
                startActivity(backToMain);
                finish();
            }
        });
    }

    private void setChangeGameModeBtnOnClickListener(){
        changeModeBtn = (ImageView) findViewById(R.id.changeModeButton);

        changeModeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent changeGameMode = new Intent("android.intent.action.ModeSelectorActivity");
                startActivity(changeGameMode);
                finish();
            }
        });
    }

    private void clearData(){
        FileOutputStream fos = null;

        try{
            String data = "";
            fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
            fos.write(data.getBytes());
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos!=null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
