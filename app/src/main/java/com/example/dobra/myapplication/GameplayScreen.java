package com.example.dobra.myapplication;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.ArrayMap;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Map;

import static android.graphics.Color.BLUE;
import static android.graphics.Color.GREEN;
import static android.graphics.Color.RED;
import static android.graphics.Color.WHITE;

public class GameplayScreen extends AppCompatActivity {
    private Level level;

    private final String ENEMY_CODEBUG = "Codebug";

    private final String ENEMY_CODEBOSS = "Fatal Error";

    private Typeface cyberFont;

    private LinearLayout topLayout, firstHalfTopLayout, secondHalfTopLayout, middleLayout, bottomLayout,  firstHalfBottomLayout, secondHalfBottomLayout;

    private GameplayManager gameplayManager;

    private TextView currentTextViewSelected;


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

        gameplayManager = new GameplayManager();

        currentTextViewSelected = null;

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
        List<ContentParser.ContentType> contentWords = ContentParser.getInstance().processedWords(level);

        LinearLayout currentLine = new LinearLayout(getApplicationContext());
        currentLine.setOrientation(LinearLayout.HORIZONTAL);

        for(ContentParser.ContentType entry:contentWords){
            if(entry.isNewLine()){
                middleLayout.addView(currentLine);

                currentLine = new LinearLayout(getApplicationContext());

            } else {
                addNewView(entry, currentLine);
            }
        }
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

        run.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        run.setText("Run away");

        firstHalfBottomLayout.addView(skills);
        firstHalfBottomLayout.addView(notes);

        secondHalfBottomLayout.addView(items);
        secondHalfBottomLayout.addView(run);
    }

    private void setUserSkillBar(){
        Button variables = new Button(getApplicationContext());

        variables.setText("Variables");

        variables.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSkillBar();

                setVariablesSkillBar();

            }
        });

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

    private void setVariablesSkillBar(){
        final Button intBtn = new Button(getApplicationContext());

        intBtn.setText("int");

        intBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentTextViewSelected!=null) {
                    if (gameplayManager.getKeywordAnswer().contentEquals(intBtn.getText())) {
                        Toast.makeText(getApplicationContext(), "Correct", Toast.LENGTH_SHORT).show();
                        currentTextViewSelected.setTextColor(GREEN);
                        currentTextViewSelected.setText(intBtn.getText());
                        currentTextViewSelected.setOnClickListener(null);
                        currentTextViewSelected = null;
                    } else {
                        Toast.makeText(getApplicationContext(), "Wrong", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        final Button charBtn = new Button(getApplicationContext());

        charBtn.setText("char");

        charBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentTextViewSelected!=null) {
                    if (gameplayManager.getKeywordAnswer().contentEquals(charBtn.getText())) {
                        Toast.makeText(getApplicationContext(), "Correct", Toast.LENGTH_SHORT).show();
                        currentTextViewSelected.setTextColor(GREEN);
                        currentTextViewSelected.setText(charBtn.getText());
                    } else {
                        Toast.makeText(getApplicationContext(), "Wrong", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        final Button doubleBtn = new Button(getApplicationContext());

        doubleBtn.setText("double");

        doubleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentTextViewSelected!=null) {
                    if (gameplayManager.getKeywordAnswer().contentEquals(doubleBtn.getText())) {
                        Toast.makeText(getApplicationContext(), "Correct", Toast.LENGTH_SHORT).show();
                        currentTextViewSelected.setTextColor(GREEN);
                        currentTextViewSelected.setText(doubleBtn.getText());
                    } else {
                        Toast.makeText(getApplicationContext(), "Wrong", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        Button back = new Button(getApplicationContext());

        back.setText("Back");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSkillBar();

                setUserSkillBar();
            }
        });

        firstHalfBottomLayout.addView(intBtn);
        firstHalfBottomLayout.addView(charBtn);

        secondHalfBottomLayout.addView(doubleBtn);
        secondHalfBottomLayout.addView(back);
    }

    private void clearSkillBar(){
        firstHalfBottomLayout.removeAllViewsInLayout();
        secondHalfBottomLayout.removeAllViewsInLayout();
    }

    @Override
    public void onBackPressed() {
        clearSkillBar();
        setUpSkillBar();
    }

    private void addNewView(final ContentParser.ContentType content, LinearLayout currentLayout){
        final TextView newWord = new TextView(this);

        newWord.setTextColor(WHITE);

        newWord.setTypeface(cyberFont);

        newWord.setPadding(10, 10, 10, 10);

        newWord.setGravity(View.TEXT_ALIGNMENT_CENTER);

        newWord.setTextSize(12);

        newWord.setText(content.wordContent);

        if(content.isKeyword()) {

            newWord.setTextColor(BLUE);

            newWord.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(gameplayManager.isKeyWordSelected()){
                        if(newWord.getCurrentTextColor() == RED){
                            newWord.setTextColor(BLUE);
                            currentTextViewSelected = null;
                            gameplayManager.clear();
                        } else {
                            Toast.makeText(getApplicationContext(), "Keyword is already selected!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        currentTextViewSelected = newWord;
                        gameplayManager.setKeywordSelected(content.wordContent);
                        gameplayManager.setKeywordAnswer(content.correctWord);
                        newWord.setTextColor(RED);
                    }
                }
            });
        }

        currentLayout.addView(newWord);
    }
}
