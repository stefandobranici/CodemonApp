package com.example.dobra.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class ModeSelectorActivity extends AppCompatActivity {

    private ImageButton binauralbeatsBtn, mnemonicsBtn, plainBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode_selector);

        binauralBeatsButtonOnClickListener();
        mnemonicsButtonOnClickListener();
        plainButtonOnClickListener();
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


}
