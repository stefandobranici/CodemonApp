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

    private void setUpSkillBar(){

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
}
