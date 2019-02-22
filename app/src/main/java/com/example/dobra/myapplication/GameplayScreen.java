package com.example.dobra.myapplication;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import static android.graphics.Color.WHITE;

public class GameplayScreen extends AppCompatActivity {
    private Level level;

    private final String ENEMY_CODEBUG = "Codebug";

    private final String ENEMY_CODEBOSS = "Fatal Error";

    private Typeface cyberFont;

    private LinearLayout topLayout, firstHalfTopLayout, secondHalfTopLayout, middleLayout, bottomLayout,  firstHalfBottomLayout, secondHalfBottomLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameplay_screen);

        topLayout = (LinearLayout) findViewById(R.id.topScreenLayout);

        firstHalfTopLayout = (LinearLayout) findViewById(R.id.topScreenLayoutFirstHalf);

        secondHalfTopLayout = (LinearLayout) findViewById(R.id.topScreenLayoutSecondHalf);

        middleLayout = (LinearLayout) findViewById(R.id.middleScreenLayout);

        bottomLayout = (LinearLayout) findViewById(R.id.bottomScreenLayout);

        firstHalfBottomLayout = (LinearLayout) findViewById(R.id.firstHalfBottomScreenLayout);

        secondHalfBottomLayout = (LinearLayout) findViewById(R.id.secondHalfBottomScreenLayout);

        level = CurrentUserInformation.getInstance().getLevelSelectedForPlay();

        cyberFont = Typeface.createFromAsset(getAssets(), "font/Cyberverse.otf");

        if(level.isGreen()){
            drawScreenLearning();
        } else if(level.isViolette()) {
            drawScreenTraining();
        } else if(level.isRed()) {
            drawScreenBoss();
        }

        loadContent();

        setUpSkillBar();


    }

    private void drawScreenLearning(){
        ImageView trainer = new ImageView(this);

        trainer.setImageResource(R.drawable.trainer);

        topLayout.addView(trainer);
    }

    private void drawScreenTraining(){
        setPlayerDetailsOnScreen();

        setEnemyDetailsOnScreen(ENEMY_CODEBUG);

    }

    private void drawScreenBoss(){
        setPlayerDetailsOnScreen();

        setEnemyDetailsOnScreen(ENEMY_CODEBOSS);

    }

    private void setPlayerDetailsOnScreen(){
        RelativeLayout codemonImageLayout = (RelativeLayout) findViewById(R.id.codemonSpot);

        RelativeLayout codemonNameLayout = (RelativeLayout) findViewById(R.id.codemonName);

        RelativeLayout codemonHealthLayout = (RelativeLayout) findViewById(R.id.codemonHealth);

        TextView playerName = new TextView(this);

        playerName.setText("ChiuPlus");

        playerName.setTextColor(WHITE);

        playerName.setTypeface(cyberFont);

        playerName.setGravity(View.TEXT_ALIGNMENT_CENTER);

        playerName.setTextSize(12);

        codemonNameLayout.addView(playerName);

        TextView playerHealth = new TextView(this);


        playerHealth.setText("10/10 H P");

        playerHealth.setTextColor(WHITE);

        playerHealth.setTypeface(cyberFont);

        playerHealth.setTextSize(12);

        codemonHealthLayout.addView(playerHealth);

        ImageView chiuplus = new ImageView(this);

        chiuplus.setImageResource(R.drawable.chiuplusback);

        chiuplus.setPadding(0, 0, 0, 20);

        codemonImageLayout.addView(chiuplus);
    }

    private void setEnemyDetailsOnScreen(String enemy){
        RelativeLayout enemyImageLayout = (RelativeLayout) findViewById(R.id.enemySpot);

        RelativeLayout enemyNameLayout = (RelativeLayout) findViewById(R.id.enemyName);

        RelativeLayout enemyHealthLayout = (RelativeLayout) findViewById(R.id.enemyHealth);

        TextView enemyName = new TextView(this);

        TextView enemyHealth = new TextView(this);

        enemyName.setText(ENEMY_CODEBUG);

        enemyName.setTextColor(WHITE);

        enemyName.setTypeface(cyberFont);

        enemyName.setTextSize(12);

        enemyNameLayout.addView(enemyName);

        enemyHealth.setText("10/10 H P");

        enemyHealth.setTextColor(WHITE);

        enemyHealth.setTypeface(cyberFont);

        enemyHealth.setTextSize(12);

        enemyHealthLayout.addView(enemyHealth);

        ImageView enemyImage = new ImageView(this);

        if(enemy.equals(ENEMY_CODEBUG)) {


            enemyImage.setImageResource(R.drawable.codebug);

        } else if(enemy.equals(ENEMY_CODEBOSS)) {

            enemyImage.setImageResource(R.drawable.boss);

        }

        enemyImageLayout.addView(enemyImage);
    }

    private void loadContent(){
        TextView content = new TextView(this);

        content.setTextColor(WHITE);

        content.setTypeface(cyberFont);

        content.setGravity(View.TEXT_ALIGNMENT_CENTER);

        content.setTextSize(12);

        content.setText(level.getContent());

        content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Bla", Toast.LENGTH_SHORT).show();
            }
        });

        middleLayout.addView(content);
    }

    private void setUpSkillBar(){

        Button skills = new Button(getApplicationContext());

        skills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSkillBar();

                setUserSkillBar();
            }
        });

        skills.setText("Skills");

        Button items = new Button(getApplicationContext());

        items.setText("Items");

        Button notes = new Button(getApplicationContext());

        notes.setText("Notebook");

        Button run = new Button(getApplicationContext());

        run.setText("Run away");

        firstHalfBottomLayout.addView(skills);
        firstHalfBottomLayout.addView(notes);

        secondHalfBottomLayout.addView(items);
        secondHalfBottomLayout.addView(run);
    }

    private void setUserSkillBar(){
        Button variables = new Button(getApplicationContext());

        variables.setText("Variables");

        Button flowControl = new Button(getApplicationContext());

        flowControl.setText("Flow Control");

        Button methodsAndClasses = new Button(getApplicationContext());

        methodsAndClasses.setText("Methods and Classes");

        Button back = new Button(getApplicationContext());

        back.setText("Back");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSkillBar();

                setUpSkillBar();
            }
        });

        firstHalfBottomLayout.addView(variables);
        firstHalfBottomLayout.addView(methodsAndClasses);

        secondHalfBottomLayout.addView(flowControl);
        secondHalfBottomLayout.addView(back);
    }

    @Override
    public void onBackPressed() {
        clearSkillBar();
        setUpSkillBar();
    }

    public void clearSkillBar(){
        firstHalfBottomLayout.removeAllViewsInLayout();
        secondHalfBottomLayout.removeAllViewsInLayout();
    }
}
