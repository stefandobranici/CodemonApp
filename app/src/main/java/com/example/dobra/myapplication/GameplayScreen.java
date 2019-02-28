package com.example.dobra.myapplication;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import pl.droidsonroids.gif.GifImageView;

import static android.graphics.Color.BLUE;
import static android.graphics.Color.GREEN;
import static android.graphics.Color.RED;
import static android.graphics.Color.WHITE;

public class GameplayScreen extends AppCompatActivity {
    private Level level;

    private final String ENEMY_CODEBUG = "Codebug";

    private final String ENEMY_CODEBOSS = "Fatal Error";

    private Typeface consolasFont;

    private LinearLayout topScreenLayout, middleScreenLayout;

    private ImageView gameScreenPlayerImage, gameScreenEnemyImage;

    private TextView gameScreenPlayerHealth, gameScreenPlayerLevel, gameScreenPlayerName, gameScreenEnemyName, gameScreenEnemyLevel, gameScreenEnemyHealth;

    //All menu layouts from third part of the screen
    private LinearLayout mainMenuBarLayout, mainSkillBarLayout, variablesSkillBarLayout, conditionalsSkillBarLayout, loopsSkillBarLayout, itemsSkillBarLayout;

    //Main menu bar buttons;
    private TextView openSkillsBarButton, openItemsBarButton, openNotebookButton, runAwayButton;

    //Skill menu buttons;
    private TextView openVariablesBarButton, openConditionalsBarButton, openLoopsBarButton, goBackToMainMenuButtonFromSkillsBar;

    //Variables skill bar buttons;
    private TextView intButton, charButton, floatButton, doubleButton, stringButton, toArrayButton, to2dArrayButton, goBackToSkillBarButtonFromVariables;

    //Conditionals skill bar buttons;
    private TextView ifButton, elseButton, elseIfButton, goBackToSkillBarButtonFromConditionals;

    //Loops skill bar buttons;
    private TextView whileButton, doButton, forButton, goBackToSkillBarButtonFromLoops;

    //Items bar buttons;
    private TextView useHealthPotionButton, useRevealingPotionButton, useFixerElixirButton, goBackToMainMenuButtonFromItemsBar;

    //Attack animations
    private GifImageView chiuplusattackanimation, enemyattackanimation;

    private TextView currentTextViewClicked;

    private ContentParser.ContentType currentContentTypeProcessed;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameplay_screen);

        level = CurrentUserInformation.getInstance().getLevelSelectedForPlay();

        consolasFont = Typeface.createFromAsset(getAssets(), "font/Consolas.ttf");

        currentTextViewClicked = null;

        currentContentTypeProcessed = null;

        if(level.isGreen()){
            setLearningScreenOn();
        } else if(level.isViolette()){
            setTrainingScreenOn();
        } else if(level.isRed()){
            setBossScreenOn();
        }
    }

    private void setLearningScreenOn(){
        topScreenLayout = (LinearLayout) findViewById(R.id.topScreenLayout);

        ImageView trainerImage = new ImageView(getApplicationContext());

        trainerImage.setImageResource(R.drawable.trainer);

        topScreenLayout.addView(trainerImage);

        loadContentOnScreen();
    }

    private void setTrainingScreenOn(){
        setUserInformationOnScreen();

        setEnemyInformationOnScreen(ENEMY_CODEBUG);

        loadContentOnScreen();

    }

    private void setBossScreenOn(){
        setUserInformationOnScreen();

        setEnemyInformationOnScreen(ENEMY_CODEBOSS);

        loadContentOnScreen();
    }

    private void setUserInformationOnScreen(){
        gameScreenPlayerImage = (ImageView) findViewById(R.id.gameScreenPlayerImage);

        gameScreenPlayerImage.setImageResource(R.drawable.chiuplusback);

        gameScreenPlayerHealth = (TextView) findViewById(R.id.gameScreenPlayerHealth);

        gameScreenPlayerLevel = (TextView) findViewById(R.id.gameScreenPlayerLevel);

        gameScreenPlayerName = (TextView) findViewById(R.id.gameScreenPlayerName);

        chiuplusattackanimation = (GifImageView) findViewById(R.id.chiuplusattackanimation);

        gameScreenPlayerHealth.setText("HP  "+String.format(CurrentUserInformation.getInstance().getUserHealth().toString())+"/"+String.format(UserLevel.getInstance().getMaxHealthAtLevel(CurrentUserInformation.getInstance().getUserLevel()).toString()));

        gameScreenPlayerName.setText(CurrentUserInformation.getInstance().getUserName());

        gameScreenPlayerLevel.setText("Lv. "+String.format(CurrentUserInformation.getInstance().getUserLevel().toString()));
    }

    private void setEnemyInformationOnScreen(String enemy){
        gameScreenEnemyImage = (ImageView) findViewById(R.id.gameScreenEnemyImage);

        gameScreenEnemyName = (TextView) findViewById(R.id.gameScreenEnemyName);

        gameScreenEnemyLevel = (TextView) findViewById(R.id.gameScreenEnemyLevel);

        gameScreenEnemyHealth = (TextView) findViewById(R.id.gameScreenEnemyHealth);

        enemyattackanimation = (GifImageView) findViewById(R.id.enemyattackanimation);

        gameScreenEnemyName.setText(enemy);

        gameScreenEnemyLevel.setText("Lv.  "+String.format(level.getLevel().toString()));

        gameScreenEnemyHealth.setText("HP  10/10");

        if(enemy.equals(ENEMY_CODEBUG)){
            gameScreenEnemyImage.setImageResource(R.drawable.codebug);
        } else if(enemy.equals(ENEMY_CODEBOSS)){
            gameScreenEnemyImage.setImageResource(R.drawable.boss);
        }
    }

    private void loadContentOnScreen(){
        middleScreenLayout = (LinearLayout) findViewById(R.id.middleScreenLayout);

        LinearLayout lineOfWords = new LinearLayout(getApplicationContext());

        lineOfWords.setPadding(15, 15, 0, 0);

        List<ContentParser.ContentType> listOfWords = ContentParser.getInstance().processedWords(level);

        for(final ContentParser.ContentType contentType:listOfWords){
            final TextView currentProcessedWord = new TextView(getApplicationContext());

            currentProcessedWord.setTypeface(consolasFont);

            currentProcessedWord.setTextSize(16);

            if(contentType.isKeyword()){
                currentProcessedWord.setTextColor(getResources().getColor(R.color.colorAccent));

                currentProcessedWord.setText(contentType.wordContent);

                currentProcessedWord.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(currentTextViewClicked!=null) {
                            if(currentTextViewClicked == currentProcessedWord){
                                enemyattackanimation.setImageResource(R.drawable.enemyattack);
                                enemyattackanimation.clearAnimation();


                                enemyattackanimation.setVisibility(View.VISIBLE);

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        enemyattackanimation.setVisibility(View.GONE);
                                        enemyattackanimation.clearAnimation();
                                    }
                                }, 2000);

                                currentTextViewClicked = currentProcessedWord;
                                currentContentTypeProcessed = contentType;
                                currentProcessedWord.setTextColor(Color.parseColor("#FF4500"));


                                currentProcessedWord.setTextColor(getResources().getColor(R.color.colorAccent));
                                currentTextViewClicked = null;
                                currentContentTypeProcessed = null;
                            } else {
                                currentTextViewClicked.setTextColor(getResources().getColor(R.color.colorAccent));
                                currentTextViewClicked = currentProcessedWord;
                                currentContentTypeProcessed = contentType;
                                currentProcessedWord.setTextColor(Color.parseColor("#FF4500"));
                            }
                        } else {
                            chiuplusattackanimation.setImageResource(R.drawable.chiuplusattack);
                            chiuplusattackanimation.clearAnimation();


                            chiuplusattackanimation.setVisibility(View.VISIBLE);

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    chiuplusattackanimation.setVisibility(View.GONE);
                                    chiuplusattackanimation.clearAnimation();
                                }
                            }, 2000);

                            currentTextViewClicked = currentProcessedWord;
                            currentContentTypeProcessed = contentType;
                            currentProcessedWord.setTextColor(Color.parseColor("#FF4500"));


                        }
                    }
                });

                currentProcessedWord.setPadding(0, 0,20,0);

                lineOfWords.addView(currentProcessedWord);

            } else if(contentType.isRegularWord()){
                currentProcessedWord.setTextColor(WHITE);

                currentProcessedWord.setText(contentType.wordContent);

                currentProcessedWord.setPadding(0, 0,20,0);

                lineOfWords.addView(currentProcessedWord);
            } else if(contentType.isNewLine()){
                middleScreenLayout.addView(lineOfWords);

                lineOfWords = new LinearLayout(getApplicationContext());

                lineOfWords.setPadding(15, 15, 0, 0);
            }
        }

    }

    private void setUpSkillBar(){
        setUpMainMenuBarLayout();
        setUpMainSkillBarLayout();
        setUpVariablesSkillBarLayout();
        setUpConditionalsSkillBarLayout();
        setUpLoopsSkillBarLayout();
        setUpItemsSkillBarLayout();
    }

    private void setUpMainMenuBarLayout(){
        mainMenuBarLayout = (LinearLayout) findViewById(R.id.mainMenuBarLayout);
        mainMenuBarLayout.setVisibility(View.VISIBLE);

        setOpenSkillsBarButtonOnClickListener();
        setOpenItemsBarButtonOnClickListener();
        setOpenNotebookButtonOnClickListener();
        setRunAwayButtonOnClickListener();
    }

    private void setUpMainSkillBarLayout(){
        mainSkillBarLayout = (LinearLayout) findViewById(R.id.mainSkillBarLayout);
        mainSkillBarLayout.setVisibility(View.GONE);

        setOpenVariablesBarButtonOnClickListener();
        setOpenConditionalsBarButtonOnClickListener();
        setOpenLoopsBarButtonOnClickListener();
        setGoBackToMainMenuButtonFromSkillsBaOnClickListener();
    }

    private void setUpVariablesSkillBarLayout(){
        variablesSkillBarLayout = (LinearLayout) findViewById(R.id.variablesSkillBarLayout);
        variablesSkillBarLayout.setVisibility(View.GONE);

        setIntButtonOnClickListener();
        setCharButtonOnClickListener();
        setFloatButtonOnClickListener();
        setDoubleButtonOnClickListener();
        setStringButtonOnClickListener();
        setToArrayButtonOnClickListener();
        setTo2dArrayButtonOnClickListener();
        setGoBackToSkillBarButtonFromVariablesOnClickListener();
    }

    private void setUpConditionalsSkillBarLayout(){
        conditionalsSkillBarLayout = (LinearLayout) findViewById(R.id.conditionalsSkillBarLayout);
        conditionalsSkillBarLayout.setVisibility(View.GONE);

        setIfButtonOnClickListener();
        setElseButtonOnClickListener();
        setElseIfButtonOnClickListener();
        setGoBackToSkillBarButtonFromConditionals();
    }

    private void setUpLoopsSkillBarLayout(){
        loopsSkillBarLayout = (LinearLayout) findViewById(R.id.loopsSkillBarLayout);
        loopsSkillBarLayout.setVisibility(View.GONE);

        setWhileButtonOnClickListener();
        setDoButtonOnClickListener();
        setForButtonOnClickListener();
        setGoBackToSkillBarButtonFromLoops();
    }

    private void setUpItemsSkillBarLayout(){
        itemsSkillBarLayout = (LinearLayout) findViewById(R.id.itemsSkillBarLayout);
        itemsSkillBarLayout.setVisibility(View.GONE);

        setUseHealthPotionButtonOnClickListener();
        setUseRevealingPotionButtonOnClickListener();
        setUseFixerElixirButtonOnClickListener();
        setGoBackToMainMenuButtonFromItemsBar();
    }


    //Main menu buttons on click listeners; openSkillsBarButton, openItemsBarButton, openNotebookButton, runAwayButton
    private void setOpenSkillsBarButtonOnClickListener(){
        openSkillsBarButton = (TextView) findViewById(R.id.openSkillsBarButton);
    }

    private void setOpenItemsBarButtonOnClickListener(){
        openItemsBarButton = (TextView) findViewById(R.id.openItemsBarButton);
    }

    private void setOpenNotebookButtonOnClickListener(){
        openNotebookButton = (TextView) findViewById(R.id.openNotebookButton);
    }

    private void setRunAwayButtonOnClickListener(){
        runAwayButton = (TextView) findViewById(R.id.runAwayButton);
    }

    //Main skills bar menu buttons on click listeners; openVariablesBarButton, openConditionalsBarButton, openLoopsBarButton, goBackToMainMenuButtonFromSkillsBar;
    private void setOpenVariablesBarButtonOnClickListener(){
        openVariablesBarButton = (TextView) findViewById(R.id.openVariablesBarButton);
    }

    private void setOpenConditionalsBarButtonOnClickListener(){
        openConditionalsBarButton = (TextView) findViewById(R.id.openConditionalsBarButton);
    }

    private void setOpenLoopsBarButtonOnClickListener(){
        openLoopsBarButton = (TextView) findViewById(R.id.openLoopsBarButton);
    }

    private void setGoBackToMainMenuButtonFromSkillsBaOnClickListener(){
        goBackToMainMenuButtonFromSkillsBar = (TextView) findViewById(R.id.goBackToMainMenuButtonFromSkillsBar);
    }

    //Variables bar menu buttons on click listener;  intButton, charButton, floatButton, doubleButton, stringButton, toArrayButton, to2dArrayButton, goBackToSkillBarButtonFromVariables;

    private void setIntButtonOnClickListener(){
        intButton = (TextView) findViewById(R.id.intButton);
    }

    private void setCharButtonOnClickListener(){
        charButton = (TextView) findViewById(R.id.charButton);
    }

    private void setFloatButtonOnClickListener(){
        floatButton = (TextView) findViewById(R.id.floatButton);
    }

    private void setDoubleButtonOnClickListener(){
        doubleButton = (TextView) findViewById(R.id.doubleButton);
    }

    private void setStringButtonOnClickListener(){
        stringButton = (TextView) findViewById(R.id.stringButton);
    }

    private void setToArrayButtonOnClickListener(){
        toArrayButton = (TextView) findViewById(R.id.toArrayButton);
    }

    private void setTo2dArrayButtonOnClickListener(){
        to2dArrayButton = (TextView) findViewById(R.id.to2dArrayButton);
    }

    private void setGoBackToSkillBarButtonFromVariablesOnClickListener(){
        goBackToSkillBarButtonFromVariables = (TextView) findViewById(R.id.goBackToSkillBarButtonFromVariables);
    }
    //Conditionals bar menu buttons on click listener; ifButton, elseButton, elseIfButton, goBackToSkillBarButtonFromConditionals;
    private void setIfButtonOnClickListener(){
        ifButton = (TextView) findViewById(R.id.ifButton);
    }

    private void setElseButtonOnClickListener(){
        elseButton = (TextView) findViewById(R.id.elseButton);
    }

    private void setElseIfButtonOnClickListener(){
        elseIfButton = (TextView) findViewById(R.id.elseIfButton);
    }

    private void setGoBackToSkillBarButtonFromConditionals(){
        goBackToSkillBarButtonFromConditionals = (TextView) findViewById(R.id.goBackToSkillBarButtonFromConditionals);
    }

    //Loops bar menu buttons on click listener; whileButton, doButton, forButton, goBackToSkillBarButtonFromLoops;

    private void setWhileButtonOnClickListener(){
        whileButton = (TextView) findViewById(R.id.whileButton);
    }

    private void setDoButtonOnClickListener(){
        doButton = (TextView) findViewById(R.id.doButton);
    }

    private void setForButtonOnClickListener(){
        forButton = (TextView) findViewById(R.id.forButton);
    }

    private void setGoBackToSkillBarButtonFromLoops(){
        goBackToSkillBarButtonFromLoops = (TextView) findViewById(R.id.goBackToSkillBarButtonFromLoops);
    }

    //Items bar menu buttons on click listener; useHealthPotionButton, useRevealingPotionButton, useFixerElixirButton, goBackToMainMenuButtonFromItemsBar;

    private void setUseHealthPotionButtonOnClickListener(){
        useHealthPotionButton = (TextView) findViewById(R.id.useHealthPotionButton);
    }

    private void setUseRevealingPotionButtonOnClickListener(){
        useRevealingPotionButton = (TextView) findViewById(R.id.useRevealingPotionButton);
    }

    private void setUseFixerElixirButtonOnClickListener(){
        useFixerElixirButton = (TextView) findViewById(R.id.useFixerElixirButton);
    }

    private void setGoBackToMainMenuButtonFromItemsBar(){
        goBackToMainMenuButtonFromItemsBar = (TextView) findViewById(R.id.goBackToMainMenuButtonFromItemsBar);
    }
}
