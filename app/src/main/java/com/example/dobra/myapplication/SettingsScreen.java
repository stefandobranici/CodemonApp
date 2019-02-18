package com.example.dobra.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SettingsScreen extends AppCompatActivity {

    private Button logoutBtn;
    private static final String FILE_NAME = "currentuser.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_screen);

        logoutBtnOnClickListener();

    }

    private void logoutBtnOnClickListener(){
        logoutBtn = (Button) findViewById(R.id.logoutButton);

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearData();
                MainScreenActivity.mainActivity.finish();
                Intent back_to_main = new Intent("android.intent.action.MainScreenActivity");
                startActivity(back_to_main);
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
