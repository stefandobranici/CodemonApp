package com.example.dobra.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SettingsScreen extends AppCompatActivity {

    private ImageView logoutBtn, changeModeBtn, changeNameBtn;

    private TextView nameChangerButton, closeNameChangeLayout, modeChangeWarningButton, closeModeChangeWarningLayout;

    private EditText userNameChangeField;

    private ImageButton backbutton;

    LinearLayout nameChangerLayout, changeModeWarningLayout;

    private static final String FILE_NAME = "currentuser.txt";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_screen);

        setLogoutBtnOnClickListener();

        setModeChangeWarningButtonOnClickListener();

        setChangeModeBtnOnClickListener();

        setChangeNameBtnOnClickListener();

        setCloseModeChangeWarningLayoutOnClickListener();

        setBackButtonOnClickListener();

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

    private void setChangeModeBtnOnClickListener(){
        changeModeBtn = (ImageView) findViewById(R.id.changeModeButton);

        changeModeWarningLayout = (LinearLayout) findViewById(R.id.changeModeWarningLayout);

        changeModeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeModeWarningLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setModeChangeWarningButtonOnClickListener(){

        modeChangeWarningButton = (TextView) findViewById(R.id.modeChangeWarningButton);

        modeChangeWarningButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CurrentUserInformation.getInstance().deleteProgress();

                Intent changeGameMode = new Intent("android.intent.action.ModeSelectorActivity");
                startActivity(changeGameMode);
                finish();
            }
        });
    }

    private void setCloseModeChangeWarningLayoutOnClickListener(){
        closeModeChangeWarningLayout = (TextView) findViewById(R.id.closeModeChangeWarningLayout);

        closeModeChangeWarningLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeModeWarningLayout.setVisibility(View.GONE);
            }
        });
    }

    private void setChangeNameBtnOnClickListener(){
        changeNameBtn = (ImageView) findViewById(R.id.changeNameButton);

        nameChangerLayout = (LinearLayout) findViewById(R.id.changeNameLayout);

        nameChangerButton = (TextView) findViewById(R.id.nameChangerButton);

        closeNameChangeLayout = (TextView) findViewById(R.id.closeNameChangeLayout);

        userNameChangeField = (EditText) findViewById(R.id.userNameChangeField);

        userNameChangeField.setText(CurrentUserInformation.getInstance().getUserName());

        nameChangerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userInput = userNameChangeField.getText().toString();

                if(userInput.equals(CurrentUserInformation.getInstance().getUserName())){
                    Toast.makeText(getApplicationContext(), "New name is identical to previous one!", Toast.LENGTH_SHORT).show();
                } else if(TextUtils.isEmpty(userInput)){
                    Toast.makeText(getApplicationContext(), "Name cannot be empty!", Toast.LENGTH_SHORT).show();
                } else if(userInput.length()<4){
                    Toast.makeText(getApplicationContext(), "Name must be at least four characters long!", Toast.LENGTH_SHORT).show();
                } else if(userInput.length()>12){
                    Toast.makeText(getApplicationContext(), "Name must be at most twelve characters long!", Toast.LENGTH_SHORT).show();
                } else {
                    CurrentUserInformation.getInstance().setNewUserName(userInput);
                    Toast.makeText(getApplicationContext(), "Name changed successfully!", Toast.LENGTH_SHORT).show();
                    nameChangerLayout.setVisibility(View.GONE);
                }
            }
        });

        closeNameChangeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameChangerLayout.setVisibility(View.GONE);
            }
        });

        changeNameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nameChangerLayout.setVisibility(View.VISIBLE);
            }
        });

    }

    private void setBackButtonOnClickListener(){
        backbutton = (ImageButton) findViewById(R.id.backbutton);

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
